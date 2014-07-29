package vvakar.graph.components;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author vvakar
 *         Date: 7/29/14
 */
class VertexWeightBeansImpl<V extends Vertex> implements VertexWeightBeans<V> {
    Map<V, VertexWeightBean<V>> weightBeanMap = new HashMap<V, VertexWeightBean<V>>();

    /**
     * Internal method to populate the collection.
     * @param vertex cannot be null
     */
    void add(V vertex, int weight) {
        Preconditions.checkNotNull(vertex);
        weightBeanMap.put(vertex, new VertexWeightBean<V>(vertex, weight));
    }

    @Override
    public int size() {
        return weightBeanMap.size();
    }

    @Override
    public boolean isEmpty() {
        return weightBeanMap.isEmpty();
    }

    @Override
    public Iterator<VertexWeightBean<V>> iterator() {
        return weightBeanMap.values().iterator();
    }
}
