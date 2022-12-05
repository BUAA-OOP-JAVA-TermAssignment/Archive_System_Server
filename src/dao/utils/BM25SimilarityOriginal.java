package dao.utils;


import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.BytesRef;

import java.util.ArrayList;
import java.util.List;

/**
 * The original version of BM25 Similarity you can find in textbooks.
 * It was copied and adapted from Lucene's BM25Similarity with the following changes:
 * 1. Storing and using the full accuracy document length;
 * 2. Using the original BM25 IDF function but set the min to 0;
 * 3. Multiplying term weights by (k1+1), which was ignored in Lucene (but does not affect ranking).
 */
public class BM25SimilarityOriginal extends Similarity {

    private final float k1;
    private final float b;

    /**
     * BM25 with the supplied parameter values.
     *
     * @param k1 Controls non-linear term frequency normalization (saturation).
     * @param b  Controls to what degree document length normalizes tf values.
     * @throws IllegalArgumentException if {@code k1} is infinite or negative, or if {@code b} is
     *                                  not within the range {@code [0..1]}
     */
    public BM25SimilarityOriginal(float k1, float b) {
        if (!Float.isFinite(k1) || k1 < 0) {
            throw new IllegalArgumentException("illegal k1 value: " + k1 + ", must be a non-negative finite value");
        }
        if (Float.isNaN(b) || b < 0 || b > 1) {
            throw new IllegalArgumentException("illegal b value: " + b + ", must be between 0 and 1");
        }
        this.k1 = k1;
        this.b = b;
    }

    /**
     * BM25 with these default values:
     * <ul>
     *   <li>{@code k1 = 1.2}</li>
     *   <li>{@code b = 0.75}</li>
     * </ul>
     */
    public BM25SimilarityOriginal() {
        this(1.2f, 0.75f);
    }

    /**
     * IDF = log (N-n+0.5)/(n+0.5)
     * <p>
     * If N-n+0.5<n+0.5, return 0 (do not count query terms that appeared in over half of the documents).
     */
    protected float idf(long docFreq, long docCount) {
        double div = (docCount - docFreq + 0.5D) / (docFreq + 0.5D);
        return div <= 1 ? 0 : (float) Math.log(div);
    }

    /**
     * The default implementation returns <code>1</code>
     */
    protected float scorePayload(int doc, int start, int end, BytesRef payload) {
        return 1;
    }

    /**
     * The default implementation computes the average as <code>sumTotalTermFreq / docCount</code>
     */
    protected float avgFieldLength(CollectionStatistics collectionStats) {
        return (float) (collectionStats.sumTotalTermFreq() / (double) collectionStats.docCount());
    }

    @Override
    public final long computeNorm(FieldInvertState state) {
        final int numTerms;
        if (state.getIndexOptions() == IndexOptions.DOCS && state.getIndexCreatedVersionMajor() >= 8) {
            numTerms = state.getUniqueTermCount();
        } else {
            numTerms = state.getLength();
        }
        return numTerms;
    }

    /**
     * Computes a score factor for a simple term and returns an explanation
     * for that score factor.
     *
     * <p>
     * The default implementation uses:
     *
     * <pre class="prettyprint">
     * idf(docFreq, docCount);
     * </pre>
     * <p>
     * Note that {@link CollectionStatistics#docCount()} is used instead of
     * {@link org.apache.lucene.index.IndexReader#numDocs() IndexReader#numDocs()} because also
     * {@link TermStatistics#docFreq()} is used, and when the latter
     * is inaccurate, so is {@link CollectionStatistics#docCount()}, and in the same direction.
     * In addition, {@link CollectionStatistics#docCount()} does not skew when fields are sparse.
     *
     * @param collectionStats collection-level statistics
     * @param termStats       term-level statistics for the term
     * @return an Explain object that includes both an idf score factor
     * and an explanation for the term.
     */
    public Explanation idfExplain(CollectionStatistics collectionStats, TermStatistics termStats) {
        final long df = termStats.docFreq();
        final long docCount = collectionStats.docCount();
        final float idf = idf(df, docCount);
        return Explanation.match(idf, "idf, computed as max( 0, log( (N - n + 0.5) / (n + 0.5) ) ) from:",
                Explanation.match(df, "n, number of documents containing term"),
                Explanation.match(docCount, "N, total number of documents with field"));
    }

