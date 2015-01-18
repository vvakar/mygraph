import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vvakar
 *         Date: 1/17/15
 */
public class Solution_Candy {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int loops = Integer.parseInt(br.readLine().trim());

        int[]arr = new int[loops];
        for(int i = 0; i < loops; ++i) {
            arr[i] = (Integer.parseInt(br.readLine().trim()));
        }
        System.out.println(doWork(arr));
    }

    /*        0 1 2 3 4  5  6 7 8 9
        arr = 4 6 5 4 8 9 10 11 1 2
              1 3 2 1

        0-1 incr, 1-3 decr, 3-7 incr, 8-9 incr

         { 1 incr, 3 decr, 5 incr }, { 2 incr }


         3 decr, 2 incr, 7 decr, 5 incr, 2 incr


       - if incr, count from 1; last one = max(size, next size)
       - if decr, count back from size

       arr[1] > arr[0]
       arr[1] > arr[2]
       arr[0] can't go any lower => 1
       arr[8], arr[3] is a local minimum => 1


         if(arr[i] <= arr[i-1]) {
                currentBottom = i;
                currentIncr = 1;
                currentDecr++;
         } else {
                currentIncr++;
                currentDecr = 1;
         }

         if(currentIncr > maxIncr) maxIncr = currentIncr; maxIncrBottom = currentBottom;
         else if(currentDecr > maxDecr) maxDecr = currentDecr;


         // 2. set bottom = 1; work away from the bottom in both directions

     */

    private static int sumAll(int toInclusive) {
        return toInclusive * (toInclusive + 1)  / 2;
    }

    static int doWork(int[]arr) {
        if(arr.length == 0) return 0;
        if(arr.length == 1) return 1;

        boolean increasing = true;
        int prev = Integer.MIN_VALUE;
        int decreasingCount = 0, increasingCount = 0;
        int total = 1;
        // 2 4 2 6 1 7 8 9 2 1
        // 1 2 1 2 1 2 3 4 2 1
        for(int i = 0; i < arr.length; ++i) {
            int a = arr[i];
            if(a > prev) {
                if(increasing) { // was already increasing, ex. 3 2 4 6 | 8 9
                    ++increasingCount;
//                    total += increasingCount;
                } else { // local minimum, ex. 3 2 6 | 7
                    // sum up all but the biggest decreasing seq
                    // then add the max(decreasing seq size, previuos increasing seq size)
                    total += sumAll(decreasingCount - 1) + Math.max(decreasingCount, increasingCount);
                    increasingCount = 2; // prime incr count without adding it to total
                    increasing = true;
                }
            } else if(a < prev) {
                if(!increasing) { // already decreasing, ex. 2 4 6 5 | 4 3
                    ++decreasingCount;
//                    total += decreasingCount;
                } else { // detected local max, ex. 2 3 4 2 | 1
                    // sum up all but the biggest increasing seq
                    // then add the max(increasing seq size, previous decreasing seq size)
                    total += sumAll(increasingCount - 1) - 1;// don't count the first and the last
                    decreasingCount = 2; // prime decr count
                    increasing = false;
                }
            } else { // a == prev
                ++total;
                if(increasing)
                    total += sumAll(increasingCount) - 1;
                else
                    total += sumAll(decreasingCount - 1) + Math.max(decreasingCount, increasingCount);

                increasingCount = 1;
                decreasingCount = 0; // reset counters
                increasing = true;
            }
            prev = a;
        }

        if(increasing)
            total += sumAll(increasingCount) - 1;
        else
            total += sumAll(decreasingCount - 1) + Math.max(decreasingCount, increasingCount);

        return total;
    }


}
