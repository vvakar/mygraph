package vvakar.graph.interfaces;

import java.util.Collection;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public interface Vertex<E extends Edge> {
    void addEdge(E e);
    Collection<E> getEdges();
    String getName();
}
