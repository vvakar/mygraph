package vvakar.nsum;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import vvakar.nsum.ThreeSum.Triple;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 12/26/14
 */
public class ThreeSumTest {
    @Test
    public void test() {
        Set<Triple> tuples = ThreeSum.getTriples(new int[] {1,2,3,4,5,6,7,8,9}, 11);
        assertEquals(ImmutableSet.of(new Triple(1,2,8), new Triple(2,3,6), new Triple(1,3,7), new Triple(1,4,6), new Triple(2,4,5)), tuples);

    }
}
