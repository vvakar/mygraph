package vvakar.graph.traversal;

import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public class DepthFirstIterator<V extends Vertex, E extends Edge> extends AbstractGraphIterator<V,E> {
    private Deque<V> stack = new ArrayDeque<V>();

    public DepthFirstIterator(Graph<V, E> graph) {
        super(graph);
    }

    public DepthFirstIterator(Graph<V, E> graph, @Nullable V startingPoint) {
        super(graph, startingPoint);
    }

    @Override
    public boolean hasNext() {
        return currentVertex != null;
    }

    @Override
    public V next() {
        V previous = currentVertex;
        if(currentVertex != null) {
            Collection<V> ns = graph.getNeighborsOf(currentVertex);
            for(V v : ns) {
                if(!seen.contains(v)) {
                    stack.push(v);
                    seen.add(v);
                }
            }

            currentVertex = stack.isEmpty() ? null : stack.pop();
        }
        return previous;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
