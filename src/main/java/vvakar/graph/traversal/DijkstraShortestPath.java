package vvakar.graph.traversal;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author vvakar
 *         Date: 7/29/14
 */
public class DijkstraShortestPath <V extends Vertex, E extends Edge> {
    private Graph<V,E> graph;

    public DijkstraShortestPath(Graph<V, E> graph) {
        this.graph = graph;
    }

    private List<DijkstraBean<V>> computePath(V start, V end) {
        final PriorityQueue<DijkstraBean<V>> heap = new PriorityQueue<DijkstraBean<V>>();
        final Map<V, DijkstraBean<V>> map = new HashMap<V, DijkstraBean<V>>();

        DijkstraBean<V> startBean = new DijkstraBean<V>(start, start, 0);
        map.put(start, startBean);
        heap.add(startBean);

        V current = start;
        do {
            int currentWeight = 0;
            if(map.containsKey(current)) {
                currentWeight = map.get(current).weight;
            }

            Iterable<VertexWeightBean<V>> ns = graph.getNeighborsOf(current);
            for (VertexWeightBean<V> wb : ns) {
                V neighbor = wb.getVertex();
                int currentWeightToNeighbor = currentWeight + wb.getWeight();
                DijkstraBean neighborBean = map.get(neighbor);
                if(neighborBean == null) {
                    // never seen before, just add it
                    neighborBean = new DijkstraBean<V>(neighbor, current, currentWeightToNeighbor);
                    map.put(neighbor, neighborBean);
                    heap.add(neighborBean);
                } else if(neighborBean.weight > currentWeightToNeighbor) {
                    // seen before, but current weight is lower
                    neighborBean = new DijkstraBean<V>(neighbor, current, currentWeightToNeighbor);
                    map.put(neighbor, neighborBean);
                    heap.add(neighborBean);
                }
            }

            current = heap.isEmpty() ? null : heap.poll().destination;
        } while(current != null && !current.equals(end));
        return composeShortestPath(map, start, end);
    }

    private List<DijkstraBean<V>> composeShortestPath(Map<V, DijkstraBean<V>>  map, V start, V end) {
        List<DijkstraBean<V>> list = new ArrayList<DijkstraBean<V>>();

        DijkstraBean<V> current = map.get(end);

        if(current != null) {
            while (!current.destination.equals(current.via)) {
                list.add(current);
                current = map.get(current.via);
            }
            list.add(current); // add the start node
        }

        Collections.reverse(list);
        return list;
    }

    public List<DijkstraBean<V>> getShortestPath(@NotNull V start, @NotNull V end) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(end);
        return computePath(start, end);
    }

    public static class DijkstraBean<V extends Vertex> implements Comparable<DijkstraBean> {
        private final V destination;
        private final V via;
        private final int weight;

        private DijkstraBean(V destination, V via, int weight) {
            Preconditions.checkNotNull(destination);
            Preconditions.checkNotNull(via);
            Preconditions.checkArgument(weight >= 0);
            this.destination = destination;
            this.via = via;
            this.weight = weight;
        }

        public V getDestination() {
            return destination;
        }

        public V getVia() {
            return via;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(@NotNull DijkstraBean o) {
            Preconditions.checkNotNull(o);
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "DijkstraBean{" +
                    "destination=" + destination +
                    ", via=" + via +
                    ", weight=" + weight +
                    '}';
        }
    }
}
