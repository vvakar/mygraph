import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vvakar
 *         Date: 12/20/14
 */
public class Solution_Tower {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String QcountStr = br.readLine().trim();
        long height = Long.parseLong(QcountStr);

        String ignore = br.readLine().trim();


            int[] Ks = strArray2intArray(br.readLine().split("\\s+"));
            System.out.println(doWork(height, Ks));

    }

    /**
     *  long - 2^63 =    1 000 000 000 000 000 000
     *
     *                                   000 000 000 000

     count =     1   1  0  0  1  2   1   0  1  3   3  1  1  4  6  4  2    5  10  10  6
     H =         4   5  6  7  8  9  10  11  12 13 14 15 16 17 18 19  20  21  22  23 24 25



     windowSize = max(Ks)
     window = new long[windowSize];
     count = window[H - k (% windowSize)]

     Ks     = 4 5
     k      = 4
     counts =
     h      = 0
     total = 0
                                   4  0  1  2  3
               counts[h % maxK] =  1  1 0+? 0  0
                          h =      4  5  6  7  9  9

                          k = 5


      55554 55545 55455 54555 45555

     1GHz = 1e9 operations / sec
     1e18 operations => 1e9 sec
    //
     */
    public static long doWork(final long H, final int[] Ks) {
        Arrays.sort(Ks);
        final int maxK = Ks[Ks.length - 1];
//        final BigInteger[] counts = new BigInteger[maxK];
        long counts[] = new long[maxK];

        for(int i = 0; i <= maxK; ++i) {
//            counts[i%maxK] = BigInteger.valueOf(getCounts(i, Ks));
            counts[i%maxK] = getCounts(i, Ks);
        }

        for(long h = maxK + 1; h <= H; ++h) {
//            BigInteger total = BigInteger.ZERO;
            long total = 0;
            for(int k : Ks) {
//                final BigInteger val = counts[(int)(Math.abs(h - k) % maxK)];
                final long val = counts[(int)(Math.abs(h - k) % maxK)];
                total = (total + val) ;
            }

            counts[(int)(h % maxK)] = total % ((long)Math.pow(10,9) + 7);
        }

//        return counts[(int) (H % maxK)].mod(BigInteger.valueOf((long)Math.pow(10,9) + 7)).longValue();
        return counts[(int) (H % maxK)];
    }


    private static long getCounts(long H, int[]Ks) {
        if(H <= 0) return 0;

        long count = 0;
        for(int k : Ks) {
            if(k == H) count += 2;
            count += getCounts(H - k, Ks);
        }
        return count;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }


}
