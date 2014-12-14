package vvakar;

import org.junit.Before;
import org.junit.Test;
import vvakar.tree.SegmentTree;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * @author vvakar
 *         Date: 12/13/14
 */
public class SegmentTreeTest {

    @Test
    public void testMaxSegmentTree() {
        // 0 1 2 3 4  5  6  7  8  9 10
        long[] arr = new long[] {9,3,7,1,8,12,10,20,15,18,5};
        int[] keys = new int[arr.length];
        TreeMap<Integer, Long> map = new TreeMap<Integer, Long>();
        for(int i = 0; i < arr.length; ++i) {
            map.put(i, arr[i]);
            keys[i] = i;
        }

        SegmentTree tree = new SegmentTree(map, keys);
        assertEquals(12, tree.query(1,6));
        assertEquals(12, tree.query(0,5));
        assertEquals(12, tree.query(3,5));
        assertEquals(12, tree.query(4,5));
        assertEquals(12, tree.query(4,6));
        assertEquals(12, tree.query(5,6));
        assertEquals(12, tree.query(5,5));
        assertEquals(20, tree.query(0,10));

        tree.update(1, 100);
        assertEquals(100, tree.query(1,5));
        assertEquals(100, tree.query(0,5));
        assertEquals(12, tree.query(3,5));
        assertEquals(12, tree.query(4,5));
        assertEquals(12, tree.query(5,5));
        assertEquals(100, tree.query(0,10));

    }

}
