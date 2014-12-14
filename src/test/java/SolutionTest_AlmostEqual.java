import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 12/1/14
 */
public class SolutionTest_AlmostEqual {
    static int MAX_K = 1000 * 1000 * 1000;
    static int MAX_Q = 100 * 1000;
    static int MAX_N = 100 * 1000;
    @Test
    public void testCountPairs() {

        assertArrayEquals(new long[] {1}, Solution_AlmostEqual.doWork_scan(new int[]{1, 3, 4, 3, 0}, ImmutableList.of(new Solution_AlmostEqual.Query(0, 1)), 2));
        assertArrayEquals(new long[] {3}, Solution_AlmostEqual.doWork_scan(new int[]{1, 3, 4, 3, 0}, ImmutableList.of(new Solution_AlmostEqual.Query(1, 3)), 2));
        assertArrayEquals(new long[] {6}, Solution_AlmostEqual.doWork_scan(new int[]{1, 3, 4, 3, 0}, ImmutableList.of(new Solution_AlmostEqual.Query(0, 4)), 2));
    }

//    @Test
//    public void testBigScan() {
//        int r = MAX_N - 1;
//        long[] H = new long[r + 1];
//        for(int i = 0; i <= r; ++i) {
//            H[i] = i;
//        }
//
//
//            Solution.doWork_scan(H, 0, r, MAX_K);
//
//    }


}
