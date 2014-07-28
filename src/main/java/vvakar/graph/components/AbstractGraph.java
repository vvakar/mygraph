package vvakar.graph.components;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Every graph has vertices and edges.
 * @author vvakar
 *         Date: 7/27/14
 */
public abstract class AbstractGraph<V extends Vertex, E extends Edge<V>> implements Graph<V,E> {
    private final Set<V> vertices;
    private final List<E> edges;

    public AbstractGraph() {
        vertices = new HashSet<V>();
        edges = new ArrayList<E>();
    }

    public Collection<V> getVertices() {
        return vertices;
    }

    /**
     * Find immediate neighbors of specified <code>Vertext</code>.
     */
    @Override
    public Collection<V> getNeighborsOf(V v) {
        Collection<V> retval = new ArrayList<V>();
        for(E e : edges) {
            Optional<V> maybeV = e.getTargetIfOriginatorIs(v);
            if(maybeV.isPresent()) {
                retval.add(maybeV.get());
            }

        }
        return retval;
    }

    /**
     * Make <code>Edge</code> known to the system.
     */
    @Override
    public void put(E e) {
        Preconditions.checkNotNull(e);
        vertices.add(e.getV1());
        vertices.add(e.getV2());
        edges.add(e);
    }

    /**
     * Get specified <code>Edge</code>.
     * @param e edge to find
     * @return <code>Optional</code> of the found <code>Edge</code>, or absent
     */
    @Override
    public Optional<E> get(E e) {
        return find(e);
    }

    private Optional<E> find(E e) {
        Preconditions.checkNotNull(e);
        int i = edges.indexOf(e);
        E opt = null;
        if(i >= 0) {
            opt = edges.get(i);
        }
        return Optional.fromNullable(opt);
    }
}
