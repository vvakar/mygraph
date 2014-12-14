import javax.management.Query;
import java.io.*;
import java.util.*;

public class Solution_AlmostEqual {
    private static Map<String, Long> MEMO = new HashMap<String,Long>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLineStrs = br.readLine().split("\\s+");
        int[] firstLine = strArray2intArray(firstLineStrs);
        int N = firstLine[0];
        int K = firstLine[1];

        String[] secondLineStrs = br.readLine().split("\\s+");
        int[] H = strArray2intArray(secondLineStrs);

        String QcountStr = br.readLine();
        int Qcount = Integer.parseInt(QcountStr);

        final List<Query> queries = new ArrayList<Query>(Qcount);
        for(int i = 0; i < Qcount; ++i) {
            String[] QStr = br.readLine().split("\\s+");
            long[] Qs = strArray2longArray(QStr);
            int l = (int)Qs[0];
            int r = (int)Qs[1];
            queries.add(new Query(l, r));
        }

        final long[] results = doWork_scan(H, queries, K);
        for(long r : results) {
            System.out.println(r);
        }
    }

    static long[] doWork_scan(int[]H, List<Query> queries, int K) {
        long[] counts = new long[queries.size()];

        for(int i = 0; i < queries.size(); ++i) {
            counts[i] = countPairs_scan(H, queries.get(i).l, queries.get(i).r, K);
        }
        return counts;
    }

    static final class Query {
        private final int l, r;
        public Query(int l, int r) {
            this.l = l; this.r = r;
        }
    }

    private static final class IndexValue {
        public final int index;
        public final long value;
        public IndexValue(int i, long v) {
            this.index = i;
            this.value = v;
        }
    }

    public static int countPairs_scan(int[] N, int originalL, int r, int K) {
        int count = 0;

        for(int l = originalL; l < r; ++l) {
            for (int i = l + 1 /* don't count self */; i <= r; ++i) {
                if (Math.abs(N[i] - N[l]) <= K) {
                    ++count;
                }
            }
        }

        return count;
    }

    private static String getKey(int l, int i) { return l + "_" + i; }

    public static int countPairs_binarySearch(long[] N, int l, int r, int K) {
        int count = 0;

        // 1. sort range
        long[] subset = Arrays.copyOfRange(N, l, r + 1);    // 17s
        Arrays.sort(subset); // 6:28s

        // 2. for each long, find its count
        for(int i = 0; i < subset.length - 1; ++i) {
            count += countSmallerOrEqualTo(subset[i] + K, subset, i + 1);
        }

        return count;
    }

    private static int countSmallerOrEqualTo(long val, long[] set, int start) {
        int cusp = findOffsetCusp(set, start, set.length - 1, val);
        return cusp - start;
    }

    /**
     * Return the offset where too-big numbers start via binary search.
     * If all are bigger, return 0.
     */
    private static int findOffsetCusp(long[] set, int start, int end, long val) {
        final int retval;
        if(val < set[start]) retval = start;
        else if(val >= set[end]) retval = end + 1;
        else if(val >= set[start] && val < set[start + 1]) retval = start + 1; // found cusp
        else {
            int half = start + (end - start) / 2;
            if (val < set[half]) {
                retval = findOffsetCusp(set, start, half, val);
            } else {
                retval = findOffsetCusp(set, half, end, val);
            }
        }

        return retval;
    }

    private static long[] strArray2longArray(String[] strs) {
        long[] ret = new long[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Long.parseLong(strs[i]);
        }
        return ret;
    }
    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}