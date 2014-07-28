package vvakar.graph.traversal;

import vvakar.graph.components.SimpleUndirectedGraph;
import vvakar.graph.components.UndirectedEdge;
import vvakar.graph.interfaces.Vertex;
import static vvakar.graph.components.GraphFactory.undirectedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
abstract class AbstractUndirectedGraphIteratorTest {
    SimpleUndirectedGraph<Vertex> emptyGraph, nonEmptyGraph;
    Vertex v1 = vertex("v1"), v2 = vertex("v2"), v3 = vertex("v3"), v4 = vertex("v4");
    UndirectedEdge e12 = undirectedEdge(v1, v2), e13 = undirectedEdge(v1, v3), e24 = undirectedEdge(v2, v4), e34 = undirectedEdge(v3, v4);

    public void before() {
        emptyGraph = new SimpleUndirectedGraph<Vertex>();
        /*
             v1 --- v2 --- v4
               \          /
                \__ v3 __/
        */
        nonEmptyGraph = new SimpleUndirectedGraph<Vertex>();
        nonEmptyGraph.put(e12);
        nonEmptyGraph.put(e13);
        nonEmptyGraph.put(e24);
        nonEmptyGraph.put(e34);
    }

}
