import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 12/9/14
 */
public class SolutionSubsequenceWeightingTest {
    private static final int MAX_N = 150000;
    @Test
    public void test() {
        assertEquals(0, Solution_Subsequence_Weighting.solveIt(new int[]{}, new int[]{})); // empty input test
        assertEquals(100, Solution_Subsequence_Weighting.solveIt(new int[]{1, 2, 3, 4}, new int[]{10, 20, 30, 40}));
        assertEquals(110, Solution_Subsequence_Weighting.solveIt(new int[]{1, 2, 3, 4, 1, 2, 3, 4}, new int[]{10, 20, 30, 40, 15, 15, 15, 50}));
        assertEquals(110, Solution_Subsequence_Weighting.solveIt(new int[]{100, 200, 300, 400, 100, 200, 300, 400}, new int[]{10, 20, 30, 40, 15, 15, 15, 50}));
        assertEquals(95, Solution_Subsequence_Weighting.solveIt(new int[]{400, 100, 200, 300, 400}, new int[]{40, 15, 15, 15, 50}));
        assertEquals(4000, Solution_Subsequence_Weighting.solveIt(new int[]{400, 100, 200, 300, 400}, new int[]{4000, 15, 15, 15, 50}));
        assertEquals(4L * Integer.MAX_VALUE, Solution_Subsequence_Weighting.solveIt(new int[]{1, 2, 3, 4}, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    public void test2() {
        assertEquals(95, Solution_Subsequence_Weighting.solveIt(new int[]{400, 100, 200, 300, 400}, new int[]{40, 15, 15, 15, 50}));
    }

    @Test
    public void testMaxSegmentTree() {
                            // 0 1 2 3 4  5  6  7  8  9 10
        long[] arr = new long[] {9,3,7,1,8,12,10,20,15,18,5};
        int[] keys = new int[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            keys[i] = i;
        }

        Solution_Subsequence_Weighting.MaxSegmentTree tree = new Solution_Subsequence_Weighting.MaxSegmentTree(keys);

        for(int i = 0; i < arr.length; ++i) {
            tree.update(i, arr[i]);
            keys[i] = i;
        }


        assertEquals(12, tree.query(1,6));
        assertEquals(12, tree.query(0,5));
        assertEquals(12, tree.query(3,5));
        assertEquals(12, tree.query(4,5));
        assertEquals(12, tree.query(4,6));
        assertEquals(12, tree.query(5,6));
        assertEquals(12, tree.query(5,5));
        assertEquals(20, tree.query(0,10));

        tree.update(1, 100);
        assertEquals(100, tree.query(1,5));
        assertEquals(100, tree.query(0,5));
        assertEquals(12, tree.query(3,5));
        assertEquals(12, tree.query(4,5));
        assertEquals(12, tree.query(5,5));
        assertEquals(100, tree.query(0,10));

    }


    @Test
//    @Ignore
    public void testbig() {
        int[] As = new int[MAX_N];
        int[] Ws = new int[MAX_N];
        long expected = 0L;
        for(int i = 0; i<As.length; ++i) {
            As[i] = i;
            Ws[i] = Integer.MAX_VALUE - i;
            expected += Ws[i];
        }
        assertEquals(expected, Solution_Subsequence_Weighting.solveIt(As, Ws));

    }
}
