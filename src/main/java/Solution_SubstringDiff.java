import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * @author vvakar
 *         Date: 12/22/14
 */
public class Solution_SubstringDiff {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine();
        int Qcount = Integer.parseInt(QcountStr);

        for(int i = 0; i < Qcount; ++i) {
            String[] line = br.readLine().split("\\s+");
            System.out.println(doWork(Integer.parseInt(line[0]), line[1], line[2]));
        }
    }

    /*

        torino                A
        012345
           tungnrin                     B
           01234567

        A[5] vs B[0]
        A[4,5] vs B[0,1]
        A[3,4,5) vs B[0,1,2]


          5 0 0 1
          4 0 0    1      1
          3 1           1
          2           1
          1
     As ^ 0 1
     Bs ->  0 1 2 3 4 5 6 7


  5 0 0 0 0 0 0 0 0
  4 0 0 1 0 1 0 0 1
  3 0 0 0 0 0 0 1 0
  2 0 0 0 0 0 1 0 0
  1 0 0 0 0 0 0 0 0
  0 1 0 0 0 0 0 0 0
    0 1 2 3 4 5 6 7

    d = 2
                           5
                         5 4
                       5 4 3
                     5 4 3 2   2
                   5 4 3 2 1 1 1
  i start  =     5 4 3 2 1 0 0 0
  j start  =     0 0 0 0 0 0 1 2
                   1 1 1 1 1 0 1
                     2 2 2 2   0
                       3 3 3
                         4 4
                           5


                           */


    public static int doWork(final int distance, String left, String right) {

        int maxCount = 0;

        for(int i = left.length() - 1; i >= 0; --i) {
            int a = i, b = 0;
            final MaxCounter counter = new MaxCounter(left, right, distance);
            for(; a < left.length() && b < right.length(); ++a, ++b) {
                counter.inspect(a, b);
            }

            maxCount = Math.max(maxCount, counter.getMaxCount());
        }

        for(int i = 0; i < right.length(); ++i) {
            final MaxCounter counter = new MaxCounter(left, right, distance);
            int b = i, a = 0;
            for(; a < left.length() && b < right.length(); ++a, ++b) {
                counter.inspect(a, b);
            }
            maxCount = Math.max(maxCount, counter.getMaxCount());
        }

        return maxCount;
    }


    private static final class MaxCounter {
        private int maxCount;
        private final String left, right;
        private final int distance;
        private int quota;
        private int count;

        public MaxCounter(String left, String right, int distance) {this.left = left; this.right = right; this.distance = distance; this.quota = distance;}

        public void inspect(int a, int b) {

            if(left.charAt(a) == right.charAt(b)) {
                ++count; // freebie
            } else if(quota > 0) {
                --quota; ++count; // goes against quota but still counts
            } else {
                // stuck, but we have some buffer - drop earliest mismatch to free up some quota so we can advance
                boolean augmentedQuota = false;
                while(!augmentedQuota && count > 0) {
                    if(left.charAt(a - count) != right.charAt(b - count)) {
                        augmentedQuota = true;
                    }
                    --count;
                }

                if(augmentedQuota) {
                    ++count; // count current mismatch after quota augmentation
                } else {
                    // out of quota and totally stuck - just start over
                    count = 0;
                    quota = distance;
                }
            }

            maxCount = Math.max(maxCount, count);
        }

        public int getMaxCount() {
            return maxCount;
        }
    }
}