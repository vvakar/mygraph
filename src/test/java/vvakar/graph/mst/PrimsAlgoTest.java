package vvakar.graph.mst;

import com.google.common.collect.Sets;
import com.google.common.io.LineReader;
import org.junit.Before;
import org.junit.Test;
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
        Graph graph = new SimpleDirectedGraph<Vertex>();
        graph.put(directedEdge(v3, v3, 9));
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
    public void testHw1() throws Exception {
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(getGraph("graph1.txt"));
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHw2() throws Exception {
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(getGraph("graph2.txt"));
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHw3() throws Exception {
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(getGraph("graph3.txt"));
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    @Test
    public void testHwMonster() throws Exception {
        List<DirectedEdge<Vertex>> list = PrimsAlgo.compute(getGraph("graphmonster.txt"));
        int totalSize = PrimsAlgo.getTotalSize(list);
        System.out.println("Total size: " + totalSize);
    }

    private static SimpleDirectedGraph<Vertex> getGraph(String s ) throws IOException {
        LineReader lineReader = getReader(s);
        SimpleDirectedGraph<Vertex> graph = new SimpleDirectedGraph<Vertex>();

        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            // tail head weight
            graph.put(new DirectedEdge<Vertex>(vertex(vals[0]), vertex(vals[1]), Integer.parseInt(vals[2])));
        }

        assertEquals(firstLine[0], graph.getVertices().size() + "");
        assertEquals(firstLine[1], graph.getEdges().size() + "");

        return graph;
    }

    private static LineReader getReader(String s) throws IOException{
        return new LineReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResource(s).openStream()));
    }
}
