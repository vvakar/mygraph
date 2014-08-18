package vvakar.graph;

import com.google.common.io.LineReader;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Vertex;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 8/18/14
 */
public class Util {

    public static SimpleDirectedGraph<Vertex> getGraph(String s ) throws IOException {
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
