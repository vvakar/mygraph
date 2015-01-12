import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vvakar
 *         Date: 1/10/15
 */
public class Solution_Knapsack {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

        for (int i = 0; i < Qcount; ++i) {
            int[] numsSize = strArray2intArray( br.readLine().trim().split("\\s+"));
            int K = numsSize[1];
            int[] arr = strArray2intArray(br.readLine().trim().split("\\s+"));

            MEMO = new HashMap<Integer, Integer>();
            System.out.println(doWork(arr, K, 0));
        }
    }

    /*
    K = 12
    arr = 1 6 9

    1 1 1 1 1 1 1 1 1 1 1 1 = 12


     doWork(12)  -> doWork(11) , doWork(6),  doWork(3) -> doWork(3)

     doWork(3) -> doWork(2), doWork(-3), doWork(-6)

     */
    private static Map<Integer, Integer> MEMO;
    private static int doWork(int[]arr, int K, int sumSoFar) {
        if(arr == null || arr.length == 0) return 0;
        if(K < sumSoFar) return 0;

        Integer found;
        if((found = MEMO.get(sumSoFar)) != null) return found;

        int maxSum = Integer.MIN_VALUE;

        for(int e : arr) {
            maxSum = Math.max(maxSum, Math.max(doWork(arr, K, sumSoFar + e), sumSoFar));
        }

        MEMO.put(sumSoFar, maxSum);
        return maxSum;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i].trim());
        }
        return ret;
    }

}
