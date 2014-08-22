package vvakar.graph.traversal;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;

import java.util.Map;

/**
 * @author vvakar
 *         Date: 8/19/14
 */
public class FloydWarshall {
    private static final long INFINITY = Long.MAX_VALUE;
    public static BiMap idsToVertices;

    /**
     * Assume all vertex names are in fact integers.
     */
    public static <V extends Vertex, E extends Edge<V>> long[][] compute(Graph<V,E> graph) {
        final int TOTAL_VERTICES = graph.getVertices().size();
        long[][] prevState, currentState = initArray(TOTAL_VERTICES);
        idsToVertices = fillInEdgeWeights(graph, currentState);


        for(int k = 1; k < TOTAL_VERTICES; ++k) {
            prevState = currentState;
            currentState = initArray(TOTAL_VERTICES);
            for(int from = 0; from < TOTAL_VERTICES; ++from) {
                for(int to = 0; to < TOTAL_VERTICES; ++to) {
                    long prevValue = prevState[to][from];
                    long candidateValue = addSensibly(prevState[k][from], prevState[to][k]);
                    currentState[to][from] = Math.min(prevValue, candidateValue);
                }
            }
        }


        // check for negative loops
        for(int i = 0; i < TOTAL_VERTICES; ++i) {
            if(currentState[i][i] < 0) {
                throw new RuntimeException("Negative loop detected at vertex " + i);
            }
        }

        // TODO: map back to original vertices
        return currentState;
    }

    /**
     * Detect infinity and avoid overflow
     * @param a
     * @param b
     * @return
     */
    private static long addSensibly(long a, long b) {
        final long res;
        if(a == INFINITY || b == INFINITY) {
            res = INFINITY;
        } else {
            res = a + b;
            if(a > 0 && b > 0 && res <= 0) {
                throw new RuntimeException("Overflow detected when adding " + a + " + " + b);
            }
        }
        return res;
    }
    private static <V extends Vertex, E extends Edge<V>> BiMap<Integer, V> fillInEdgeWeights(Graph<V, E> graph, long[][] currentState) {
        BiMap<Integer, V> idsToVertices = HashBiMap.create();
        int idGen = 0;
        // fill in all edge weights
        for(E e : graph.getEdges()) {
            V v1 = e.getV1();
            V v2 = e.getV2();

            if(!idsToVertices.inverse().containsKey(v1)) {
                idsToVertices.put(idGen++, v1);
            }
            if(!idsToVertices.inverse().containsKey(v2)) {
                idsToVertices.put(idGen++, v2);
            }

            int v1Id = idsToVertices.inverse().get(v1);
            int v2Id = idsToVertices.inverse().get(v2);

            Preconditions.checkArgument(v1Id >= 0 && v2Id >= 0); // no overflow!

            int w = e.getWeight();
            // this also handles duplicate edges by keeping the smallest one
            long currentWeight = currentState[v1Id][v2Id];
            currentState[v1Id][v2Id] = Math.min(currentWeight, w);
        }

        return idsToVertices;
    }

    /**
     * Use long arrays with Integer.MAX_VALUE representing infinity so further add operations don't overflow.
     * @param totalVertices
     * @return
     */
    private static long[][] initArray(int totalVertices) {
        long[][] arr = new long[totalVertices][totalVertices];
        for(int i = 0; i< totalVertices; ++i) {
            for(int j = 0; j < totalVertices; ++j) {
                arr[i][j] = i == j ? 0 : INFINITY;
            }
        }

        return arr;
    }


    public static class FloydWarshallDistanceBean<V extends Vertex> implements Comparable<FloydWarshallDistanceBean<V>> {
        private final V from, to;
        private final int weight;

        public FloydWarshallDistanceBean(V from, V to, int weight) {
            Preconditions.checkNotNull(from);
            Preconditions.checkNotNull(to);
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(FloydWarshallDistanceBean<V> o) {
            return this.getWeight() - o.getWeight();
        }

        public V getFrom() {
            return from;
        }

        public V getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FloydWarshallDistanceBean that = (FloydWarshallDistanceBean) o;

            if (weight != that.weight) return false;
            if (!from.equals(that.from)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            result = 31 * result + weight;
            return result;
        }
    }
}
