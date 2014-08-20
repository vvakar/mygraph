package vvakar.graph.traversal;

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

    /**
     * Assume all vertex names are in fact integers.
     */
    public static <V extends Vertex, E extends Edge<V>> long[][] compute(Graph<V,E> graph) {
        final int totalVertices = graph.getVertices().size() + 1;
        long[][] prevState, currentState = initArray(totalVertices);

        // fill in all edge weights
        for(E e : graph.getEdges()) {
            V v1 = e.getV1();
            V v2 = e.getV2();
            int w = e.getWeight();

            // handle duplicate edges by keeping the smallest one
            long currentWeight = currentState[Integer.parseInt(v1.getName())][Integer.parseInt(v2.getName())];
            currentState[Integer.parseInt(v1.getName())][Integer.parseInt(v2.getName())] = Math.min(currentWeight, w);
        }

        for(int k = 1; k < totalVertices; ++k) {
            prevState = currentState;
            currentState = initArray(totalVertices);
            for(int from = 0; from < prevState[0].length; ++from) {
                for(int to = 0; to < prevState.length; ++to) {
                    long prevValue = prevState[to][from];
                    long candidateValue = prevState[k][from] + prevState[to][k];
                    currentState[to][from] = Math.min(prevValue, candidateValue);
                }
            }
        }


        // check for negative loops
        for(int i = 0; i < totalVertices; ++i) {
            if(currentState[i][i] < 0) {
                throw new RuntimeException("Negative loop detected at vertex " + i);
            }
        }

        return currentState;
    }

    private static long[][] initArray(int totalVertices) {
        long[][] arr = new long[totalVertices][totalVertices];
        for(int i = 0; i< arr.length; ++i) {
            for(int j = 0; j < arr[0].length; ++j) {
                arr[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }

        return arr;
    }
}
