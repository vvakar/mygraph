package vvakar.tree;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * A segment tree is a balanced binary tree that allows querying for local extremes (minima, maxima)
 * aka. Range (Minimum|Maximum) Queries (RMQ) in O(log(n)) time.
 *
 * The tree requires two sets of values: index positions and corresponding values. While this can be achieved
 * using arrays, note that the data can be very sparse, which may cause a lot of waste and require unnecessarily
 * large arrays. Due to that, this implementation uses a TreeMap to store the data (index -> value).
 *
 * It does not allow adding/removing elements once bootstrapped, but does updates in O(log(n)).
 *
 * Space complexity: O(2n - 1) nodes.
 *
 *
 * @see <a href="http://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/">article</a>
 *
 * @author vvakar
 *         Date: 12/13/14
 */
public class SegmentTree {
    private static final class Node {
        final Node left, right;
        long value;
        final int from, to;

        Node(Node left, Node right, int from, int to, long v) {
            this.from = from;
            this.to = to;
            this.left = left;
            this.right = right;
            this.value = v;
        }
    }

    private final Node root;
    final TreeMap<Integer,Long> map;
    public SegmentTree(TreeMap<Integer, Long> map, int As[]) {
        this.map = map;
        int [] copyAs = Arrays.copyOf(As, As.length);
        Arrays.sort(copyAs);
        root = construct(map, copyAs, 0, As.length - 1);
    }

    private static final Node NULL_NODE = new Node(null, null, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    private Node construct(TreeMap<Integer,Long> map, int[] As, int from, int to) {

        if(As.length == 0 || from > to)
            return NULL_NODE;

        int fromA = As[from];
        int toA = As[to];
        if(fromA > toA) {
            int temp = toA;
            toA = fromA;
            fromA = temp;
        }

        if(from == to) {
            return new Node(null, null, fromA, toA, map.get(As[from]));
        }

        int rootIndex = (to + from)/2;
        Node leftNode = construct(map, As, from, rootIndex);
        Node rightNode = construct(map, As, rootIndex + 1, to);
        return new Node(leftNode, rightNode, fromA, toA, Math.max(leftNode.value, rightNode.value));
    }

    public void update(int key, long val) {
        map.put(key, val);
        updateMax(root, key, val);
    }

    private void updateMax(Node root, int key, long maxCandidate) {
        if(root == null) return;
        if(key > root.to || key < root.from) return; // out of range

        if(root.from <= key && root.to >= key && root.value < maxCandidate) {
            root.value = maxCandidate; // in range
        }
        updateMax(root.left, key, maxCandidate);
        updateMax(root.right, key, maxCandidate);
    }

    public long query(int from, int to) {
        return findMax(from, to, root);
    }

    private long findMax(int from, int to, Node root) {
        if(from > to || root == null) return 0;
        if(from > root.to || to < root.from) return 0; // out of range

        // in range - query is on or after current from, and on or before current to
        // no use further narrowing down
        if(from <= root.from && root.to <= to) {
            return root.value;
        }

        long candidateLeft = findMax(from, to, root.left); // narrow to the left
        long candidateRight = findMax(from, to, root.right);
        return Math.max(candidateLeft, candidateRight);
    }
}
