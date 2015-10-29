package vvakar.algos1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by valentin.vakar on 10/27/15.
 */
public class KargerGraph {

    public static void main(String[] asdf) throws Exception {
        KargerGraph graph = parseGraph("KargerMinCut.txt");
        System.out.println("HELLOOO " + graph.minCut());
    }

    public static KargerGraph parseGraph(String filename) throws IOException {
        KargerGraph graph = new KargerGraph();
        BufferedReader reader = new BufferedReader(new InputStreamReader(KargerGraph.class.getClassLoader().getResourceAsStream(filename)));
        while (reader.ready()) {
            String[] line = reader.readLine().split("\\s+");
            String v = line[0];
            for (int i = 1; i < line.length; ++i) {
                Edge edge = new Edge(v, line[i], 0);
                if (!graph.allEdges.contains(edge)) // input specifies duplicate edges
                    graph.recordEdge(v, line[i]);
            }
        }
        return graph;
    }

    public Map<String, Set<Edge>> verticesToEdges = new HashMap<>();
    private Set<Edge> allEdges = new HashSet<>();

    public void recordEdge(String a, String b) {
        if (a.equals(b)) return; // ignore self-edges

        Set<Edge> edgesA = verticesToEdges.getOrDefault(a, new HashSet<>());
        Set<Edge> edgesB = verticesToEdges.getOrDefault(b, new HashSet<>());

        int i = 0;
        Edge newEdge = new Edge(a, b, i);
        while (allEdges.contains(newEdge)) {
            newEdge = new Edge(a, b, ++i);
        }
        edgesA.add(newEdge);
        edgesB.add(newEdge);
        allEdges.add(newEdge);
        verticesToEdges.put(a, edgesA);
        verticesToEdges.put(b, edgesB);
    }

    /**
     * Contract two vertices and return the new key
     */
    public String contractVertices(String a, String b) {
        if (!verticesToEdges.containsKey(a)) throw new IllegalStateException("Cant find key " + a);
        if (!verticesToEdges.containsKey(b)) throw new IllegalStateException("Cant find key " + b);
        String key = mergeKeys(a, b);
        if (verticesToEdges.containsKey(key)) throw new IllegalStateException("Key already there: " + key);

        Set<Edge> aEdges = verticesToEdges.get(a);
        Set<Edge> bEdges = verticesToEdges.get(b);
        Set<Edge> abEdges = new HashSet<>(aEdges);
        abEdges.addAll(bEdges);


        for (Edge edge : abEdges) {
            allEdges.remove(edge);
            if ((edge.a.equals(a) && edge.b.equals(b) || edge.b.equals(a) && edge.a.equals(b))) {
                // swallow
            } else if (edge.a.equals(a) || edge.a.equals(b)) {
                verticesToEdges.get(edge.b).remove(edge);
                recordEdge(key, edge.b);
            } else {
                verticesToEdges.get(edge.a).remove(edge);
                recordEdge(edge.a, key);
            }
        }

        verticesToEdges.remove(a);
        verticesToEdges.remove(b);
        return key;
    }

    /**
     * Utilities
     */
    public int minCut() {
        int min = Integer.MAX_VALUE;

        Map<String, Set<Edge>> verticesToEdgesOriginal = clone(verticesToEdges);

        for (int i = 0; i < verticesToEdgesOriginal.size() * 100; ++i) {
            List<String> keys = new ArrayList<>(verticesToEdges.keySet());
            while (keys.size() > 2) {
                Collections.shuffle(keys);
                String a = keys.remove(keys.size() - 1);
                String b = keys.remove(keys.size() - 1);

                String newKey = contractVertices(a, b);
                keys.add(newKey);
            }

            min = Math.min(min, allEdges.size());
            verticesToEdges = clone(verticesToEdgesOriginal);
            allEdges = verticesToEdges.values().stream().flatMap(s -> s.stream()).collect(Collectors.toSet());


            if(i % 100 == 0) System.out.println(i + ": " + min);
        }

        return min;
    }

    private Set<Edge> clone(Set<Edge> edges) {
        Set<Edge> cl = new HashSet<>(edges.size());
        for (Edge e : edges) {
            cl.add(new Edge(e.a, e.b, e.i));
        }
        return cl;
    }

    private Map<String, Set<Edge>> clone(Map<String, Set<Edge>> map) {
        Map<String, Set<Edge>> cl = new HashMap<>(map.size());
        for (Map.Entry<String, Set<Edge>> entry : map.entrySet()) {
            cl.put(entry.getKey(), clone(entry.getValue()));
        }

        return cl;
    }

    public static final class Edge {
        public String a, b;
        public int i;

        public Edge(String a, String b, int i) {
            this.a = a;
            this.b = b;
            this.i = i;
        }

        @Override
        public String toString() {
            return a + "<->" + b + ":" + i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (i != edge.i) return false;
            return (a.equals(edge.a) && b.equals(edge.b)) || ((b.equals(edge.a) && a.equals(edge.b)));
        }

        @Override
        public int hashCode() {
            int result = 31 * (a.hashCode() + b.hashCode());
            result = 31 * result + i;
            return result;
        }

    }

    public static String mergeKeys(String a, String b) {
        return "[" + a + "][" + b + "]";
    }

}
