import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * Figure out where to buy and where to sell for maxiumum profit:
 *
 *       1 3 5 2 6 -1 3 5 0 9
 */
public class Study_LowHigh {
    public static void main(String[]args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = strArray2intArray(in.readLine().split("\\s+"));

        int low = arr[0];
        int hi = arr[1];
        int min = Math.min(low, hi);


        for(int i = 2; i < arr.length; ++i) {
            int current = arr[i];

            if(current - min > hi - low) {
                hi = current;
                low = min;
            }
            min = Math.min(current, min);
        }

        System.out.println("Buy: " + low + ", sell: " + hi);
    }


    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
