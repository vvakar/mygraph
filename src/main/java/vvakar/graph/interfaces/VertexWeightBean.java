package vvakar.graph.interfaces;

import com.google.common.base.Preconditions;

/**
 * Vertex and a weight associated with it.
 * @author vvakar
 *         Date: 7/29/14
 */

public class VertexWeightBean<V> {
    private V vertex;
    private int weight;

    public VertexWeightBean(V vertex, int weight) {
        Preconditions.checkNotNull(vertex);
        this.vertex = vertex;
        this.weight = weight;
    }

    public V getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexWeightBean that = (VertexWeightBean) o;

        if (weight != that.weight) return false;
        if (!vertex.equals(that.vertex)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vertex.hashCode();
        result = 31 * result + weight;
        return result;
    }
}
