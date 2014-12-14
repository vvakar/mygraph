package vvakar.tsp;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import vvakar.util.Util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Traveling salesman problem via dynamic programming.
 * @author vvakar
 *         Date: 9/25/14
 */
public class TSP {
    public static List<Point> computeShortestPath(List<Point> points) {
        if(false) {
            return mine(points);
        } else  {
            return theirs(points);
        }

    }

    private static List<Point> theirs(List<Point> points) {
        int size = points.size();
        double[][] dist = new double[size][size];

        // calculate distance matrics from/to every point
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                Point from = points.get(i);
                Point to = points.get(j);
                dist[i][j] = Point.distance(from, to);
            }
        }

        double shortest = getShortestHamiltonianCycle(dist);
        return points;
    }

    public static double getShortestHamiltonianCycle(double[][] dist) {
        int n = dist.length;
        double[][] dp = new double[1 << n][n];
        for (double[] d : dp)
            Arrays.fill(d, Double.MAX_VALUE / 2);
        dp[1][0] = 0;
        for (int mask = 1; mask < 1 << n; mask += 2) {
            for (int i = 1; i < n; i++) {
                if ((mask & 1 << i) != 0) {
                    for (int j = 0; j < n; j++) {
                        if ((mask & 1 << j) != 0) {
                            dp[mask][i] = Math.min(dp[mask][i], dp[mask ^ (1 << i)][j] + dist[j][i]);
                        }
                    }
                }
            }
        }
        double res = Double.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            res = Math.min(res, dp[(1 << n) - 1][i] + dist[i][0]);
        }

        // reconstruct path
        int cur = (1 << n) - 1;
        int[] order = new int[n];
        int last = 0;
        for (int i = n - 1; i >= 1; i--) {
            int bj = -1;
            for (int j = 1; j < n; j++) {
                if ((cur & 1 << j) != 0 && (bj == -1 || dp[cur][bj] + dist[bj][last] > dp[cur][j] + dist[j][last])) {
                    bj = j;
                }
            }
            order[i] = bj;
            cur ^= 1 << bj;
            last = bj;
        }
        System.out.println(Arrays.toString(order));
        return res;
    }

    private static List<Point> mine(List<Point>points) {
        System.out.println("Starting TSP at " + new Date());

        final Point ONE = points.get(0);
        Map<LastStop, Double> previousCostMap = Maps.newHashMap();

        // prime prev cost map with ONE
        previousCostMap.put(new LastStop(ImmutableSet.<Point>of(), ONE), 0d);
        for(Point p : points) {
            previousCostMap.put(new LastStop(ImmutableSet.of(ONE), p), Point.distance(ONE, p));
        }

        for(int m = 3; m < points.size(); ++m) {
            Map<LastStop, Double> costMap = Maps.newHashMap();
            List<Set<Point>> subsets = allKSubsetsThatInclude(ONE, points, m); // convert to generator to ease memory pressure
            System.out.println("SUBSET SIZE = " + subsets.size() + " for k = " + m);
            int subsetCount = 0;
            for(Set<Point> subset : subsets) {

                ++subsetCount;
                if(subsetCount % 10000 == 0) {
                    System.out.println("Processing subset " + subsetCount + " of " + subsets.size() + " for k = " + m);
                }

                for(Point J : subset) {
                    if(!J.equals(ONE)) {
                        Set<Point> S_Minus_J = setWithoutPoint(subset, J);
                        for(Point I : S_Minus_J) {
                            if(!I.equals(ONE)) {
                                Set<Point> S_Minus_J_Minus_I = setWithoutPoint(S_Minus_J, I);
                                double distanceONE_to_I = previousCostMap.get(new LastStop(S_Minus_J_Minus_I, I));
                                double distance_I_to_J = Point.distance(I, J);
                                costMap.put(new LastStop(S_Minus_J, J), distanceONE_to_I + distance_I_to_J);
                            }
                        }
                    }
                }

            }
            previousCostMap = costMap;

        }

        // TODO: find distance back to ONE
        List<Point> ret = Lists.newArrayList();
        for(Point point : points) {
            ret.add(point);
        }
        return ret;
    }

    /**
     * Get all subsets that include <code>one</code> and have size <code>k</code>
     */
    private static List<Set<Point>> allKSubsetsThatInclude(Point one, List<Point> points, int k) {
        Set<Point> allButOne = Sets.newHashSet(points);
        List<Set<Point>> list = Lists.newLinkedList();
        allButOne.remove(one);

        for(Set<Point> set : Util.allKSubsets(allButOne, k-1)) {
            Set<Point> aSet = Sets.newHashSet(set);
            aSet.add(one);
            list.add(aSet);
        }

        return list;
    }

    /**
     * Point where we last stopped and the set of points we traversed to get there. Optimal substructure guarantees that
     * this is the shortest path.
     * Cost represents total cost from ONE to point via points.
     */
    private static class LastStop {
        final Point last;
        final Set<Point> breadcrumbs;

        public LastStop(Set<Point> points, Point point) {
            Preconditions.checkNotNull(points);
            Preconditions.checkNotNull(point);
            this.last = point;
            this.breadcrumbs = points;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LastStop lastStop = (LastStop) o;

            if (!breadcrumbs.equals(lastStop.breadcrumbs)) return false;
            if (!last.equals(lastStop.last)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = last.hashCode();
            result = 31 * result + breadcrumbs.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "LastStop{" +
                     breadcrumbs + " --> " + last +
                    '}';
        }
    }

    private static Set<Point> setWithoutPoint(Set<Point> set, Point point) {
        Set<Point> copy = Sets.newHashSet(set);
        boolean found = copy.remove(point);
        if(!found) {
            throw new IllegalArgumentException("Point " + point + " should have been found in set " + set);
        }
        return copy;
    }
}
