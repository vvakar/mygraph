package vvakar.tsp;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author vvakar
 *         Date: 9/25/14
 */
public class TSP {
    public static List<Point> computeShortestPath(List<Point> points) {

        List<Point> ret = Lists.newArrayList();
        for(Point point : points) {
            ret.add(point);
        }
        return ret;
    }
}
