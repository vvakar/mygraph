package vvakar.graph.structure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class SimpleGraphTest {
    Vertex v1 = new Vertex("v1");
    Vertex v2 = new Vertex("v2");
    UndirectedEdge<Vertex> e = new UndirectedEdge<Vertex>(v1, v2);

    private SimpleGraph<Vertex> graph;

    @Before
    public void before() {
        graph = new SimpleGraph();
    }
    @Test
    public void testgetNeighborsOf_empty() {
        Collection<Vertex> ns = graph.getNeighborsOf(v1);
        assertTrue(ns.isEmpty());
    }
    @Test
    public void testgetNeighborsOf_nonempty() {
        graph.put(e);
        Collection<Vertex> ns = graph.getNeighborsOf(v1);
        assertEquals(1, ns.size());
        assertEquals(v2, ns.iterator().next());
    }

    @Test
    public void testGet_empty() {
        assertFalse(graph.get(e).isPresent());
    }

    @Test
    public void testGet_nonempty() {
        graph.put(e);
        assertEquals(e, graph.get(e).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisallowsDuplicateEdges() {
        graph.put(e);
        graph.put(e);
    }
}
