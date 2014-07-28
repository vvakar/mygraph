package vvakar.graph.traversal;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.structure.Edge;
import vvakar.graph.structure.Graph;
import vvakar.graph.structure.SimpleGraph;
import vvakar.graph.structure.Vertex;
import static vvakar.graph.structure.GraphFactory.*;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class UndirectedBreadthFirstIteratorTest {

    Graph graph;

    /*
                 v1 --- v2 --- v4
                   \
                    \__ v3
     */
    Vertex v1 = vertex("v1"), v2 = vertex("v2"), v3 = vertex("v3"), v4 = vertex("v4");
    Edge e12 = undirectedEdge(v1, v2), e13 = undirectedEdge(v1, v3), e24 = undirectedEdge(v2, v4);

    @Before
    public void before() {
        graph = new SimpleGraph();

    }

    @Test
    public void testEmpty() {
        assertFalse(new UndirectedBreadthFirstIterator(graph).hasNext());
    }

    @Test
    public void testNonEmpty() {
        graph.put(e12);
        graph.put(e13);
        graph.put(e24);
        UndirectedBreadthFirstIterator iterator = new UndirectedBreadthFirstIterator(graph, v1);
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
