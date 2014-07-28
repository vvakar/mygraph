package vvakar.graph.components;

import org.junit.Before;
import vvakar.graph.interfaces.Vertex;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public class SimpleDirectedGraphTest extends AbstractSimpleGraphTest {
    @Before
    public void before() {
        graph = new SimpleDirectedGraph<Vertex>();
        e = new DirectedEdge<Vertex>(v1, v2);
    }
}
