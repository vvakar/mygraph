package vvakar.graph.traversal;

import org.junit.Before;
import org.junit.Test;
import vvakar.graph.Util;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 8/19/14
 */
public class FloydWarshallTest {
    Graph nonEmptyGraph;
    Vertex v1, v2, v3, v4, v5;
    DirectedEdge e12, e13, e25,e34, e45;


    @Before
    public void before() {
        v1 = vertex("1");
        v2 = vertex("2");
        v3 = vertex("3");
        v4 = vertex("4");
        v5 = vertex("5");

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
        long[][] arr = FloydWarshall.compute(graph);
//        assertEquals(0, FloydWarshall.getTotalSize(list));
//        assertTrue(list.isEmpty());
    }

    @Test
    public void testNoPath() {
        Graph graph = new SimpleDirectedGraph<Vertex>();
        long[][] arr = FloydWarshall.compute(graph);
//        assertEquals(0, FloydWarshall.getTotalSize(list));
//        assertTrue(list.isEmpty());
    }

    @Test
    public void testConnected() {

        long[][] arr = FloydWarshall.compute(nonEmptyGraph);
//        assertEquals(nonEmptyGraph.getVertices().size() -1, list.size());
//        assertEquals(23, FloydWarshall.getTotalSize(list));

    }

    @Test(expected = RuntimeException.class)
    public void testHw1() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getDirectedGraph("graph1.txt");
        long[][] arr = FloydWarshall.compute(graph);
        assertFalse(true);
    }

    @Test(expected = RuntimeException.class)
    public void testHw2() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getDirectedGraph("graph2.txt");
        long[][] arr = FloydWarshall.compute(graph);
        assertFalse(true);
    }

    @Test
    public void testHw3() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getDirectedGraph("graph3.txt");
        long[][] arr = FloydWarshall.compute(graph);

        long min = Long.MAX_VALUE;
        int x = -1, y = -1;
        for(int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[0].length; ++j) {
                if(i == j) continue;

                long candidate = arr[i][j];
                if(candidate < min) {
                    min = candidate;
                    x = i;
                    y = j;
                }
            }
        }

        Vertex v1 = (Vertex)FloydWarshall.idsToVertices.get(x);
        Vertex v2 = (Vertex)FloydWarshall.idsToVertices.get(y);

        assertFalse(true);
    }

    @Test
    public void testHwMonster() throws Exception {
        Graph<Vertex, DirectedEdge<Vertex>> graph = Util.getDirectedGraph("graphmonster.txt");
        long[][] arr = FloydWarshall.compute(graph);
        assertFalse(true);
//        assertEquals(graph.getVertices().size() - 1, list.size());
//        int totalSize = FloydWarshall.getTotalSize(list);
//        System.out.println("Total size: " + totalSize);
    }


}
