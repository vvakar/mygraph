import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Study_MaximumSubarray {
    /**
     * Figure out where to buy and where to sell for maxiumum profit:
     * <p>
     * 1 3 5 2 6 -1 3 5 0 9
     */
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = strArray2intArray(in.readLine().split("\\s+"));

        int runningTotal = 0;
        int max = 0;
        int from = 0;
        int maxFrom = 0, maxTo =0;


        for (int i = 0; i < arr.length; ++i) {
            int current = arr[i];

            runningTotal += current;
            if(runningTotal < 0) {
                runningTotal = 0;
                from = i;
            }

            if(runningTotal > max) {
                max = runningTotal;
                maxFrom = from;
                maxTo = i;
            }
        }

        System.out.println("Max sum: " + max + ", from: " + maxFrom + " to: " + maxTo);
    }


    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
