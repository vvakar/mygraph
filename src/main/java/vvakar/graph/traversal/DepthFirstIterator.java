package vvakar.graph.traversal;

import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Iterate depth-first over the supplied graph. Has no notion of edge weight.
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
            Iterable<VertexWeightBean<V>> ns = graph.getNeighborsOf(currentVertex);
            for(VertexWeightBean<V> wb : ns) {
                V vertex = wb.getVertex();
                if(!seen.contains(vertex)) {
                    stack.push(vertex);
                    seen.add(vertex);
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
