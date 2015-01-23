import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 1/23/15
 */
public class Solution_StockMaximize {

    public static void main(String[]asdf) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int loops = Integer.parseInt(br.readLine().trim());

        for(int i = 0; i < loops; ++i) {
            Integer.parseInt(br.readLine().trim());
            String[]strs = br.readLine().trim().split("\\s+");
            System.out.println(doWork(strArray2intArray(strs)));
        }
    }


    private static long doWork(int[]arr) {
        int i = 0;
        long shares = 0;
        long balance = 0;

        int[] biggestNumbersAfter = preprocess(arr);
        for(int a : arr) {
            if(biggestNumbersAfter[i] > a) {
                balance -= a;
                ++shares;
            } else {
                balance += shares * a;
                shares = 0;
            }
            ++i;
        }
        return balance > 0 ? balance : 0L;
    }

    private static int[] preprocess(int[]arr) {
        int[] ret = new int[arr.length];
        int max = Integer.MIN_VALUE;
        ret[arr.length - 1] = max;
        for(int i = arr.length - 2; i >= 0; --i) {
            ret[i] = max = Math.max(arr[i+1], max);
        }
        return ret;
    }



    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i].trim());
        }
        return ret;
    }

}
