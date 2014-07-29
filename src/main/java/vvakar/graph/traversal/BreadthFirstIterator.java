package vvakar.graph.traversal;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Iterate breadth-first over the supplied graph. Has no notion of edge weight.
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
            VertexWeightBeans<V> ns = graph.getNeighborsOf(currentVertex);
            for(VertexWeightBean<V> vw : ns) {
                V vertex = vw.getVertex();
                if(!seen.contains(vertex)) {
                    queue.addLast(vertex);
                    seen.add(vertex);
                }
            }

            currentVertex = queue.isEmpty() ? null : queue.removeFirst();
        }
        return previous;
    }
}
