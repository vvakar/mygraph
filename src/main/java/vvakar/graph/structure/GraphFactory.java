package vvakar.graph.structure;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public class GraphFactory {
    private GraphFactory() {}

    public static Vertex vertex(String name) {
        return new Vertex(name);
    }

    public static Edge undirectedEdge(Vertex v1, Vertex v2) {
        return new UndirectedEdge(v1, v2);
    }
}
