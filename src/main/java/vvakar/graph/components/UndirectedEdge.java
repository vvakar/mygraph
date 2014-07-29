package vvakar.graph.components;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Vertex;

/**
 * An undirected edge connects two vertices without direction or weight.
* @author vvakar
*         Date: 7/27/14
*/
public class UndirectedEdge<V extends Vertex> extends AbstractEdge<V> {
    UndirectedEdge(V v1, V v2) {
        super(v1, v2);
    }

    public UndirectedEdge(V v1, V v2, int weight) {
        super(v1, v2, weight);
    }

    public Optional<V> getTargetIfOriginatorIs(V v) {
        Preconditions.checkNotNull(v);
        V retval = null;
        if(getV1().equals(v)) {
            retval = getV2();
        } else if(getV2().equals(v)) {
            retval = getV1();
        }
        return Optional.fromNullable(retval);
    }
}
