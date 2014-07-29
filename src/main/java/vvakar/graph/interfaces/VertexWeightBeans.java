package vvakar.graph.interfaces;

/**
 * @author vvakar
 *         Date: 7/29/14
 */
public interface VertexWeightBeans<V extends Vertex> extends Iterable<VertexWeightBean<V>> {
    boolean isEmpty();
    int size();

}
