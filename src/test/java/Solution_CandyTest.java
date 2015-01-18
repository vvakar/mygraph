import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 1/17/15
 */
public class Solution_CandyTest {
    @Test
    public void testBasic() {
        assertEquals(0, Solution_Candy.doWork(new int[]{}));
        assertEquals(1, Solution_Candy.doWork(new int[]{1}));
    }

    @Test
    public void test2() {
        assertEquals(3, Solution_Candy.doWork(new int[]{1,2}));
        assertEquals(4, Solution_Candy.doWork(new int[]{1,2,2}));
    }


    @Test
    public void test3() {                          //    1 2 1 2 1 2 3 4 2 1
        assertEquals(19, Solution_Candy.doWork(new int[]{2,4,2,6,1,7,8,9,2,1}));
    }

    @Test
    public void testEqualValues() {
        assertEquals(4, Solution_Candy.doWork(new int[]{2,2,2,2}));
    }


    @Test
    public void testPlateau() {
                                                     //  1 2 1 1 1 1 2 3
        assertEquals(12, Solution_Candy.doWork(new int[]{3,4,2,2,2,2,3,4}));
    }

    @Test
    public void testPlateauStart() {
        assertEquals(8, Solution_Candy.doWork(new int[]{2,2,2,3,4}));
    }

    @Test
    public void testPlateauEnd() {
        assertEquals(7, Solution_Candy.doWork(new int[]{3,4,2,2,2,2}));
    }

}
