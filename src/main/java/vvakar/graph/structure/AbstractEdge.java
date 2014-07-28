package vvakar.graph.structure;
import com.google.common.base.Preconditions;

/**
 * All edges connect exactly two vertices.
 * @author vvakar
 *         Date: 7/27/14
 */
abstract class AbstractEdge<V extends Vertex> implements Edge<V> {
    private V v1, v2;

    AbstractEdge(V v1, V v2) {
        Preconditions.checkNotNull(v1);
        Preconditions.checkNotNull(v2);
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public V getV1() {
        return v1;
    }
    @Override
    public V getV2() {
        return v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEdge edge = (AbstractEdge) o;

        if (!v1.equals(edge.v1)) return false;
        if (!v2.equals(edge.v2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        return result;
    }

}
