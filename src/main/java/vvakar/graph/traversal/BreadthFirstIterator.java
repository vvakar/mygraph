package vvakar.graph.traversal;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * Iterate breadth-first over the supplied graph.
 * @author vvakar
 *         Date: 7/27/14
 */
public class BreadthFirstIterator<V extends Vertex, E extends Edge> extends AbstractGraphIterator<V,E> {
    private Deque<V> queue = new ArrayDeque<V>();

    public BreadthFirstIterator(Graph<V, E> graph) {
        super(graph);
    }

    public BreadthFirstIterator(Graph<V, E> graph, V start) {
        super(graph, start);
    }

    @Override
    public V next() {
        V previous = currentVertex;
        if(currentVertex != null) {
            Collection<V> ns = graph.getNeighborsOf(currentVertex);
            for(V v : ns) {
                if(!seen.contains(v)) {
                    queue.addLast(v);
                    seen.add(v);
                }
            }

            currentVertex = queue.isEmpty() ? null : queue.removeFirst();
        }
        return previous;
    }
}
