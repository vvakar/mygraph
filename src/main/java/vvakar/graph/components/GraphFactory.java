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

    public static <V extends Vertex> UndirectedEdge<V> undirectedEdge(V v1, V v2) {
        return new UndirectedEdge<V>(v1, v2);
    }
    public static <V extends Vertex> DirectedEdge directedEdge(V v1, V v2) {
        return new DirectedEdge<V>(v1, v2);
    }
    public static <V extends Vertex> DirectedEdge directedEdge(V v1, V v2, int weight) {
        return new DirectedEdge<V>(v1, v2, weight);
    }
}