    /**
     * Computes a score factor for a phrase.
     *
     * <p>
     * The default implementation sums the idf factor for
     * each term in the phrase.
     *
     * @param collectionStats collection-level statistics
     * @param termStats       term-level statistics for the terms in the phrase
     * @return an Explain object that includes both an idf
     * score factor for the phrase and an explanation
     * for each term.
     */
    public Explanation idfExplain(CollectionStatistics collectionStats, TermStatistics termStats[]) {
        double idf = 0d; // sum into a double before casting into a float
        List<Explanation> details = new ArrayList<>();
        for (final TermStatistics stat : termStats) {
            Explanation idfExplain = idfExplain(collectionStats, stat);
            details.add(idfExplain);
            idf += idfExplain.getValue().floatValue();
        }
        return Explanation.match((float) idf, "idf, sum of:", details);
    }

    @Override
    public final SimScorer scorer(float boost, CollectionStatistics collectionStats, TermStatistics... termStats) {
        Explanation idf = termStats.length == 1 ? idfExplain(collectionStats, termStats[0]) : idfExplain(collectionStats, termStats);
        float avgdl = avgFieldLength(collectionStats);
        return new BM25Scorer(boost, k1, b, idf, avgdl);
    }

    /**
     * Collection statistics for the BM25 model.
     */
    private static class BM25Scorer extends SimScorer {

        /**
         * query boost
         */
        private final float boost;

        /**
         * k1 value for scale factor
         */
        private final float k1;

        /**
         * b value for length normalization impact
         */
        private final float b;

        /**
         * BM25's idf
         */
        private final Explanation idf;

        /**
         * The average document length.
         */
        private final float avgdl;

        /**
         * weight ( idf * boost ) * ( k1 + 1 )
         */
        private final float weight;

        BM25Scorer(float boost, float k1, float b, Explanation idf, float avgdl) {
            this.boost = boost;
            this.idf = idf;
            this.avgdl = avgdl;
            this.k1 = k1;
            this.b = b;
            this.weight = (k1 + 1) * boost * idf.getValue().floatValue();
        }

        @Override
        public float score(float freq, long encodedNorm) {
            // In order to guarantee monotonicity with both freq and norm without
            // promoting to doubles, we rewrite freq / (freq + norm) to
            // 1 - 1 / (1 + freq * 1/norm).
            // freq * 1/norm is guaranteed to be monotonic for both freq and norm due
            // to the fact that multiplication and division round to the nearest
            // float. And then monotonicity is preserved through composition via
            // x -> 1 + x and x -> 1 - 1/x.
            // Finally we expand weight * (1 - 1 / (1 + freq * 1/norm)) to
            // weight - weight / (1 + freq * 1/norm), which runs slightly faster.
            float normInverse = 1f / (k1 * ((1 - b) + b * encodedNorm / avgdl));
            return weight - weight / (1f + freq * normInverse);
        }

        @Override
        public Explanation explain(Explanation freq, long encodedNorm) {
            List<Explanation> subs = new ArrayList<>(explainConstantFactors());
            Explanation tfExpl = explainTF(freq, encodedNorm);
            subs.add(tfExpl);
            float normInverse = 1f / (k1 * ((1 - b) + b * encodedNorm / avgdl));
            // not using "product of" since the rewrite that we do in score()
            // introduces a small rounding error that CheckHits complains about
            return Explanation.match(weight - weight / (1f + freq.getValue().floatValue() * normInverse),
                    "score(freq=" + freq.getValue() + "), computed as boost * idf * tf from:", subs);
        }

        private Explanation explainTF(Explanation freq, long norm) {
            List<Explanation> subs = new ArrayList<>();
            subs.add(freq);
            subs.add(Explanation.match(k1, "k1, term saturation parameter"));
            subs.add(Explanation.match(b, "b, length normalization parameter"));
            if ((norm & 0xFF) > 39) {
                subs.add(Explanation.match((float) norm, "dl, length of field (approximate)"));
            } else {
                subs.add(Explanation.match((float) norm, "dl, length of field"));
            }
            subs.add(Explanation.match(avgdl, "avgdl, average length of field"));
            float normInverse = 1f / (k1 * ((1 - b) + b * (float) norm / avgdl));
            return Explanation.match(
                    1f - 1f / (1 + freq.getValue().floatValue() * normInverse),
                    "tf, computed as freq / (freq + k1 * (1 - b + b * dl / avgdl)) from:", subs);
        }

        private List<Explanation> explainConstantFactors() {
            List<Explanation> subs = new ArrayList<>();
            // query boost
            if (boost != 1.0f) {
                subs.add(Explanation.match(boost, "boost"));
            }
            // idf
            subs.add(idf);
            return subs;
        }
    }

    @Override
    public String toString() {
        return "BM25(k1=" + k1 + ",b=" + b + ")";
    }

    public final float getK1() {
        return k1;
    }

    public final float getB() {
        return b;
    }

}