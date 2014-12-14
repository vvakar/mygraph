package vvakar.tsp;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;
import vvakar.util.Util;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static vvakar.graph.components.GraphFactory.directedEdge;


/**
 * @author vvakar
 *         Date: 9/25/14
 */
public class TSPTest {
    @Test
    public void testTSPOneVertex() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,2));
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(1, ret.size());
    }


    @Test
    public void testTSPTwoVertices() {
        List<Point> points = Lists.newArrayList();
        points.add(new Point(1,2, "1"));
        points.add(new Point(3,4, "2"));
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(2, ret.size());

    }


    @Test
    public void testTSPThreeVertices() {
        List<Point> points = Lists.newArrayList();
        Point p1 = new Point(1,0,"1");
        Point p2 = new Point(50,0,"2");
        Point p3 = new Point(2,0,"3");
        points.add(p1);
        points.add(p2);
        points.add(p3);
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(3, ret.size());
        assertEquals(ImmutableList.of(p1,p3,p2), ret);

    }

    /**
     * shortest path = 26442.73030895475
     * path (0-based) = [0, 1, 5, 9, 10, 11, 14, 18, 17, 21, 22, 20, 16, 19, 24, 23, 15, 13, 12, 8, 6, 2, 3, 7, 4]
     * @throws Exception
     */
    @Test
    public void testTSP() throws Exception {
        List<Point> points = Util.getTspPoints("tsp.txt");
        List<Point> ret = TSP.computeShortestPath(points);
        assertEquals(ret.size(), points.size());
    }
}
