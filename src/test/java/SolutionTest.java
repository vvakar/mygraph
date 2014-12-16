import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 12/16/14
 */
public class SolutionTest {
    @Test
    public void test() {
        assertEquals(8, Solution.doWork(new int[] {3,1, 2, 5, 4}));
    }
}
