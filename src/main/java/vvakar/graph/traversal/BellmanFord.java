package vvakar.graph.traversal;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 8/17/14
 */
public class BellmanFord{
    public static <V extends Vertex, E extends Edge> List<BellmanFordDistanceBean<V>> compute(Graph<V, E> graph, V start, V end) {
        Set<V> seen = Sets.newHashSetWithExpectedSize(graph.getVertices().size());
        Map<V, BellmanFordDistanceBean<V>> currentWeights = Maps.newHashMap();
        LinkedList<V> toSee = Lists.newLinkedList();

        seen.add(start);
        currentWeights.put(start, new BellmanFordDistanceBean<V>(start, start, 0));
        int iterationsRemaining = graph.getVertices().size();
        V currentVertex = start;

        while(iterationsRemaining >= 0 && currentVertex != null) {
            seen.add(currentVertex); // mark seen immediately in case there are loopbacks

            int currentWeight = currentWeights.get(currentVertex).getTotalWeight(); // must have been added already
            for (VertexWeightBean<V> neighbor : graph.getNeighborsOf(currentVertex)) {
                V neighborVertex = neighbor.getVertex();
                if(!seen.contains(neighborVertex)) {
                    toSee.add(neighborVertex);
                    int neighborHopWeight = neighbor.getWeight();
                    BellmanFordDistanceBean<V> neighborTotalWeight = currentWeights.get(neighborVertex);

                    if (neighborTotalWeight == null || neighborTotalWeight.getTotalWeight() > (neighborHopWeight + currentWeight)) {
                        currentWeights.put(neighborVertex, new BellmanFordDistanceBean<V>(neighborVertex, currentVertex, neighborHopWeight + currentWeight));
                    }
                }

                --iterationsRemaining;
            }

            currentVertex = toSee.isEmpty()? null : toSee.removeFirst();
        }

        if(!toSee.isEmpty()) {
            throw new RuntimeException("CYCLES DETECTED!");
        }

        return computePath(currentWeights, start, end);

    }

    private static <V extends Vertex> List<BellmanFordDistanceBean<V>> computePath(Map<V, BellmanFordDistanceBean<V>> currentWeights, V start, V end) {
        List<BellmanFordDistanceBean<V>> path = Lists.newArrayListWithCapacity(currentWeights.size());

        BellmanFordDistanceBean<V> endBean;
        V endVertex = end;
        do {
            endBean = currentWeights.get(endVertex);
            if(endBean != null) {
                path.add(endBean);
                endVertex = endBean.getVia();
            }
        } while(endBean != null && !endBean.getVertex().equals(start));

        Collections.reverse(path);
        return path;
    }

    public static class BellmanFordDistanceBean<V> {
        private V vertex;
        private V via;
        private int totalWeight;

        private BellmanFordDistanceBean(V vertex, V via, int totalWeight) {
            Preconditions.checkNotNull(vertex);
            Preconditions.checkNotNull(via);
            this.vertex = vertex;
            this.via = via;
            this.totalWeight = totalWeight;
        }

        public V getVertex() {
            return vertex;
        }

        public V getVia() {
            return via;
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BellmanFordDistanceBean that = (BellmanFordDistanceBean) o;

            if (totalWeight != that.totalWeight) return false;
            if (!vertex.equals(that.vertex)) return false;
            if (!via.equals(that.via)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = vertex.hashCode();
            result = 31 * result + via.hashCode();
            result = 31 * result + totalWeight;
            return result;
        }

        @Override
        public String toString() {
            return "{" +
                    "vertex=" + vertex +
                    ", via=" + via +
                    ", totalWeight=" + totalWeight +
                    '}';
        }
    }
}
