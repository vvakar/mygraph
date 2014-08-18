package vvakar.graph.traversal;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 8/17/14
 */
public class BellmanFordTest {
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
        List<BellmanFord.BellmanFordDistanceBean> list = BellmanFord.compute(graph, start, start);
        assertEquals(1, list.size());
        assertEquals(start, list.get(0).getVertex());
        assertEquals(start, list.get(0).getVia());
        assertEquals(0, list.get(0).getTotalWeight());
    }

    @Test
    public void testNoPath() {
        List<BellmanFord.BellmanFordDistanceBean> list = BellmanFord.compute(nonEmptyGraph, v2, v1);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testMulti() {
        List<BellmanFord.BellmanFordDistanceBean<Vertex>> list = BellmanFord.compute(nonEmptyGraph, v1, v5);
        assertEquals(4, list.size());
        assertEquals(v1, list.get(0).getVertex());
        assertEquals(v5, list.get(3).getVertex());
        assertEquals(0, list.get(0).getTotalWeight());
        assertEquals(20, list.get(3).getTotalWeight());
    }

    @Test(expected = RuntimeException.class)
    public void testHw1() throws Exception {
        BellmanFord.compute(Util.getGraph("graph1.txt"), vertex("1"), vertex("2"));
    }

    @Test(expected = RuntimeException.class)
    public void testHw2() throws Exception {
        BellmanFord.compute(Util.getGraph("graph2.txt"), vertex("1"), vertex("2"));
    }

    @Test
    public void testHw3() throws Exception {
        BellmanFord.compute(Util.getGraph("graph3.txt"), vertex("1"), vertex("2"));
    }

}
