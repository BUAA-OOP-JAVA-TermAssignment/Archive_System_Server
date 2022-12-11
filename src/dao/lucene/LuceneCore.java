package dao.lucene;


import controller.SearchController;
import dao.utils.DBUtil;

import java.nio.file.FileSystems;
import java.sql.ResultSet;

import dao.utils.IKAnalyzer4Lucene9;
import message.SearchReturnMsg;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @author pcpas
 */
public class LuceneCore {

    private static LuceneCore luceneCore;

    /**
     * 线程安全的单例模式
     *
     * @return
     */
    public static LuceneCore getInstance() {
        if (luceneCore == null) {
            synchronized (LuceneCore.class) {
                if (luceneCore == null) {
                    luceneCore = new LuceneCore();
                }
            }
        }
        return luceneCore;
    }


    static final String PATH_INDEX = "D:\\Archive_System\\example_index_lucene";

    /**
     * 通过本地存放的文档来建立Lucene索引
     *
     * @throws IOException
     */
    public void buildIndex() throws SQLException, IOException {
        //欲输入的数据路径和索引表的存放路径
        Directory dir = FSDirectory.open(new File(LuceneCore.PATH_INDEX).toPath());

        Analyzer analyzer = new IKAnalyzer4Lucene9();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 每次新建索引都会覆盖原来的索引
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter ixwriter = new IndexWriter(dir, config);

        //metadata field (no tokenization, searchable, and stored).
        FieldType fieldTypeMetadata = new FieldType();
        fieldTypeMetadata.setOmitNorms(true);
        fieldTypeMetadata.setIndexOptions(IndexOptions.DOCS);
        fieldTypeMetadata.setStored(true);
        fieldTypeMetadata.setTokenized(false);
        fieldTypeMetadata.freeze();

        //normal text field (tokenized, searchable, store document vectors)
        FieldType fieldTypeText = new FieldType();
        fieldTypeText.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        fieldTypeText.setStoreTermVectors(true);
        fieldTypeText.setStoreTermVectorPositions(true);
        fieldTypeText.setTokenized(true);
        fieldTypeText.setStored(true);
        fieldTypeText.freeze();

        Connection cn = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        assert cn != null;
        ps = cn.prepareStatement("select * from document");
        rs = ps.executeQuery();
        while (rs.next()) {
            Document doc = new Document();
            doc.add(new Field("id", rs.getString("id"), fieldTypeMetadata));
            doc.add(new Field("name", rs.getString("name"), fieldTypeText));
            doc.add(new Field("author", rs.getString("author"), fieldTypeText));
            doc.add(new Field("content", rs.getString("content"), fieldTypeText));
            doc.add(new Field("downloadCnt", String.valueOf(rs.getInt("downloadCnt")), fieldTypeText));
            ixwriter.addDocument(doc);
        }
        cn.close();
        ixwriter.close();
        dir.close();
        return;
    }

    /**
     * 通过关键词搜索
     *
     * @param keyWord 关键词
     * @param offset  偏移量
     * @param max     搜索篇数
     * @return 成功返回搜索回执MSG, 失败返回null
     */
    public SearchReturnMsg search(String keyWord, int offset, int max) {
        DirectoryReader directoryReader = null;
        boolean isValid = true;
        if ("".equals(keyWord)) {
            keyWord = "pcpas";
            isValid = false;
        }
        try {
            // 1、创建Directory
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(PATH_INDEX));
            // 2、创建IndexReader
            directoryReader = DirectoryReader.open(directory);
            // 3、根据IndexReader创建IndexSearch
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            // 4、创建搜索的Query
            Analyzer analyzer = new IKAnalyzer4Lucene9();

            String[] fields = {"name", "content", "author"};
            // MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
            // MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国"
            Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);

            // 5、根据searcher搜索并且返回TopDocs
            TopDocs topDocs = indexSearcher.search(multiFieldQuery, offset + max);
            // 6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共找到匹配文档数：" + scoreDocs.length);
            SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("**", "**");
            QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
            Highlighter highlighter = new Highlighter(simpleHtmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            SearchReturnMsg srm = new SearchReturnMsg();
            int i = 0;
            for (ScoreDoc scoreDoc : scoreDocs) {
                i++;
                if (i <= offset) {
                    continue;
                }
                // 7、根据searcher和ScoreDoc对象获取具体的Document对象
                Document document = indexSearcher.doc(scoreDoc.doc);
                System.out.println(document.get("id"));
                String content = document.get("content");
                String matchSegment = "";
                if (!isValid) {
                    matchSegment = "猜你喜欢";
                } else {
                    String[] segs = highlighter.getBestFragments(analyzer, "content", content, 5);
                    for (String s : segs) {
                        matchSegment += s;
                        matchSegment += "\n";
                    }
                }
                //打包发给前台
                srm.addDoc(document.get("id"), document.get("name"), document.get("author"), matchSegment, document.get("downloadCnt"), scoreDoc.score);
            }
            return srm;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lucene search failed");
        } finally {
            try {
                if (directoryReader != null) {
                    directoryReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}