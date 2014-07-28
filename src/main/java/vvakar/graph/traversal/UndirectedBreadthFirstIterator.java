package vvakar.graph.traversal;
import vvakar.graph.structure.Edge;
import vvakar.graph.structure.Graph;
import vvakar.graph.structure.Vertex;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Iterate breadth-first over the supplied graph.
 * @author vvakar
 *         Date: 7/27/14
 */
public class UndirectedBreadthFirstIterator implements Iterator<Vertex> {
    private final Graph<Vertex,Edge> graph;
    private final Set<Vertex> seen = new HashSet<Vertex>();
    private Vertex currentVertex;
    private Deque<Vertex> queue = new ArrayDeque<Vertex>();

    /**
     * Traverse graph starting with arbitrarily chosen vertex.
     * @param graph
     */
    public UndirectedBreadthFirstIterator(Graph graph) {
        this(graph, graph.getVertices().isEmpty() ? null : (Vertex) graph.getVertices().iterator().next());
    }

    /**
     * Traverse graph starting at vertex indicated by <code>startingPoint</code>
     * @param graph
     * @param startingPoint
     */
    public UndirectedBreadthFirstIterator(Graph graph, @Nullable Vertex startingPoint) {
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
    public Vertex next() {
        Vertex previous = currentVertex;
        if(currentVertex != null) {
            Collection<Vertex> ns = graph.getNeighborsOf(currentVertex);
            for(Vertex v : ns) {
                if(!seen.contains(v)) {
                    queue.addLast(v);
                    seen.add(v);
                }
            }

            currentVertex = queue.isEmpty() ? null : queue.removeFirst();
        }
        return previous;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
