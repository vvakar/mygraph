import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 1/12/15
 */
public class Solution_SamAndSubstrings {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(doWork(br.readLine().trim()));
    }

    private static String doWork(String N) {
        return sumSubstrings(N) + "";
    }

    /*     012
       N = 16342
       count =   1 + prefix(1) + 6 + prefix(6) + 3 + prefix(3) + 4 + prefix(4)       + 2 +         prefix(2)
                        0                16            63, 163      34, 634, 1634            42, 342, 6342, 16342


prefix(6) = (1 + 0) * 10 + 1 * 6 = 10 + 1*6  = 16

             6*10 + 16*10 + 2*3 = (6+16)*10 + 2*3  = 220 + 6
             (3 + 63 + 163) * 10 + 3*4 = 1290 + 3*4

prefix(3) = (6 + 16) * 10 + 2 * 3 = (10+6  + 6)*10 = 220 + 6 + 2*3
prefix(4) = (3 + 63 + 163) * 10 + 3*4 = (220 + 6 + 3)*10  + 3*4
     */
    private static final int MOD = (int)Math.pow(10,9) + 7;
    private static long sumSubstrings(String N) {
        if(N.length() == 0) return 0;

        long count = 0;
        long prevVal = 0;

        char[] chars = N.toCharArray();
        for(int i = 0; i< chars.length; ++i) {
            long num = chars[i] - '0';
            long currentVal = (prevVal*10 + i*num + num)  % MOD;
            count = (count + currentVal)% MOD;
            prevVal = currentVal;
        }

        return count;
    }
}
