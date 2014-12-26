import org.junit.Test;
import vvakar.graph.traversal.AbstractDirectedGraphIteratorTest;

import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 12/16/14
 */
public class SolutionTest_AlmostSorted {
    @Test
    public void test() {                      //   1  1  2   2+1   1
        assertEquals(8, Solution_AlmostSorted.doWork(new int[]{3, 1, 2, 5, 4}));



        /*



6     X
5     X
4
3           X  X  X  X
2     X              X
1           X  X  X  X
   1  2  3  4  5  6  7


           - get most recent i, where a[i] >= current
               - dimensions - along sequence, value


           - get all bumps < current - TreeMap
         */
        assertEquals(2202, Solution_AlmostSorted.doWork(new int[]{1, 4, 3, 5, 9, 7, 10, 6, 2, 11, 8, 12, 13, 17, 14, 15, 16, 18, 19, 20, 21, 22, 24, 26, 27, 23, 25, 28, 29, 32, 31, 30, 33, 34, 37, 36, 35, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 52, 50, 49, 51, 53, 54, 55, 56, 57, 58, 59, 60, 62, 65, 63, 61, 68, 69, 64, 66, 71, 72, 67, 73, 70, 74, 75, 76, 77, 82, 79, 80, 81, 78, 83, 87, 84, 88, 85, 86, 89, 91, 90, 92, 93, 94, 95, 96, 99, 98, 100, 97}));
    }
}
