import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vvakar
 *         Date: 1/10/15
 */
public class Solution_MaxSubarray {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

        for (int i = 0; i < Qcount; ++i) {
            br.readLine();
            int[] arr = strArray2intArray(br.readLine().trim().split("\\s+"));
            System.out.println(doWork(arr));
        }
    }

    /*
                1 2 3 4 -5 3 8


                10 -5 11 -22 -3 1 4 -4 5 2

                contiguousSumSoFar = 16 | 0
                current = 11


             maxContiguous = 16

     */
    private static String doWork(int[]raw) {
        int maxTotal = 0, maxContiguous = 0;
        int contiguousSumSoFar = 0;

        boolean foundPositive = false;

        int arr[] = preprocess(raw);

        for(int current : arr) {
            if(current > 0) {
                foundPositive = true;
                maxTotal += current;
            }

            contiguousSumSoFar += current;
            if(contiguousSumSoFar < 0)
                contiguousSumSoFar = 0;

            maxContiguous = Math.max(maxContiguous, contiguousSumSoFar);

        }

        if(!foundPositive) {
            maxContiguous = maxTotal = Integer.MIN_VALUE;
            for(int i : raw) {
                maxContiguous = maxTotal = Math.max(maxContiguous, i);
            }
        }

        return maxContiguous + " " + maxTotal;
    }


    /*
        1 2 3 -3 -2 -1 5 6 7
     */
    private static int[] preprocess(int []arr) {
        if(arr.length <= 1) return arr;

        List<Integer> ints = new ArrayList<Integer>();

        int a = arr[0];
        int sum = a;
        for(int i = 1; i < arr.length; ++i) {
            a = arr[i];

            if(Math.signum(a) == Math.signum(sum)) sum += a;
            else {
                ints.add(sum);
                sum = a;
            }
        }

        ints.add(sum);

        int[] retval = new int[ints.size()];
        int i = 0;
        for(int b : ints) {
            retval[i++] = b;
        }
        return retval;
    }


    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i].trim());
        }
        return ret;
    }

}
