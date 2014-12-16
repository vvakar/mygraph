import java.io.*;
import java.util.*;

public class Solution_AlmostEqual {
    private static Map<String, Integer> MEMO = new HashMap<String, Integer>();

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

        long start = System.currentTimeMillis();
        final List<Query> queries = new ArrayList<Query>(Qcount);
        for (int i = 0; i < Qcount; ++i) {
            String[] QStr = br.readLine().split("\\s+");
            long[] Qs = strArray2longArray(QStr);
            int l = (int) Qs[0];
            int r = (int) Qs[1];
            queries.add(new Query(l, r));
        }

        for (long r : doWork_scan(H, queries, K)) {
            System.out.println(r);
        }

        System.out.println("Completed in " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * The only reusable computations are the overlaps
     * 1. find overlaps and pre-compute them
     * 2. brute-force compute the rest
     * <p/>
     * <p/>
     * input =
     * <p/>
     * 2Q - 1 nodes:
     * must keep balanced to maintain O(log q)
     * <p/>
     * 5-200
     * 5-70
     * all Qs =          5-57 30-70  100-200
     * <p/>
     * <p/>
     * 2              7
     * 4                9
     * <p/>
     * overlap = 4,7  = max(l, lastL), min(r, lastR)
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * overlaps = {30-57}
     * <p/>
     * 100m ~= 100, 000, 000
     * <p/>
     * k = 2
     * 3,9 = 1
     * <p/>
     * 3,8
     * <p/>
     * <p/>
     * <p/>
     * 7-9
     * 4-5   7-9
     * 1-1   1-5
     * <p/>
     * 2    4    5    6    3    2   12    4    4    3    8      9
     * 0    1    2    3    4    5    6    7    8    9    10    11
     */

    static int[] doWork_scan(int[] N, List<Query> queries, int K) {
        List<Query> allOverlappingQueries = getOverlappingQueries(queries);
//        precompute(N, allOverlappingQueries, K);

        int[] arr = new int[queries.size()];
        for(int i = 0; i < queries.size(); ++i) {
            arr[i] = countPairs_scan(N, queries.get(i).l, queries.get(i).r, K);
        }
        return arr;
    }

    private static void precompute(int[]N, List<Query> queries, int K) {
        int count = 0;
        for(Query q: queries) {
            for(int l = q.l; l < q.r; l += 100) {
                MEMO.put(getKey(l, q.r), countPairs_scan(N, l, q.r, K));
                debug("Cached " + ++count);
            }
        }
    }

    private static String getKey(int a, int b) {
        return a + "-" + b;
    }

    private static List<Query> getOverlappingQueries(List<Query> queriesUnsorted) {
                /*
             *  1. sort by starting offset
     *  2. lastL = lastR = -1
     *  3. allOverlaps = {}
     *     prevOverlap = -1,-1

     *  4. take first Q. Overlap = max(l, lastL), min(r, lastR)
     *     if(prevOverlap overlaps overlap)
     *          overlap = cumulativeOverlap(prevOverlap, overlap)
     *     else
     *          allOverlaps.add(prevOverlap)
     *
     *     prevL = l, prevR = r
     *     prevOverlap = overlap
         */
        List<Query> queries = new ArrayList<Query>(queriesUnsorted);
        Collections.sort(queries, new Comparator<Query>() {
            @Override
            public int compare(Query o1, Query o2) {
                int first = Integer.compare(o1.l, o2.l);
                if(first == 0) {
                    return Integer.compare(o1.r, o2.r);
                }
                return first;
            }
        });

        List<Query> allOverlaps = new ArrayList<Query>();
        int lastL = -1, lastR = Integer.MAX_VALUE;
        Query prevOverlap = new Query(-1, -1);

        for (Query q : queries) {
            int overlapL = Math.max(lastL, q.l);
            int overlapR = Math.min(lastR, q.r);

            if (overlapsOrIsContiguous(new Query(overlapL, overlapR), prevOverlap)) {
                prevOverlap.r = Math.max(prevOverlap.r, overlapR); // extend overlapping segment
                prevOverlap.l = Math.min(prevOverlap.l, overlapL);
            } else {
                // break in the overlap - save this overlap segment and on to the next one
                if (prevOverlap.l != -1)
                    allOverlaps.add(prevOverlap);

                prevOverlap.l = overlapL;
                prevOverlap.r = overlapR;
            }

            lastL = q.l;
            lastR = q.r;
        }
        allOverlaps.add(prevOverlap); // one more time to capture the last one
        return allOverlaps;
    }

    private static boolean overlapsOrIsContiguous(Query q1, Query q2) {
        return q1.l <= q2.r && q2.l <= q1.r;
    }


static final class Query {
    private int l, r;

    public Query(int l, int r) {
        this.l = l;
        this.r = r;
    }
}

    static int h = 0;
    public static int countPairs_scan(int[] N, int originalL, int r, int K) {
        int cachehits = 0;
        Integer b = MEMO.get(getKey(originalL, r));
        if(b != null) {
            if(++cachehits % 500 ==0 )debug("Cache hits " + cachehits);
            return b;
        }

        int count = 0;

        for (int l = r + 1; l >= originalL  ; --l) {

            b = MEMO.get(getKey(l, r));
            if(b != null) {
                if(++cachehits % 500 ==0 )debug("Cache hits " + cachehits);
                count += b;
            } else {
                for (int i = l + 1 /* don't count self */; i <= r; ++i) {
                    if (Math.abs(N[i] - N[l]) <= K) {
                        ++count;
                    }
                }
            }
        }

        return count;
    }

    private static long[] strArray2longArray(String[] strs) {
        long[] ret = new long[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Long.parseLong(strs[i]);
        }
        return ret;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }

    static void debug(String d) {
        System.err.println("-------- " + d);
    }
}