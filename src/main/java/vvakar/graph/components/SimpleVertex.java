package vvakar.graph.components;

import com.google.common.base.Preconditions;
import vvakar.graph.interfaces.Vertex;

/**
* @author vvakar
*         Date: 7/27/14
*/
public class SimpleVertex implements Vertex{
    private String name;

    public SimpleVertex(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
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
