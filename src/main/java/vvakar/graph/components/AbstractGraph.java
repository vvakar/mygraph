package vvakar.graph.components;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Every graph has vertices and edges.
 * @author vvakar
 *         Date: 7/27/14
 */
public abstract class AbstractGraph<V extends Vertex, E extends Edge<V>> implements Graph<V,E> {
    protected final Map<V, V> vertices;
    protected final Set<E> edges;

    public AbstractGraph() {
        vertices = new HashMap<V,V>();
        edges = new HashSet<E>();
    }

    public Collection<V> getVertices() {
        return vertices.values();
    }

    public Collection<E> getEdges() { return edges; }

    /**
     * Find immediate neighbors of specified <code>Vertext</code>.
     * @param v cannot be null
     */
    @Override
    public VertexWeightBeans<V> getNeighborsOf(V v) {
        Preconditions.checkNotNull(v);

//        Collection<VertexWeightBeansImpl<V>> coll = new ArrayList<VertexWeightBeansImpl<V>>();
        VertexWeightBeansImpl<V> vertexWeightBeans = new VertexWeightBeansImpl<V>();
        // FIXME: move edge information into the vertices
        for(E e : edges) {
            Optional<V> maybeV = e.getTargetIfOriginatorIs(v);
            if(maybeV.isPresent()) {
                vertexWeightBeans.add(maybeV.get(), e.getWeight());
            }
        }
        return vertexWeightBeans;
    }

    /**
     * Make <code>Edge</code> known to the system.
     */
    @Override
    public void put(E e) {
        Preconditions.checkNotNull(e);
        V startVertex = e.getV1();
        putVertex(startVertex, e);
        putVertex(e.getV2());

        e.setV1(vertices.get(e.getV1()));
        e.setV2(vertices.get(e.getV2()));
        edges.add(e);
    }

    private V putVertex(V vertex) {
        V temp = vertices.get(vertex);
        if(temp == null) {
            temp = vertex;
            vertices.put(vertex, vertex);
        }
        return temp;
    }

    private V putVertex(V vertex, E edge) {
        V temp = putVertex(vertex);
        temp.addEdge(edge);
        return temp;
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
        E opt;
        if(edges.contains(e)) {
            opt = e;
        } else {
            opt = null;
        }

        return Optional.fromNullable(opt);
    }
}
