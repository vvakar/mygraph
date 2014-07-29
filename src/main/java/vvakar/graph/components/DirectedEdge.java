package vvakar.graph.components;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Vertex;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public class DirectedEdge<V extends Vertex> extends AbstractEdge<V> {
    DirectedEdge(V v1, V v2) {
        super(v1, v2);
    }

    public DirectedEdge(V v1, V v2, int weight) {
        super(v1, v2, weight);
    }

    public Optional<V> getTargetIfOriginatorIs(V v) {
        Preconditions.checkNotNull(v);
        V retval = null;
        if(getV1().equals(v)) {
            retval = getV2();
        }
        return Optional.fromNullable(retval);
    }
}
