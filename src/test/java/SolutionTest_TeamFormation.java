import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 12/8/14
 */
public class SolutionTest_TeamFormation {
    @Test
    public void testFirst() {

        assertEquals(3, Solution_Team_Formation.solveIt(new int[]{3, 1, 2, 3}));


        assertEquals(2, Solution_Team_Formation.solveIt(new int[]{3, 0, 1, 2, 2, 3, 3}));

        assertEquals(4, Solution_Team_Formation.solveIt(new int[]{12, 1, 2, 1, 2, 1, 2, 3, 4, 3, 4, 3, 4}));

        assertEquals(4, Solution_Team_Formation.solveIt(new int[]{8, 1, 2, 3, 3, 4, 4, 5, 6}));

        assertEquals(3, Solution_Team_Formation.solveIt(new int[]{8, 1, 2, 3, 3, 4, 4, 5, 6, 12, 13, 14}));
        assertEquals(4, Solution_Team_Formation.solveIt(new int[]{8, 101, 102, 103, 103, 104, 104, 105, 106}));

        assertEquals(3, Solution_Team_Formation.solveIt(new int[]{7, 4, 5, 2, 3, -4, -3, -5}));
        assertEquals(1, Solution_Team_Formation.solveIt(new int[]{1, -4}));
        assertEquals(1, Solution_Team_Formation.solveIt(new int[]{4, 3, 2, 3, 1}));
        assertEquals(7, Solution_Team_Formation.solveIt(new int[]{7, 1, -2, -3, -4, 2, 0, -1}));





    }
}
