import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author vvakar
 *         Date: 12/23/14
 */
public class SinkZeros {
    /*

                              3
                           /    \
                          4      5
                      /    \    /
                     0      6  7
                      \   /   / \
                      8  9   2  11
                               / \
                              0   12
                                   \
                                    0
                                     \
                                      15
                                       \
                                        0

     */

    public static void main(String[] args) {


        Node root = new Node(3);

        root.left = new Node(4);
        root.left.left = new Node(0);
        root.left.left.right = new Node(8);
        root.left.right = new Node(6);
        root.left.right.left = new Node(9);


        root.right = new Node(5);
        root.right.left = new Node(7);
        root.right.left.left = new Node(2);
        root.right.left.right = new Node(11);

        root.right.left.right.left = new Node(0);
        root.right.left.right.right = new Node(12);


        root.right.left.right.right.right = new Node(0);
        root.right.left.right.right.right.right = new Node(15);
        root.right.left.right.right.right.right.right = new Node(0);


        print(root);

        listAllPaths(root);

        sinkZeros(root);

        print(root);

        listAllPaths(root);

        printMaxPath(root);
    }

    /**
     *                N * M  - brute force
     *
     *            - take smaller tree and insert into HashSet. Then lookup everything from bigger tree
     *
     *
     *                  buckets:  1.5N   +   N entries
     *                     tree: N nodes + 2N pointers
     *
     *                     M * 1  + N * 1  = M + N => O(N)
     *
     *
     *               N > M
     *
     *               M log M + N log M      => N log M
     *
     *
     *
     *
     *
     *                                          A
     *                           B                              C
     *                     D          E                   F            G
     *                I       J    K     L             M    N      O        P
     *
     *
     *
     *                         A(50)                    -> List(A), middle
     *                     B      C
     *                  D        F   G
     *                    E
     *
     */

    public static void printMaxPath(Node root) {
        System.out.println("Max path:  " + getMaxPath(root, 0));
    }

    private static int getMaxPath(Node root, int depthSoFar) {
        if(root == null) return depthSoFar;
        return Math.max( getMaxPath(root.left, depthSoFar + 1),
                         getMaxPath(root.right, depthSoFar + 1));
    }


    public static void print(Node root) {
        System.out.println("");
        print(Collections.singletonList(root));
    }

    private static void print(List<Node> tier) {
        if(tier.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        List<Node> nextTier = new ArrayList<Node>();
        for(Node n : tier) {
            sb.append(" " + n.value);
            if(n.left != null) nextTier.add(n.left);
            if(n.right != null) nextTier.add(n.right);
        }
        System.out.println(sb);

        print(nextTier);
    }


    public static void listAllPaths(Node root) {
        System.out.println("All Paths: ");
        followPath(root, "");
        System.out.println("/All Paths ");
    }

    /*
                                    A
                                B       C
                            D     F
                         E
     */
    private static void followPath(Node root, String breadcrumbs) {
        if(root == null) return;

        String nextBreadcrumbs = breadcrumbs + " " + root.value;

        if(root.left == null && root.right == null)
            System.out.println(nextBreadcrumbs);

        followPath(root.left, nextBreadcrumbs);
        followPath(root.right, nextBreadcrumbs);

    }


    public static void sinkZeros(Node root) {
        if(root == null) return;

        sweep(Collections.singletonList(root), new ArrayDeque<Node>());
    }

    private static void sweep(List<Node> tier, Deque<Node> queue) {
        if(tier.isEmpty()) return;
        //if anything in this tier is a 0, queue it up
        List<Node> toQueueUp = new ArrayList<Node>();
        for(Node n : tier) {
            if(n.value == 0) {
                toQueueUp.add(n);
            } else {
                if(!queue.isEmpty()) {
                    Node previousZero = queue.poll();
                    // swap values
                    previousZero.value = n.value;
                    n.value = 0;
                    toQueueUp.add(n); // queue up the new 0
                }
            }
        }

        queue.addAll(toQueueUp);
        sweep(getChildren(tier), queue);
    }


    private static List<Node> getChildren(List<Node> parents) {
        List<Node> children = new ArrayList<Node>();
        for(Node n : parents) {
            if(n.left != null)
                children.add(n.left);
            if(n.right != null)
                children.add(n.right);
        }
        return children;
    }

    static final class Node {
        Node left, right;
        int value;
        public Node(int value)  { this.value = value;}
    }

    static final class TreeBuilder {
        public static Node addLeft(Node root, int value) {
            if(root.left != null) throw new IllegalStateException();

            root.left = new Node(value);
            return root.left;
        }


        public static Node addRight(Node root, int value) {
            if(root.right != null) throw new IllegalStateException();

            root.right = new Node(value);
            return root.right;
        }

        private static Node deepCopy(Node root)  {
            if(root == null) return null;

            Node newRoot = new Node(root.value);
            newRoot.left = deepCopy(root.left);
            newRoot.right = deepCopy(root.right);
            return newRoot;
        }

    }
}
