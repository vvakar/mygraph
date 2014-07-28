package vvakar.graph.structure;

/**
 * "As opposed to a multigraph, a simple graph is an undirected graph that has no loops (edges connected at both ends to
 * the same vertex) and no more than one edge between any two different vertices. In a simple graph the edges of the graph
 * form a set (rather than a multiset) and each edge is a distinct pair of vertices. In a simple graph with n vertices
 * every vertex has a degree that is less than n (the converse, however, is not true — there exist non-simple graphs
 * with n vertices in which every vertex has a degree smaller than n)."
 * - <link>http://en.wikipedia.org/wiki/Graph_(mathematics)#Undirected_graph</link>
 *
 * @author vvakar
 *         Date: 7/27/14
 */
public class SimpleGraph<V extends Vertex> extends AbstractGraph<V, UndirectedEdge<V>> {
    @Override
    public void put(UndirectedEdge<V> e) {
        if(get(e).isPresent()) {
            throw new IllegalArgumentException("Edge " + e + " already exists in graph. Simple Graph allows no duplicates.");
        }
        super.put(e);
    }
}
