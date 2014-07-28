package vvakar.graph.components;

import vvakar.graph.interfaces.Vertex;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class GraphFactory {
    private GraphFactory() {}

    public static Vertex vertex(String name) {
        return new SimpleVertex(name);
    }

    public static UndirectedEdge undirectedEdge(Vertex v1, Vertex v2) {
        return new UndirectedEdge<Vertex>(v1, v2);
    }
    public static DirectedEdge directedEdge(Vertex v1, Vertex v2) {
        return new DirectedEdge<Vertex>(v1, v2);
    }
}
