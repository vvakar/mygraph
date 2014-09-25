package vvakar.tsp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 9/25/14
 */
public class PointTest {

    @Test
    public void testDistance() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);

        assertEquals(1.41, Point.distance(p1, p2), 0.01);
        assertEquals(1.41, Point.distance(p2, p1), 0.01);
    }

    @Test
    public void testDistanceToSelf() {
        Point p1 = new Point(1,1);
        assertEquals(0, Point.distance(p1, p1), 0.01);
    }

}
