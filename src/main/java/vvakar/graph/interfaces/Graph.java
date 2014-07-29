package vvakar.graph.interfaces;

import com.google.common.base.Optional;
import java.util.Collection;

/**
 * @author vvakar
 *         Date: 7/27/14
 */
public interface Graph<V extends Vertex, E extends Edge> {
    Collection<V> getVertices();

    VertexWeightBeans<V> getNeighborsOf(V v);

    void put(E e);

    Optional<E> get(E e);

}
