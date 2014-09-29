package vvakar.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.LineReader;
import com.sun.tools.javac.util.Pair;
import vvakar.graph.components.DirectedEdge;
import vvakar.graph.components.SimpleDirectedGraph;
import vvakar.graph.interfaces.Vertex;
import vvakar.knapsack.Knapsack;
import vvakar.tsp.Point;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static vvakar.graph.components.GraphFactory.vertex;

/**
 * @author vvakar
 *         Date: 8/18/14
 */
public class Util {

    /**
     * Get all subsets of set <code>set</code> that are of size <code>k</code>
     */
    public static <T> Set<Set<T>> allKSubsets(Set<T> set, int k) {
        final Set<Set<T>> ret = Sets.newHashSet();
        final List<T> list = Lists.newArrayList(set);

        for(int i = 0; i < 1 << list.size(); ++i) {
            if(countBits(i) == k) {
                Set<T> aSet = getItemsMatchingBits(list, i);
                ret.add(aSet);
            }
        }

        return ret;
    }

    public static <T> Set<T> getItemsMatchingBits(List<T> list, int b) {
        Preconditions.checkArgument(list.size() < 31);
        Set<T> set = Sets.newHashSet();
        for(int i = 0; list.size() > i; ++i) {
            if((b & 1 << i) > 0) {
                set.add(list.get(i));
            }
        }

        return set;
    }
    /**
     * Count number of bits in b
     */
    public static int countBits(int b) {
        int count = 0;
        while(b != 0) {
            if((b & 1) > 0) {
                ++count;
            }
            b = b >> 1;
        }
        return count;
    }

    public static SimpleDirectedGraph<Vertex> getDirectedGraph(String s) throws IOException {
        LineReader lineReader = getReader(s);
        SimpleDirectedGraph<Vertex> graph = new SimpleDirectedGraph<Vertex>();

        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            // tail head weight
            graph.put(new DirectedEdge<Vertex>(vertex(vals[0]), vertex(vals[1]), Integer.parseInt(vals[2])));
        }

        assertEquals(firstLine[0], graph.getVertices().size() + "");
        assertEquals(firstLine[1], graph.getEdges().size() + "");

        return graph;
    }

    public static SimpleDirectedGraph<Vertex> getUndirectedGraph(String s ) throws IOException {
        LineReader lineReader = getReader(s);
        SimpleDirectedGraph<Vertex> graph = new SimpleDirectedGraph<Vertex>();

        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            // tail head weight
            graph.put(new DirectedEdge<Vertex>(vertex(vals[0]), vertex(vals[1]), Integer.parseInt(vals[2])));
            graph.put(new DirectedEdge<Vertex>(vertex(vals[1]), vertex(vals[0]), Integer.parseInt(vals[2])));
        }

        assertEquals(firstLine[0], graph.getVertices().size() + "");
        assertEquals(firstLine[1], graph.getEdges().size() / 2 + "");

        return graph;
    }


    public static class JobBean {
        private int weight, lenght;

        public JobBean(int weight, int lenght) {
            this.weight = weight;
            this.lenght = lenght;
        }

        public int getWeight() {
            return weight;
        }

        public int getLenght() {
            return lenght;
        }
    }

    public static List<JobBean> getJobs(String s) throws IOException{
        List<JobBean> coll = Lists.newLinkedList();
        LineReader lineReader = getReader(s);
        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            //  weight length
            coll.add(new JobBean(Integer.parseInt(vals[0]), Integer.parseInt(vals[1])));
        }

        assertEquals(firstLine[0], coll.size() + "");

        return coll;


    }

    public static Pair<Integer, List<Knapsack.Item>> getKnapsackItems(String s) throws IOException {
        List<Knapsack.Item> coll = Lists.newLinkedList();
        LineReader lineReader = getReader(s);
        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            //  weight length
            coll.add(new Knapsack.Item(Integer.parseInt(vals[0]), Integer.parseInt(vals[1])));
        }


        int knapsackSize = Integer.parseInt(firstLine[0]);
        assertEquals(firstLine[1], coll.size() + "");

        return new Pair<Integer, List<Knapsack.Item>>(knapsackSize, coll);

    }

    public static List<Point> getTspPoints(String file) throws IOException {
        LineReader lineReader = getReader(file);
        List<Point> points = Lists.newArrayList();

        String[] firstLine = lineReader.readLine().split(" "); // vertices edges
        String line;
        int i = 1;
        while( (line = lineReader.readLine()) != null) {
            String[] vals = line.split(" ");
            // x coordinate, y coordinate
            points.add(new Point(Double.valueOf(vals[0]), Double.valueOf(vals[1]), String.valueOf(i++)));
        }

        assertEquals(firstLine[0], points.size() + "");
        return points;
    }

    private static LineReader getReader(String s) throws IOException{
        return new LineReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResource(s).openStream()));
    }

}
