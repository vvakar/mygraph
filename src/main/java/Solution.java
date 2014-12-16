import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 12/16/14
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

//        for (int i = 0; i < Qcount; ++i) {
//            String QStr = br.readLine();
//            System.out.println(doWork(QStr));
//
//        }

        int[] ar = strArray2intArray(br.readLine().split("\\s+"));
        System.out.println(doWork(ar));
    }


    public static int doWork(int[]arr) {
        return 0;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
