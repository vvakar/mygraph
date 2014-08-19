package vvakar.graph.components;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Vertex;

import java.util.Set;

/**
* @author vvakar
*         Date: 7/27/14
*/
public class SimpleVertex<E extends Edge> implements Vertex<E>{
    private String name;
    protected final Set<E> edges;

    public SimpleVertex(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
        edges = Sets.newHashSet();
    }

    public String getName() {
        return name;
    }

    public void addEdge(E e) {
        edges.add(e);
    }

    public Set<E> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleVertex vertex = (SimpleVertex) o;

        if (!name.equals(vertex.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                '}';
    }
}
