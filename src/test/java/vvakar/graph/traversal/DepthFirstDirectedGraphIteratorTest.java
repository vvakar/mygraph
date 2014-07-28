package vvakar.graph.traversal;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.interfaces.Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public class DepthFirstDirectedGraphIteratorTest extends AbstractDirectedGraphIteratorTest {
    @Before
    public void before() {
        super.before();
    }

    @Test
    public void testEmpty() {
        assertFalse(new DepthFirstIterator(emptyGraph).hasNext());
    }

    @Test
    public void testNonEmpty() {
        DepthFirstIterator iterator = new DepthFirstIterator(nonEmptyGraph, v1);
        assertTrue(iterator.hasNext());
        assertEquals(v1, iterator.next());
        Vertex v2orv3 = iterator.next();
        assertTrue(v2.equals(v2orv3) || v3.equals(v2orv3));

        Vertex v4orv5 = iterator.next();
        assertTrue(v4.equals(v4orv5) || v5.equals(v4orv5));

        v2orv3 = iterator.next();
        assertTrue(v2.equals(v2orv3) || v3.equals(v2orv3));

        v4orv5 = iterator.next();
        assertTrue(v4.equals(v4orv5) || v5.equals(v4orv5));

        assertFalse(iterator.hasNext());
    }
}
