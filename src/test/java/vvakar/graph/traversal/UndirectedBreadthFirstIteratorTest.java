package vvakar.graph.traversal;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.interfaces.Vertex;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class UndirectedBreadthFirstIteratorTest extends AbstractUndirectedIteratorTest {
    @Before
    public void before() {
        super.before();
    }

    @Test
    public void testEmpty() {
        assertFalse(new UndirectedBreadthFirstIterator(emptyGraph).hasNext());
    }

    @Test
    public void testNonEmpty() {
        UndirectedBreadthFirstIterator iterator = new UndirectedBreadthFirstIterator(nonEmptyGraph, v1);
        assertTrue(iterator.hasNext());
        assertEquals(v1, iterator.next());
        Vertex v2orv3 = iterator.next();
        assertTrue(v2.equals(v2orv3) || v3.equals(v2orv3));
        v2orv3 = iterator.next();
        assertTrue(v2.equals(v2orv3) || v3.equals(v2orv3));
        assertEquals(v4, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
