package vvakar;

import org.junit.Test;
import vvakar.tree.MinMaxSegmentTree;
import vvakar.tree.SumSegmentTree;

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
        for(int i = 0; i < arr.length; ++i) {
            keys[i] = i;
        }

        MinMaxSegmentTree tree = new MinMaxSegmentTree(keys);

        for(int i = 0; i < arr.length; ++i) {
            tree.update(i, arr[i]);
            keys[i] = i;
        }

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
    @Test
    public void testSumSegmentTree() {
                              // 0 1 2 3 4  5  6  7  8  9 10
        long[] arr = new long[] {9,3,7,1,8,12,10,20,15,18,5};
        int[] keys = new int[arr.length];
        for(int i = 0; i < arr.length; ++i) {
            keys[i] = i;
        }

        SumSegmentTree tree = new SumSegmentTree(keys);

        for(int i = 0; i < arr.length; ++i) {
            tree.update(i, arr[i]);
            keys[i] = i;
        }

        assertEquals(41, tree.query(1,6));
        assertEquals(40, tree.query(0,5));
        assertEquals(21, tree.query(3,5));
        assertEquals(20, tree.query(4,5));
        assertEquals(30, tree.query(4,6));
        assertEquals(22, tree.query(5,6));
        assertEquals(12, tree.query(5,5));
        assertEquals(108, tree.query(0,10));

        tree.update(1, 100);
        assertEquals(128, tree.query(1,5));
        assertEquals(137, tree.query(0,5));
        assertEquals(21, tree.query(3,5));
        assertEquals(20, tree.query(4,5));
        assertEquals(12, tree.query(5,5));
        assertEquals(205, tree.query(0,10));

    }

}
