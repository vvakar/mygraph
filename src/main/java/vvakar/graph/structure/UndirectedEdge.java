package vvakar.graph.structure;

/**
 * An undirected edge connects two vertices without direction or weight.
* @author vvakar
*         Date: 7/27/14
*/
class UndirectedEdge<V extends Vertex> extends AbstractEdge<V> {
    UndirectedEdge(V v1, V v2) {
        super(v1, v2);
    }
}
