import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 12/20/14
 */
public class Solution_EvenTree {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String QStr = br.readLine();
        int[] verticesEdges = strArray2intArray(QStr.split("\\s+"));

        List<Edge> list = new ArrayList();
//        int[][] graph = new int[verticesEdges[0]][verticesEdges[0]];
        for (int i = 0; i < verticesEdges[1]; ++i) {
            String edge = br.readLine();
            int[] input = strArray2intArray(edge.split("\\s+"));
//            graph[input[0]][input[1]] = 1;
            int v1 = input[0];
            int v2 = input[1];
            list.add(new Edge(Math.min(v1, v2), Math.max(v1, v2)));
        }
        System.out.println(doWork(list));
    }

    /**
     * 1. find fringe nodes (with only one non-permanent edge)
     * 2. make their edges permanent
     * 3. repeat until down to one node (?)
     */
    public static long doWork(final List<Edge> edges) {
        Set<Edge> edgesToRemove = new HashSet<Edge>();
        Set<Edge> edgesToKeep = new HashSet<Edge>();
        UnionFind unionFind = new UnionFind();

        while(edges.size() > edgesToKeep.size() + edgesToRemove.size()) {
            Map<Integer, Edge> verticesSeenOnce = new HashMap<Integer, Edge>();
            Set<Integer> verticesSeenMultipleTimes = new HashSet<Integer>();

            for (Edge e : edges) {
                if(edgesToRemove.contains(e)) continue; // fixme
                if(edgesToKeep.contains(e)) continue; // fixme

                if (verticesSeenOnce.containsKey(e.a)) {
                    verticesSeenOnce.remove(e.a);
                    verticesSeenMultipleTimes.add(e.a);
                } else if (!verticesSeenMultipleTimes.contains(e.a)) {
                    verticesSeenOnce.put(e.a, e);
                }

                if (verticesSeenOnce.containsKey(e.b)) {
                    verticesSeenOnce.remove(e.b);
                    verticesSeenMultipleTimes.add(e.b);
                } else if (!verticesSeenMultipleTimes.contains(e.b)) {
                    verticesSeenOnce.put(e.b, e);
                }
            }

            // vertices seen once are subject to disconnection if are part of a even-number-sized cluster
            for(Map.Entry<Integer,Edge> entry : verticesSeenOnce.entrySet()) {
                int count = unionFind.findCount(entry.getKey());
                if(count > 0 && count %2 == 0) {
                    edgesToRemove.add(entry.getValue());
                } else { // otherwise we keep this edge
                    unionFind.union(entry.getValue());
                    edgesToKeep.add(entry.getValue());
                }
            }
        }

       return edgesToRemove.size();
    }


    private static final class UnionFind {
        private Map<Integer, Set<Integer>> masterToNodes = new HashMap<Integer, Set<Integer>>();
        private Map<Integer, Integer> nodeToMaster = new HashMap<Integer, Integer>();

        public void union(Edge edge) {
            // is the edge already part of a known cluster?
            Integer p1 = nodeToMaster.get(edge.a);
            Integer p2 = nodeToMaster.get(edge.b);
            if(p1 != null && p2 != null) {
                // union everything under p1
                if(p1 != p2) {
                    for(Map.Entry<Integer, Integer> entry : new HashSet<Map.Entry<Integer,Integer>>(nodeToMaster.entrySet())) {
                        if(entry.getValue() == p2) {
                            nodeToMaster.put(entry.getKey(), p1);
                        }
                    }

                    masterToNodes.get(p1).addAll(masterToNodes.remove(p2));
                } else {
                    // already under the same master
                }
            } else if(p1 != null) {
                nodeToMaster.put(edge.b, p1);
                Set<Integer> masterSet = masterToNodes.get(p1);
                if(masterSet == null) masterSet = new HashSet<Integer>();
                masterSet.add(edge.b);
                masterToNodes.put(p1, masterSet);
            }
            else if(p2 != null) {
                nodeToMaster.put(edge.a, p2);
                Set<Integer> masterSet = masterToNodes.get(p2);
                if(masterSet == null) masterSet = new HashSet<Integer>();
                masterSet.add(edge.a);
                masterToNodes.put(p2, masterSet);
            } else {
                // new cluster
                nodeToMaster.put(edge.b, edge.a);
                nodeToMaster.put(edge.a, edge.a);
                Set<Integer> masterSet = masterToNodes.get(edge.a);
                if(masterSet == null) masterSet = new HashSet<Integer>();
                masterSet.add(edge.b);
                masterSet.add(edge.a);
                masterToNodes.put(edge.a, masterSet);
            }
        }

        public int findCount(Integer findMe) {
            final Integer n = nodeToMaster.get(findMe);
            return n == null ? 0 :  masterToNodes.get(n).size();
        }

    }

    private static final class Edge {
        final int a, b;
        Edge(int a, int b) { this.a = a; this.b = b;}

        @Override
        public boolean equals(Object o) {
            if(o == null) return false;
            if(o instanceof Edge) {
                Edge o1 = (Edge)o;
                return this.a == o1.a && this.b == o1.b;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return (this.a + "-" + this.b).hashCode();
        }
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
