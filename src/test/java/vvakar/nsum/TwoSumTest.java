package vvakar.nsum;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import vvakar.nsum.TwoSum;
import vvakar.nsum.TwoSum.Pair;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static vvakar.nsum.TwoSum.Pair.*;

/**
 * @author vvakar
 *         Date: 12/26/14
 */
public class TwoSumTest {

    @Test
    public void test() {
        Set<TwoSum.Pair> pairs = TwoSum.getPairs(new int[] { 1,3,5,7,8,6,2,4,3}, 11);
        Set<Pair> expected = new HashSet<Pair>();
        expected.add(Pair(5, 6));
        expected.add(Pair(3, 8));
        expected.add(Pair(7, 4));
        assertEquals(expected, pairs);
    }
}
