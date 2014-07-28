package vvakar.graph.traversal;

import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public abstract class AbstractGraphIterator<V extends Vertex, E extends Edge> implements Iterator<V> {
    protected final Graph<V,E> graph;
    protected final Set<V> seen = new HashSet<V>();
    protected V currentVertex;

    /**
     * Traverse graph starting with arbitrarily chosen vertex.
     * @param graph
     */
    public AbstractGraphIterator(Graph<V, E> graph) {
        this(graph, graph.getVertices().isEmpty() ? null : (V) graph.getVertices().iterator().next());
    }

    /**
     * Traverse graph starting at vertex indicated by <code>startingPoint</code>
     * @param graph
     * @param startingPoint
     */
    public AbstractGraphIterator(Graph<V, E> graph, @Nullable V startingPoint) {
        this.graph = graph;
        this.currentVertex = startingPoint;

        if(startingPoint != null)
            seen.add(startingPoint);
    }

    @Override
    public boolean hasNext() {
        return currentVertex != null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
