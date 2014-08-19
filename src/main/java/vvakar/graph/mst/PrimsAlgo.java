package vvakar.graph.mst;

import com.apple.concurrent.Dispatch;
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
            PriorityQueue<E> heap = new PriorityQueue<E>(10, new Comparator<E>() {
                @Override
                public int compare(E o1, E o2) {
                    return o1.getWeight() - o2.getWeight();
                }
            });

            // prime the computation
            int totalVertices = graph.getVertices().size();
            V current = graph.getVertices().iterator().next();
            seen.add(current);
            heap.addAll(current.getEdges());

            do {
                E smallestEdge = heap.poll();
                if (smallestEdge != null) {
                    V next = smallestEdge.getV2();
                    if (!seen.contains(next)) {
                        retval.add(smallestEdge);
                        seen.add(next);
                        heap.addAll(next.getEdges());
                    }
                } else if (seen.size() < totalVertices) {
                    throw new RuntimeException("Apparently graph not connected");
                }
            } while(seen.size() < totalVertices);
        }

        return retval;
    }

    public static <V extends Vertex, E extends Edge<V>> int getTotalSize( List<E> list)  {
        int total = 0;
        for(E e : list) {
            if(!e.getV1().equals(e.getV2())) { // don't count self-edges
                total += e.getWeight();
            }
        }

        return total;
    }
}
