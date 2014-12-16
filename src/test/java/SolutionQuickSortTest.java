import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 12/16/14
 */
public class SolutionQuickSortTest {
    @Test
    public void testSort() {
        assertArrayEquals(new int[]{1,2,3,5,7,8,9}, Solution_QuickSort.quickSort(new int[]{1, 3, 9, 8, 2, 7, 5}));
        assertArrayEquals(new int[]{1,2,3,4,5,6,7,8}, Solution_QuickSort.quickSort(new int[]{8, 7, 6, 5, 4, 3, 2, 1}));
        assertEquals(8, Solution_QuickSort.countQuickSortSwaps(new int[]{1, 3, 9, 8, 2, 7, 5}));
    }

    @Test
    public void testInsertionSortCount() {
        assertEquals(9, Solution_QuickSort.countInsertionSortShifts(new int[]{1, 3, 9, 8, 2, 7, 5}));
        assertEquals(28, Solution_QuickSort.countInsertionSortShifts(new int[]{8, 7, 6, 5, 4, 3, 2, 1}));
    }
}
