import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 1/10/15
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

//        String[] strs = new String[Qcount];
        for (int i = 0; i < Qcount; ++i) {
            String QStr = br.readLine();
//            strs[i] = QStr.trim();
            System.out.println(
                    removeToMakePalindrome(QStr));

        }

    }

    /*
      aaab

      - work from ends to middle
      - if not symmetric, we have to remove one
        - try both removals - one has to work

     */
    private static int removeToMakePalindrome(String str) {

        int length = str.length();
        for(int i = 0; i < length / 2; ++i) {
            char left = str.charAt(i);
            char right = str.charAt(length - 1 - i);
            if(left != right) {
                if(str.charAt(i + 1) == right) return i;
                else if(str.charAt(length - 2 - i) == left) return length - 1 - i;
                else throw new IllegalStateException();
            }

        }

        return -1;
    }

}
