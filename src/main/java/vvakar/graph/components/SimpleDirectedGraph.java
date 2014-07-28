package vvakar.graph.components;
import vvakar.graph.interfaces.Vertex;

/**
 * "A directed graph (or digraph) is a graph, or set of nodes connected by edges, where the edges have a direction
 * associated with them..."
 * "A digraph is called "simple" if it has no loops, and no multiple arcs (arcs with same starting and ending nodes)."
 *   - <link>http://en.wikipedia.org/wiki/Directed_graph</link>
 *
 * @author vvakar
 *         Date: 7/28/14
 */
public class SimpleDirectedGraph<V extends Vertex> extends SimpleAbstractGraph<V, DirectedEdge<V>> {
}
