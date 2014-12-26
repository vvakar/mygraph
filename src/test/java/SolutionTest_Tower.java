import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 12/20/14
 */
public class SolutionTest_Tower {
    @Test
    public void test() {
        assertEquals(2, Solution_Tower.doWork(1, new int[]{1}));
        assertEquals(4, Solution_Tower.doWork(5, new int[]{2, 3}));
        assertEquals(8, Solution_Tower.doWork(19, new int[]{4, 5}));
        assertEquals(12, Solution_Tower.doWork(24, new int[]{4, 5}));
        assertEquals(830, Solution_Tower.doWork(13, new int[]{1, 2, 8}));
/*
    Ks = 1 2 8

                      0   1   2   3   4   5   6   7
    counts[h%8] =     2   4   8   12  0   0   0   0

    h = 11
    k = 2
    [h - k % maxK]= 8
    [h - 2 % maxK]= 8
    counts [h - 1 % maxK], [h - 2 % maxK], [h - 8 % maxK] = counts[2], counts[1], counts[3] =  8,4,0


         */


        assertEquals(237506897, Solution_Tower.doWork(8418749856060372L, new int[]{5, 10, 1, 14, 11, 9, 6, 4, 8, 12, 3, 13, 2, 15, 7}));

    }
}
