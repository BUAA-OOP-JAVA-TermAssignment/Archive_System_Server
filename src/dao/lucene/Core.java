package dao.lucene;

import dao.utils.BM25SimilarityOriginal;
import dao.utils.LuceneUtils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.util.BytesRef;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


//TODO:修改连接到数据库，并删除一下原注释，但是等到数据库和服务器弄好再说吧

/**
 * @author pcpas
 */
public class Core {

    static String pathCorpus = "";
    static String pathIndex = "";

    static void buildIndex() throws IOException {
        //欲输入的数据路径和索引表的存放路径
        String pathCorpus = Core.pathCorpus;
        String pathIndex = Core.pathIndex;

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());

        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                // Step 1: 选择合适的tokenize方法
                TokenStreamComponents ts = new TokenStreamComponents(new StandardTokenizer());
                // Step 2: 所有token转换为小写
                ts = new TokenStreamComponents(ts.getSource(), new LowerCaseFilter(ts.getTokenStream()));
                // Step 3: whether to remove stop words (unnecessary to remove stop words unless you can't afford the extra disk space)
                // Uncomment the following line to remove stop words
                // ts = new TokenStreamComponents( ts.getSource(), new StopFilter( ts.getTokenStream(), EnglishAnalyzer.ENGLISH_STOP_WORDS_SET ) );
                // Step 4: whether to apply stemming
                // Uncomment one of the following two lines to apply Krovetz or Porter stemmer (Krovetz is more common for IR research)
                ts = new TokenStreamComponents(ts.getSource(), new KStemFilter(ts.getTokenStream()));
                // ts = new TokenStreamComponents( ts.getSource(), new PorterStemFilter( ts.getTokenStream() ) );
                return ts;
            }
        };

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // Note that IndexWriterConfig.OpenMode.CREATE will override the original index in the folder
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // Lucene's default BM25Similarity stores document field length using a "low-precision" method.
        // Use the BM25SimilarityOriginal to store the original document length values in index.
        config.setSimilarity(new BM25SimilarityOriginal());

        IndexWriter ixwriter = new IndexWriter(dir, config);

        // This is the field setting for metadata field (no tokenization, searchable, and stored).
        FieldType fieldTypeMetadata = new FieldType();
        fieldTypeMetadata.setOmitNorms(true);
        fieldTypeMetadata.setIndexOptions(IndexOptions.DOCS);
        fieldTypeMetadata.setStored(true);
        fieldTypeMetadata.setTokenized(false);
        fieldTypeMetadata.freeze();

        // This is the field setting for normal text field (tokenized, searchable, store document vectors)
        FieldType fieldTypeText = new FieldType();
        fieldTypeText.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        fieldTypeText.setStoreTermVectors(true);
        fieldTypeText.setStoreTermVectorPositions(true);
        fieldTypeText.setTokenized(true);
        fieldTypeText.setStored(true);
        fieldTypeText.freeze();

        // You need to iteratively read each document from the example corpus file,
        // create a Document object for the parsed document, and add that
        // Document object by calling addDocument().

        // Well, the following only works for small text files. DO NOT follow this part for large dataset files.
        InputStream instream = new GZIPInputStream(new FileInputStream(pathCorpus));
        String corpusText = new String(IOUtils.toByteArray(instream), "UTF-8");
        instream.close();

        Pattern pattern = Pattern.compile(
                "<DOC>.+?<DOCNO>(.+?)</DOCNO>.+?<TITLE>(.+?)</TITLE>.+?<AUTHOR>(.+?)</AUTHOR>.+?<SOURCE>(.+?)</SOURCE>.+?<TEXT>(.+?)</TEXT>.+?</DOC>",
                Pattern.CASE_INSENSITIVE + Pattern.MULTILINE + Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(corpusText);

        while (matcher.find()) {

            String docno = matcher.group(1).trim();
            String title = matcher.group(2).trim();
            String author = matcher.group(3).trim();
            String source = matcher.group(4).trim();
            String text = matcher.group(5).trim();

            // Create a Document object
            Document d = new Document();
            // Add each field to the document with the appropriate field type options
            d.add(new Field("docno", docno, fieldTypeMetadata));
            d.add(new Field("title", title, fieldTypeText));
            d.add(new Field("author", author, fieldTypeText));
            d.add(new Field("source", source, fieldTypeText));
            d.add(new Field("text", text, fieldTypeText));
            // Add the document to the index
            System.out.println("indexing document " + docno);
            ixwriter.addDocument(d);
        }

        // remember to close both the index writer and the directory
        ixwriter.close();
        dir.close();
    }

    static void getNumOfIndex() throws IOException {
        // modify to your index path
        String pathIndex = Core.pathIndex;

        // First, open the directory
        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        // Then, open an IndexReader to access your index
        IndexReader index = DirectoryReader.open(dir);

        // Now, start working with your index using the IndexReader object
        System.out.println(index.numDocs()); // just an example: get the number of documents in the index

        // Remember to close both the IndexReader and the Directory after use
        index.close();
        dir.close();
    }

    static void listByFrequency() throws IOException {
        String pathIndex = Core.pathIndex;

        // Let's just retrieve the posting list for the term "reformulation" in the "text" field
        String field = "text";
        String term = "reformulation";

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        // The following line reads the posting list of the term in a specific index field.
        // You need to encode the term into a BytesRef object,
        // which is the internal representation of a term used by Lucene.
        System.out.printf("%-10s%-15s%-6s\n", "DOCID", "DOCNO", "FREQ");
        PostingsEnum posting = MultiTerms.getTermPostingsEnum(index, field, new BytesRef(term), PostingsEnum.FREQS);
        if (posting != null) { // if the term does not appear in any document, the posting object may be null
            int docid;
            // Each time you call posting.nextDoc(), it moves the cursor of the posting list to the next position
            // and returns the docid of the current entry (document). Note that this is an internal Lucene docid.
            // It returns PostingsEnum.NO_MORE_DOCS if you have reached the end of the posting list.
            while ((docid = posting.nextDoc()) != PostingsEnum.NO_MORE_DOCS) {
                String docno = LuceneUtils.getDocno(index, "docno", docid);
                int freq = posting.freq(); // get the frequency of the term in the current document
                System.out.printf("%-10d%-15s%-6d\n", docid, docno, freq);
            }
        }
        index.close();
        dir.close();
    }

    static void listByPosition() throws IOException {
        String pathIndex = Core.pathIndex;

        // Let's just retrieve the posting list for the term "reformulation" in the "text" field
        String field = "text";
        String term = "reformulation";

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        // we also print out external ID
        Set<String> fieldset = new HashSet<>();
        fieldset.add("docno");

        // The following line reads the posting list of the term in a specific index field.
        // You need to encode the term into a BytesRef object,
        // which is the internal representation of a term used by Lucene.
        System.out.printf("%-10s%-15s%-10s%-20s\n", "DOCID", "DOCNO", "FREQ", "POSITIONS");
        PostingsEnum posting = MultiTerms.getTermPostingsEnum(index, field, new BytesRef(term), PostingsEnum.POSITIONS);
        if (posting != null) { // if the term does not appear in any document, the posting object may be null
            int docid;
            // Each time you call posting.nextDoc(), it moves the cursor of the posting list to the next position
            // and returns the docid of the current entry (document). Note that this is an internal Lucene docid.
            // It returns PostingsEnum.NO_MORE_DOCS if you have reached the end of the posting list.
            while ((docid = posting.nextDoc()) != PostingsEnum.NO_MORE_DOCS) {
                String docno = index.document(docid, fieldset).get("docno");
                int freq = posting.freq(); // get the frequency of the term in the current document
                System.out.printf("%-10d%-15s%-10d", docid, docno, freq);
                for (int i = 0; i < freq; i++) {
                    // Get the next occurrence position of the term in the current document.
                    // Note that you need to make sure by yourself that you at most call this function freq() times.
                    System.out.print((i > 0 ? "," : "") + posting.nextPosition());
                }
                System.out.println();
            }
        }

        index.close();
        dir.close();
    }

    static void vistIndexedDocument() throws IOException {
        String pathIndex = Core.pathIndex;

        // let's just retrieve the document vector (only the "text" field) for the Document with internal ID=21
        String field = "text";
        int docid = 21;

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        Terms vector = index.getTermVector(docid, field); // Read the document's document vector.

        // You need to use TermsEnum to iterate each entry of the document vector (in alphabetical order).
        System.out.printf("%-20s%-10s%-20s\n", "TERM", "FREQ", "POSITIONS");
        TermsEnum terms = vector.iterator();
        PostingsEnum positions = null;
        BytesRef term;
        while ((term = terms.next()) != null) {

            String termstr = term.utf8ToString(); // Get the text string of the term.
            long freq = terms.totalTermFreq(); // Get the frequency of the term in the document.

            System.out.printf("%-20s%-10d", termstr, freq);

            // Lucene's document vector can also provide the position of the terms
            // (in case you stored these information in the index).
            // Here you are getting a PostingsEnum that includes only one document entry, i.e., the current document.
            positions = terms.postings(positions, PostingsEnum.POSITIONS);
            positions.nextDoc(); // you still need to move the cursor
            // now accessing the occurrence position of the terms by iteratively calling nextPosition()
            for (int i = 0; i < freq; i++) {
                System.out.print((i > 0 ? "," : "") + positions.nextPosition());
            }
            System.out.println();
        }

        index.close();
        dir.close();
    }

    static void getDocLength() throws IOException {
        String pathIndex = Core.pathIndex;
        String field = "text";

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader ixreader = DirectoryReader.open(dir);

        // we also print out external ID
        Set<String> fieldset = new HashSet<>();
        fieldset.add("docno");

        // The following loop iteratively print the lengths of the documents in the index.
        System.out.printf("%-10s%-15s%-10s\n", "DOCID", "DOCNO", "Length");
        for (int docid = 0; docid < ixreader.maxDoc(); docid++) {
            String docno = ixreader.document(docid, fieldset).get("docno");
            int doclen = 0;
            TermsEnum termsEnum = ixreader.getTermVector(docid, field).iterator();
            while (termsEnum.next() != null) {
                doclen += termsEnum.totalTermFreq();
            }
            System.out.printf("%-10d%-15s%-10d\n", docid, docno, doclen);
        }

        ixreader.close();
        dir.close();
    }

    static void getTextCorpusStatics() throws IOException {
        String pathIndex = Core.pathIndex;

        // Let's just count the IDF and P(w|corpus) for the word "reformulation" in the "text" field
        String field = "text";
        String term = "reformulation";

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        int N = index.numDocs(); // the total number of documents in the index
        int n = index.docFreq(new Term(field, term)); // get the document frequency of the term in the "text" field
        double idf = Math.log((N + 1.0) / (n + 1.0)); // well, we normalize N and n by adding 1 to avoid n = 0

        System.out.printf("%-30sN=%-10dn=%-10dIDF=%-8.2f\n", term, N, n, idf);

        long corpusTF = index.totalTermFreq(new Term(field, term)); // get the total frequency of the term in the "text" field
        long corpusLength = index.getSumTotalTermFreq(field); // get the total length of the "text" field
        double pwc = 1.0 * corpusTF / corpusLength;

        System.out.printf("%-30slen(corpus)=%-10dfreq(%s)=%-10dP(%s|corpus)=%-10.6f\n", term, corpusLength, term, corpusTF, term, pwc);

        // remember to close the index and the directory
        index.close();
        dir.close();
    }

    static void listVocabulary() throws IOException {
        String pathIndex = Core.pathIndex;

        // Let's just retrieve the vocabulary of the "text" field
        String field = "text";

        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        double N = index.numDocs();
        double corpusLength = index.getSumTotalTermFreq(field);

        System.out.printf("%-30s%-10s%-10s%-10s%-10s\n", "TERM", "DF", "TOTAL_TF", "IDF", "p(w|c)");

        // Get the vocabulary of the index.

        Terms voc = MultiTerms.getTerms(index, field);
        // You need to use TermsEnum to iterate each entry of the vocabulary.
        TermsEnum termsEnum = voc.iterator();
        BytesRef term;
        int count = 0;
        while ((term = termsEnum.next()) != null) {
            count++;
            String termstr = term.utf8ToString(); // get the text string of the term
            int n = termsEnum.docFreq(); // get the document frequency (DF) of the term
            long freq = termsEnum.totalTermFreq(); // get the total frequency of the term
            double idf = Math.log((N + 1.0) / (n + 1.0)); // well, we normalize N and n by adding 1 to avoid n = 0
            double pwc = freq / corpusLength;
            System.out.printf("%-30s%-10d%-10d%-10.2f%-10.8f\n", termstr, n, freq, idf, pwc);
            if (count >= 100) {
                break;
            }
        }

        index.close();
        dir.close();
    }

    static void query() throws ParseException, IOException {
        String pathIndex = Core.pathIndex;

        // Analyzer specifies options for text tokenization and normalization (e.g., stemming, stop words removal, case-folding)
        Analyzer analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                // Step 1: tokenization (Lucene's StandardTokenizer is suitable for most text retrieval occasions)
                TokenStreamComponents ts = new TokenStreamComponents(new StandardTokenizer());
                // Step 2: transforming all tokens into lowercased ones (recommended for the majority of the problems)
                ts = new TokenStreamComponents(ts.getSource(), new LowerCaseFilter(ts.getTokenStream()));
                // Step 3: whether to remove stop words (unnecessary to remove stop words unless you can't afford the extra disk space)
                // Uncomment the following line to remove stop words
                // ts = new TokenStreamComponents( ts.getSource(), new StopFilter( ts.getTokenStream(), EnglishAnalyzer.ENGLISH_STOP_WORDS_SET ) );
                // Step 4: whether to apply stemming
                // Uncomment one of the following two lines to apply Krovetz or Porter stemmer (Krovetz is more common for IR research)
                ts = new TokenStreamComponents(ts.getSource(), new KStemFilter(ts.getTokenStream()));
                // ts = new TokenStreamComponents( ts.getSource(), new PorterStemFilter( ts.getTokenStream() ) );
                return ts;
            }
        };

        String field = "text"; // the field you hope to search for
        QueryParser parser = new QueryParser(field, analyzer); // a query parser that transforms a text string into Lucene's query object

        String qstr = "query reformulation"; // this is the textual search query
        Query query = parser.parse(qstr); // this is Lucene's query object

        // Okay, now let's open an index and search for documents
        Directory dir = FSDirectory.open(new File(pathIndex).toPath());
        IndexReader index = DirectoryReader.open(dir);

        // you need to create a Lucene searcher
        IndexSearcher searcher = new IndexSearcher(index);

        // make sure the similarity class you are using is consistent with those being used for indexing
        searcher.setSimilarity(new BM25SimilarityOriginal());

        int top = 10; // Let's just retrieve the talk 10 results
        TopDocs docs = searcher.search(query, top); // retrieve the top 10 results; retrieved results are stored in TopDocs

        System.out.printf("%-10s%-20s%-10s%s\n", "Rank", "DocNo", "Score", "Title");
        int rank = 1;
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            int docid = scoreDoc.doc;
            double score = scoreDoc.score;
            String docno = LuceneUtils.getDocno(index, "docno", docid);
            String title = LuceneUtils.getDocno(index, "title", docid);
            System.out.printf("%-10d%-20s%-10.4f%s\n", rank, docno, score, title);
            rank++;
        }

        // remember to close the index and the directory
        index.close();
        dir.close();
    }

    public static void main(String[] args) {

        try {
            query();
        } catch (Exception e) {
            System.out.println("Hi!");
        }

    }
}