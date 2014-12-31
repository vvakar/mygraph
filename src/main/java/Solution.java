import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author vvakar
 *         Date: 12/22/14
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine();
        int Qcount = Integer.parseInt(QcountStr);

        for(int i = 0; i < Qcount; ++i) {
            String ignore = br.readLine();
            int[] arr = strArray2intArray(br.readLine().split("\\s+"));
            System.out.println(doWork(arr));
        }
    }

    final static int MOD = (int)Math.pow(10,9)  + 7;

    static HashMap<BigInteger, Integer> MEMO;

    static int doWork(int[] arr) {
        MEMO = new HashMap<BigInteger, Integer>();
        MEMO.put(BigInteger.ZERO, 0);
        int sum = 0;
        /*

        N = str.length
        D = 3
        abcdabcabcdabdafvbadab


         ND + ND + ND ... = NND


         1  2  3

      sum = 1+2+3

      2  3
      1  2
      0  1
         1  2  3
                           1   2   12  3  13   23  123
         mask 1 - 7       001 010 011 100 101 110 111


         001 -> 1
         011 = f(001) ^ arr[010]

         111 = MEMO.get(011) ^ arr[100]


         1100101101 = MEMO.get(100101101) ^ arr[9]

         */

        for(BigInteger mask = BigInteger.ONE; mask.compareTo(BigInteger.ONE.shiftLeft(arr.length)) < 0; mask = mask.add(BigInteger.ONE)) {
            int highestBit = mask.bitLength() - 1;
            BigInteger maskMinusHighestBit = mask.flipBit(highestBit);
            int xord = MEMO.get(maskMinusHighestBit) ^ arr[highestBit];

            MEMO.put(mask, xord);
            sum = (xord + sum) % MOD;
        }

        return sum;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }

}