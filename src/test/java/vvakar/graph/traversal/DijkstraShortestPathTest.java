package vvakar.graph.traversal;

import com.sun.istack.internal.NotNull;
import org.junit.Before;
import org.junit.Test;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.GraphFactory;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

import java.util.List;

/**
 * @author vvakar
 *         Date: 7/30/14
 */
public class DijkstraShortestPathTest {
    Graph nonEmptyGraph;
    Vertex v1 = vertex("v1"), v2 = vertex("v2"), v3 = vertex("v3"), v4 = vertex("v4"), v5 = vertex("v5");
    DirectedEdge e12 = directedEdge(v1, v2, 3), e13 = directedEdge(v1, v3, 10), e25 = directedEdge(v2, v5, 123456),
            e34 = directedEdge(v3, v4, 9), e45 = directedEdge(v4, v5, 1);


    @Before
    public void before() {
        nonEmptyGraph = new SimpleDirectedGraph<Vertex>();
        /*
             v1 --3-> v2 --123456-> v5
               \10                  ^
                \__> v3 --9-> v4  / 1
        */

        nonEmptyGraph.put(e12);
        nonEmptyGraph.put(e13);
        nonEmptyGraph.put(e34);
        nonEmptyGraph.put(e25);
        nonEmptyGraph.put(e45);
    }

    @Test
    public void testOne() {
        Vertex start = GraphFactory.vertex("V1");
        Graph graph = new SimpleDirectedGraph<Vertex>();
        DijkstraShortestPath dsp = new DijkstraShortestPath<Vertex, Edge<Vertex>>(graph);
        List<DijkstraShortestPath.DijkstraBean<Vertex>> list = dsp.getShortestPath(start, start);
        assertEquals(1, list.size());
        assertEquals(start, list.get(0).getDestination());
        assertEquals(0, list.get(0).getWeight());
    }

    @Test
    public void testNoPath() {
        DijkstraShortestPath dsp = new DijkstraShortestPath<Vertex, Edge<Vertex>>(nonEmptyGraph);
        List<DijkstraShortestPath.DijkstraBean<Vertex>> list = dsp.getShortestPath(v2, v1);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testMulti() {
        DijkstraShortestPath dsp = new DijkstraShortestPath<Vertex, Edge<Vertex>>(nonEmptyGraph);
        List<DijkstraShortestPath.DijkstraBean<Vertex>> list = dsp.getShortestPath(v1, v5);
        assertEquals(4, list.size());
        assertEquals(v1, list.get(0).getDestination());
        assertEquals(v5, list.get(3).getDestination());
        assertEquals(0, list.get(0).getWeight());
        assertEquals(20, list.get(3).getWeight());
    }
}
