
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by valentin.vakar on 10/19/15.
 */
public class Solution_BricksGame {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String QcountStr = br.readLine().trim();
        long Qcount = Long.parseLong(QcountStr);

        for (int i = 0; i < Qcount; ++i) {
            br.readLine();
            long[] ar = strArray2intArray(br.readLine().split("\\s+"));
            System.out.println(doWork(ar));
        }
    }

    private static long doWork(long[] arr) {
        MEMO = new HashMap<Integer, Pair<Integer, Long>>();
        return maximize(arr, 0).getValue();
    }

    static Map<Integer, Pair<Integer, Long>> MEMO;

    private static Pair<Integer, Long> maximize(long[] arr, int i) {
        if (arr.length - i <= 0) return new Pair(0, 0L);
        if (arr.length - i == 1) return new Pair(1, arr[i]);
        if (arr.length - i == 2) return new Pair(2, arr[i] + arr[i + 1]);
        if (arr.length - i == 3) return new Pair(3, arr[i] + arr[i + 1] + arr[i + 2]);

        if (MEMO.containsKey(i)) return MEMO.get(i);

        Pair<Integer, Long> x1, x2, x3;
        x1 = new Pair(1, arr[i] + maximize(arr, i + 1 + maximize(arr, i + 1).getKey()).getValue());
        x2 = new Pair(2, arr[i] + arr[i + 1] + maximize(arr, i + 2 + maximize(arr, i + 2).getKey()).getValue());
        x3 = new Pair(3, arr[i] + arr[i + 1] + arr[i + 2] + maximize(arr, i + 3 + maximize(arr, i + 3).getKey()).getValue());

        Pair<Integer, Long> x = max(x1, x2, x3);
        MEMO.put(i, x);
        return x;
    }

    private static Pair<Integer, Long> max(Pair<Integer, Long>... arr) {
        Pair<Integer, Long> max = null;
        for (Pair<Integer, Long> v : arr) {
            if (max == null || max.getValue() < v.getValue())
                max = v;
        }
        return max;
    }

    private static long[] strArray2intArray(String[] strs) {
        long[] ret = new long[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Long.parseLong(strs[i]);
        }
        return ret;
    }

    private static final class Pair<K, V> {
        private final K k;
        private final V v;
        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }
        public K getKey() { return k; }
        public V getValue() { return v; }
    }
}
