package vvakar.graph.components;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.Map;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 7/29/14
 */
public class WeightedDirectedGraphTest  extends AbstractSimpleGraphTest {
    int weight = 3;

    @Before
    public void before() {
        graph = new SimpleDirectedGraph<Vertex>();
        e = new DirectedEdge<Vertex>(v1, v2, 3);
    }

    @Test
    public void testGetWeight() {
        graph.put(e);
        VertexWeightBeans<Vertex> weights = graph.getNeighborsOf(v1);

        assertEquals(1, weights.size());
        VertexWeightBean<Vertex> next = weights.iterator().next();
        assertEquals((long) weight, (long)next.getWeight());
        assertEquals(v2, next.getVertex());
    }
}
