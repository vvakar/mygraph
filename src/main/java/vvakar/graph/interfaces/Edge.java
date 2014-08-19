package vvakar.graph.interfaces;

import com.google.common.base.Optional;

/**
 * Graph edge. If directed, assume v1 points to v2.
 * @author vvakar
 *         Date: 7/27/14
 */
public interface Edge<V> {
    V getV1();
    void setV1(V v);

    V getV2();
    void setV2(V v2);

    int getWeight();

    public Optional<V> getTargetIfOriginatorIs(V v);
}
