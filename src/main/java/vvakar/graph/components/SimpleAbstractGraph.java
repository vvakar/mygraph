package vvakar.graph.components;


import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Vertex;

/**
 * Abstract graph that disallows duplicated edges (hence is simple).
 * @author vvakar
 *         Date: 7/27/14
 */
abstract class SimpleAbstractGraph<V extends Vertex, E extends Edge<V>> extends AbstractGraph<V, E> {
    @Override
    public void put(E e) {
        if(get(e).isPresent()) {
            throw new IllegalArgumentException("Edge " + e + " already exists in graph. Simple Graph allows no duplicates.");
        }
        super.put(e);
    }
}
