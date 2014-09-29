package vvakar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.Test;
import vvakar.util.Util;

import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 9/27/14
 */
public class UtilTest {

    @Test
    public void testAllKSubsets() {
        Set<Set<Integer>> allSubsets = Util.allKSubsets(ImmutableSet.of(1,2,3), 2);
        assertEquals(3, allSubsets.size());
        assertEquals(ImmutableSet.of(
                ImmutableSet.of(1,2),
                ImmutableSet.of(1,3),
                ImmutableSet.of(2,3)
        ), allSubsets);

        allSubsets = Util.allKSubsets(ImmutableSet.of(1,2,3), 3);
        assertEquals(1, allSubsets.size());
        assertEquals(ImmutableSet.of(
                ImmutableSet.of(1,2,3)
        ), allSubsets);

        allSubsets = Util.allKSubsets(ImmutableSet.of(1,2,3), 1);
        assertEquals(3, allSubsets.size());
        assertEquals(ImmutableSet.of(
                ImmutableSet.of(1),
                ImmutableSet.of(2),
                ImmutableSet.of(3)
        ), allSubsets);
    }

    @Test
    public void testgetItemsMatchingBits() {
        List<Integer> ints = ImmutableList.of(1,2,3,4,5,6,7,8,9,10);
        assertArrayEquals(new Integer[] {1,2}, Util.getItemsMatchingBits(ints, 3).toArray(new Integer[0]));
        assertArrayEquals(new Integer[] {4}, Util.getItemsMatchingBits(ints, 8).toArray(new Integer[0]));
        assertArrayEquals(new Integer[] {1}, Util.getItemsMatchingBits(ints, 1).toArray(new Integer[0]));
        assertArrayEquals(new Integer[] {10}, Util.getItemsMatchingBits(ints, 1 << 9).toArray(new Integer[0]));
    }

    @Test
    public void testCountBits() {
        assertEquals(2, Util.countBits(6));
        assertEquals(3, Util.countBits(7));
        assertEquals(1, Util.countBits(1));
        assertEquals(0, Util.countBits(0));
    }
}
