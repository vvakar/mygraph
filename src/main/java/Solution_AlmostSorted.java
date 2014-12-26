import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author vvakar
 *         Date: 12/16/14
 */
public class Solution_AlmostSorted {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

//        for (int i = 0; i < Qcount; ++i) {
//            String QStr = br.readLine();
//            System.out.println(doWork(QStr));
//
//        }

        int[] ar = strArray2intArray(br.readLine().split("\\s+"));
        System.out.println(doWork(ar));
    }


    /**
     * 3   1    2     5 4
     *
     *
     * 1 2 3
     *
     * streak = 3
     * counter = counter +
     *
     * current = 2
     * prev = 1
     * if(prev > current) streak = 0;
     * streak ++
     * counter = counter + streak
     *
     */
    public static int doWork(int[]arr) {
        NavigableMap<Integer,Integer> mostRecentMinima = new TreeMap<Integer, Integer>();
        RecentPeaksSegmentTree recentPeaksSegmentTree = new RecentPeaksSegmentTree(arr);
        int counter = 0;

        int i = 0;
        for(int current : arr) {
            ++counter; // always count current
            // get most recent wall
            final int wallIndex = recentPeaksSegmentTree.getIndexOfMostRecentWallUpTo(i - 1, current);

            // get the count of all minima seen after the wall
            // counter += visibleMinima.getCountFromIndexGreaterThan(wallIndex); // sorted array

            for(Map.Entry<Integer,Integer> entry : mostRecentMinima.entrySet()) {
                if(entry.getValue() > wallIndex) { // has to be after the wall, which means all minima that go through are smaller than current
                    ++counter;
                }
            }

            // maintenance for next iteration
            recentPeaksSegmentTree.update(current, i);

            // prune all cloaked minima by removing all mostRecentMinima >= current
            // visibleMinima.truncateFromValueGTE(current);
            ArrayList<Integer> keys = new ArrayList<Integer>(mostRecentMinima.tailMap(current).keySet());
            for(Integer k : keys) mostRecentMinima.remove(k);

            mostRecentMinima.put(current, i);

            ++i;
        }

        return counter;
    }


    private static final class RecentPeaksSegmentTree {
        private static final class Node {
            public int max, indexOfMax, from, to;
            public final Node left, right;
            public Node parent;
            Node(int max, int indexOfMax, Node left, Node right, int from, int to) { this.max = max; this.indexOfMax = indexOfMax; this.left = left; this.right = right; this.from = from; this.to = to; }
        }

        private final Node root;
        private final Map<Integer, Node> indexesToNodes = new HashMap<Integer, Node>();
        RecentPeaksSegmentTree(int[] arr) {
             root = construct(arr, 0, arr.length -1);
        }

        private static final Node NULL_NODE = new Node(Integer.MIN_VALUE, -1, null, null, -1, -1);
        private Node construct(int[] arr, int from, int to) {
            if(from > to)
                return NULL_NODE;

            if(from == to) {
                Node node = new Node(arr[from], from, NULL_NODE, NULL_NODE, from, to);
                indexesToNodes.put(from, node);
                return node;
            }


            final int half = (to + from)/2;
            final Node left = construct(arr, from, half);
            final Node right = construct(arr, half + 1, to);

            // favor leftmost max
            final int max, indexOfMax;
            if(left.max >= right.max) {
                max = left.max;
                indexOfMax = left.indexOfMax;
            } else {
                max = right.max;
                indexOfMax = right.indexOfMax;
            }

            final Node node = new Node(
                    max,
                    indexOfMax,
                    left,
                    right,
                    from,
                    to);


            left.parent = right.parent = node;
            return node;
        }

        public void update(int index, int value) {
            Node node = indexesToNodes.get(index); // cannot be null

            update(node, index, value);
        }

        private void update(Node node, int index, int value) {
            if(node != null && node.max <= value) {
                node.indexOfMax = index;
                update(node.parent, index, value);
            }
        }

        /**
         * @return index of most recent wall or -1 if not found
         */
        public int getIndexOfMostRecentWallUpTo(int index, int value) {
            return getIndexOfMostRecentWallUpTo(root, index, value);
        }

        private int getIndexOfMostRecentWallUpTo(Node root, int index, int value) {
            if(root.max < value || root.from > index)
                return -1;

            int candidate = -1;
            if(root.to <= index) // in range - return what we have
                candidate = root.indexOfMax;

            // out of range - drill down
            int maxOtherTwo = Math.max(getIndexOfMostRecentWallUpTo(root.right, index, value),
                    getIndexOfMostRecentWallUpTo(root.left, index, value));

            // We're not just looking for the max - we're looking for any  >= value with the *highest* index
            return  Math.max(candidate, maxOtherTwo);
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
