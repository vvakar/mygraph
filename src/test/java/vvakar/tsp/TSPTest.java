package vvakar.tsp;

import com.google.common.collect.Lists;
import org.junit.Test;
import vvakar.graph.Util;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;
import static vvakar.graph.components.GraphFactory.vertex;


/**
 * @author vvakar
 *         Date: 9/25/14
 */
public class TSPTest {
    @Test
    public void testTSPOneNode() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,2));
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(1, ret.size());
    }


    @Test
    public void testTSPTwoVertices() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,2));
        points.add(new Point(3,4));
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(2, ret.size());

    }

    @Test
    public void testTSP() throws Exception {
        List<Point> points = Util.getTspPoints("tsp.txt");
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(ret.size(), points.size());
    }
}
