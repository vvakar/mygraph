package vvakar.coverage;

import org.junit.Test;

/**
 * @author vvakar
 *         Date: 2/1/15
 */
public class CoverageTest {
    @Test
    public void testCyclomatic() {
        Coverage.twoFlags(true, false);
    }
}
