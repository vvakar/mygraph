package vvakar.graph.components;

import org.junit.Test;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public abstract class AbstractSimpleGraphTest {
    protected Vertex v1 = GraphFactory.vertex("v1");
    protected Vertex v2 = GraphFactory.vertex("v2");
    protected Graph graph;
    Edge<Vertex> e;

    @Test
    public void testGetNeighborsOf_empty() {
        VertexWeightBeans<Vertex> ns = graph.getNeighborsOf(v1);
        assertTrue(ns.isEmpty());
    }
    @Test
    public void testGetNeighborsOf_nonempty() {
        graph.put(e);
        VertexWeightBeans<Vertex> ns = graph.getNeighborsOf(v1);
        assertEquals(1, ns.size());
        assertEquals(v2, ns.iterator().next().getVertex());
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
