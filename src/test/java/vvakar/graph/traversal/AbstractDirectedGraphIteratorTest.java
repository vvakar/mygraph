package vvakar.graph.traversal;

import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.components.SimpleUndirectedGraph;
import vvakar.graph.components.UndirectedEdge;
import vvakar.graph.interfaces.Vertex;

import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 7/28/14
 */
public class AbstractDirectedGraphIteratorTest {
    SimpleDirectedGraph<Vertex> emptyGraph, nonEmptyGraph;
    Vertex v1 = vertex("v1"), v2 = vertex("v2"), v3 = vertex("v3"), v4 = vertex("v4"), v5 = vertex("v5");
    DirectedEdge e12 = directedEdge(v1, v2), e13 = directedEdge(v1, v3), e24 = directedEdge(v2, v4),
            e35 = directedEdge(v3, v5);

    public void before() {
        emptyGraph = new SimpleDirectedGraph<Vertex>();
        /*
             v1 ---> v2 ---> v4
               \
                \__> v3 ---> v5
        */
        nonEmptyGraph = new SimpleDirectedGraph<Vertex>();
        nonEmptyGraph.put(e12);
        nonEmptyGraph.put(e13);
        nonEmptyGraph.put(e35);
        nonEmptyGraph.put(e24);
    }


}
