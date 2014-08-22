package vvakar.graph.mst;

import com.google.common.collect.Sets;
import com.google.common.io.LineReader;
import org.junit.Before;
import org.junit.Test;
import vvakar.graph.Util;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.GraphFactory;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 8/17/14
 */
public class PrimsAlgoTest {
    Graph nonEmptyGraph;
    Vertex v1, v2, v3, v4, v5;
    DirectedEdge e12, e13, e25,e34, e45;


    @Before
    public void before() {
        v1 = vertex("v1");
        v2 = vertex("v2");
        v3 = vertex("v3");
        v4 = vertex("v4");
        v5 = vertex("v5");

        e12 = directedEdge(v1, v2, 3);
        e13 = directedEdge(v1, v3, 10);
        e25 = directedEdge(v2, v5, 123456);
        e34 = directedEdge(v3, v4, 9);
        e45 = directedEdge(v4, v5, 1);


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
        Graph graph = new SimpleDirectedGraph<Vertex>();
        graph.put(directedEdge(vertex("V"),vertex("V"), 9));
        List<Edge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(0, PrimsAlgo.getTotalSize(list));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testNoPath() {
        Graph graph = new SimpleDirectedGraph<Vertex>();
        List<Edge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(0, PrimsAlgo.getTotalSize(list));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testConnected() {
        List<Edge<Vertex>> list = PrimsAlgo.compute(nonEmptyGraph);
        assertEquals(nonEmptyGraph.getVertices().size() -1, list.size());
        assertEquals(23, PrimsAlgo.getTotalSize(list));

    }

    @Test
    public void testMstHw() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getGraph("msthw.txt");
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(graph.getVertices().size() - 1, list.size());
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);

    }

    @Test
    public void testHw1() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getGraph("graph1.txt");
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(graph);

        assertEquals(graph.getVertices().size() - 1, list.size());
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHw2() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getGraph("graph2.txt");
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(graph.getVertices().size() - 1, list.size());
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHw3() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getGraph("graph3.txt");
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(graph.getVertices().size() - 1, list.size());
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHwMonster() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getGraph("graphmonster.txt");
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(graph);
        assertEquals(graph.getVertices().size() - 1, list.size());
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

}
