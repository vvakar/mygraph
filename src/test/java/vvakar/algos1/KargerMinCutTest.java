package vvakar.algos1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static vvakar.algos1.KargerGraph.*;

public class KargerMinCutTest {
    private KargerGraph graph;

    @Before
    public void before() {
        graph = new KargerGraph();

    }

    @Test
    public void testSupportsDuplicateEdges() {
        graph.recordEdge("x", "y");
        graph.recordEdge("x", "y");
        graph.recordEdge("x", "y");

        assertEquals(3, graph.verticesToEdges.get("x").size());
        assertEquals(3, graph.verticesToEdges.get("y").size());
    }

    @Test
    public void testSupportsContraction() {
        graph.recordEdge("a", "b");
        graph.recordEdge("a", "c");
        graph.recordEdge("a", "d");
        graph.recordEdge("c", "d");

        int expectedEdgeCount = 0;

        for (Edge e : graph.verticesToEdges.get("a")) {
            if (!(e.a.equals("a") && e.b.equals("b")) || (e.b.equals("a") && e.a.equals("b"))) {
                ++expectedEdgeCount;
            }
        }
        for (Edge e : graph.verticesToEdges.get("b")) {
            if (!(e.a.equals("a") && e.b.equals("b")) || (e.b.equals("a") && e.a.equals("b"))) {
                ++expectedEdgeCount;
            }
        }

        String newKey = graph.contractVertices("a", "b");

        assertFalse(graph.verticesToEdges.containsKey("a"));
        assertFalse(graph.verticesToEdges.containsKey("b"));
        assertEquals(expectedEdgeCount, graph.verticesToEdges.get(newKey).size());
    }

    @Test
    public void test() {
        graph.recordEdge("a", "b");
        graph.recordEdge("a", "c");
        graph.recordEdge("a", "d");
        graph.recordEdge("c", "d");
        assertEquals(1, graph.minCut());
    }

    @Test
    public void test2() {
        graph.recordEdge("a", "b");
        graph.recordEdge("a", "c");
        graph.recordEdge("a", "d");
        graph.recordEdge("b", "c");
        graph.recordEdge("b", "d");
        graph.recordEdge("c", "d");

        graph.recordEdge("e", "f");
        graph.recordEdge("e", "g");
        graph.recordEdge("e", "h");
        graph.recordEdge("f", "g");
        graph.recordEdge("f", "h");
        graph.recordEdge("g", "h");

        graph.recordEdge("b", "e");
        graph.recordEdge("d", "g");

        assertEquals(2, graph.minCut());
    }

    @Test
    public void test3() {
        graph.recordEdge("a", "b");
        graph.recordEdge("a", "c");
        graph.recordEdge("a", "d");
        graph.recordEdge("b", "c");
        graph.recordEdge("b", "d");
        graph.recordEdge("c", "d");

        graph.recordEdge("e", "f");
        graph.recordEdge("e", "g");
        graph.recordEdge("e", "h");
        graph.recordEdge("f", "g");
        graph.recordEdge("f", "h");
        graph.recordEdge("g", "h");

        graph.recordEdge("b", "e");
        graph.recordEdge("e", "b");// dupe
        graph.recordEdge("d", "g");

        assertEquals(3, graph.minCut());
    }


    @Test
    public void testTc1() throws Exception {
       assertEquals(2, parseGraph("KargerMinCuttc1.txt").minCut());
    }

    @Test
    public void testTc2() throws Exception {
       assertEquals(1, parseGraph("KargerMinCuttc2.txt").minCut());
    }

    @Test
    public void testTc3() throws Exception {
       assertEquals(3, parseGraph("KargerMinCuttc3.txt").minCut());
    }
}
