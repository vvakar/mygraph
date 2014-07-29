package vvakar.graph.traversal;

import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import javax.annotation.Nullable;

/**
 * @author vvakar
 *         Date: 7/29/14
 */
public class DijkstraShortestPathIterator <V extends Vertex, E extends Edge> extends AbstractGraphIterator<V,E> {

    public DijkstraShortestPathIterator(Graph<V, E> graph) {
        super(graph);
    }

    public DijkstraShortestPathIterator(Graph<V, E> graph, @Nullable V startingPoint) {
        super(graph, startingPoint);
    }

    @Override
    public V next() {
        if(true) throw new UnsupportedOperationException();
        return null;
    }
}
