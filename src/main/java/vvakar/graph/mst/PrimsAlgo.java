package vvakar.graph.mst;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import vvakar.graph.interfaces.Edge;
import vvakar.graph.interfaces.Graph;
import vvakar.graph.interfaces.Vertex;
import vvakar.graph.interfaces.VertexWeightBean;
import vvakar.graph.interfaces.VertexWeightBeans;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 8/17/14
 */
public class PrimsAlgo {
    public static <V extends Vertex, E extends Edge<V>> List<E> compute(Graph<V,E> graph) {
        List<E> retval = Lists.newArrayList();
        Set<V> seen = Sets.newHashSet();

        if(graph.getVertices().iterator().hasNext()) {

            // prime
            int totalVertices = graph.getVertices().size();
            V current = graph.getVertices().iterator().next();
            seen.add(current);

            while(seen.size() < totalVertices) {
                PriorityQueue<E> heap = new PriorityQueue<E>(10, new Comparator<E>() {
                    @Override
                    public int compare(E o1, E o2) {
                        return o1.getWeight() - o2.getWeight();
                    }
                });

                for (E edge : graph.getEdges()) {
                    if (seen.contains(edge.getV1()) && !seen.contains(edge.getV2())) {
                        // this is a bridge edge - add to heap
                        heap.add(edge);
                    }
                }

                E smallestEdge = heap.poll();
                if(smallestEdge != null) {
                    retval.add(smallestEdge);
                    seen.add(smallestEdge.getV2());
                } else if(seen.size() < totalVertices) {
                    throw new RuntimeException("Apparently graph not connected");
                }
            }
        }

        return retval;
    }

    public static <V extends Vertex, E extends Edge<V>> int getTotalSize( List<E> list)  {
        int total = 0;
        for(E e : list) {
            total += e.getWeight();
        }

        return total;
    }
}
