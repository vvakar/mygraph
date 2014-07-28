package vvakar.graph.components;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.interfaces.Vertex;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class SimpleUndirectedGraphTest extends AbstractSimpleGraphTest {
    @Before
    public void before() {
        graph = new SimpleUndirectedGraph<Vertex>();
        e = new UndirectedEdge<Vertex>(v1, v2);
    }

}
