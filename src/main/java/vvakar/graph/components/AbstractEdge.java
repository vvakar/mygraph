package vvakar.graph.components;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Vertex;

/**
 * All edges connect exactly two vertices.
 * @author vvakar
 *         Date: 7/27/14
 */
abstract class AbstractEdge<V extends Vertex> implements Edge<V> {
    private V v1, v2;
    private int weight;
    private static final int DEFAULT_WEIGHT = 1;

    AbstractEdge(V v1, V v2) {
        this(v1, v2, DEFAULT_WEIGHT);
    }

    AbstractEdge(V v1, V v2, int weight) {
        Preconditions.checkNotNull(v1);
        Preconditions.checkNotNull(v2);
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    @Override
    public V getV1() {
        return v1;
    }
    @Override
    public V getV2() {
        return v2;
    }

    public void setV1(V v1) {
        this.v1 = v1;
    }

    public void setV2(V v2) {
        this.v2 = v2;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEdge that = (AbstractEdge) o;

        if (weight != that.weight) return false;
        if (!v1.equals(that.v1)) return false;
        if (!v2.equals(that.v2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        result = 31 * result + weight;
        return result;
    }
}
