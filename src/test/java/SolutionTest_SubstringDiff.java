import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 12/20/14
 */
public class SolutionTest_SubstringDiff {
    @Test
    public void test() {
        assertEquals(3, Solution_SubstringDiff.doWork(1, "asf", "asf"));
        assertEquals(3, Solution_SubstringDiff.doWork(0, "asf", "asf"));
        assertEquals(3, Solution_SubstringDiff.doWork(1, "adf", "asf"));
    }
    @Test
    public void testDistance1() {
        assertEquals(3, Solution_SubstringDiff.doWork(1, "asfg", "bdfg"));
    }
}
