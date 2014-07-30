package vvakar.graph.traversal;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Generic graph iteration skeleton. Tracks already visited vertices and has the notion of a current vertex.
 * Disallows removals in order to enforce the preconceived notion that immutability is better.
 * @author vvakar
 *         Date: 7/28/14
 */
public abstract class AbstractGraphIterator<V extends Vertex, E extends Edge> implements Iterator<V> {
    protected final Graph<V,E> graph;
    protected final Set<V> seen = new HashSet<V>();
    protected V currentVertex;

    /**
     * Traverse graph starting with arbitrarily chosen vertex.
     * @param graph not null
     */
    public AbstractGraphIterator(Graph<V, E> graph) {
        this(graph, graph.getVertices().isEmpty() ? null : (V) graph.getVertices().iterator().next());
    }

    /**
     * Traverse graph starting at vertex indicated by <code>startingPoint</code>
     * @param graph not null
     * @param startingPoint null only if there are no elements in the graph
     */
    public AbstractGraphIterator(@NotNull Graph<V, E> graph, @Nullable V startingPoint) {
        Preconditions.checkNotNull(graph);
        Preconditions.checkArgument((startingPoint != null || graph.getVertices().isEmpty()));
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
