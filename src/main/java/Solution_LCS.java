import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 1/10/15
 */
public class Solution_LCS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine(); //ignore

            int[] arr1 = strArray2intArray(br.readLine().trim().split("\\s+"));
            int[] arr2 = strArray2intArray(br.readLine().trim().split("\\s+"));

            System.out.println(doWork(arr1, arr2));

    }

    /*
      As = 7 1 99 2 99 3 99
      Bs = 8 1 2 3

      Ai = 0
      Bi = 0

      r(As, 1, Bs, 0)                                       r(As, 0, Bs, 1)
 r(As, 2, Bs, 0)       r(As, 1, Bs, 1)            r(As,1, Bs, 1)          r(As, 0, Bs, 2)
 3, 0    2, 1        2, 1    1, 2             2, 1  1, 2                 1, 2,  0, 3

 00 01 10 11 20 21 22 32...   n * m



j= 0 1 2 3 4 5 6 7 8 9
   3 3 9 9 9 1 7 2 0 6

i= 0 1 2 3 4 5 6 7 8
   3 9 8 3 9 7 9 7 0


            9 0 0 0 0 0 0 0 0 0
            8 0 0 0 0 0 0 0 0 1
            7 0 0 0 0 0 0 0 0 0
            6 0 0 0 0 0 1 0 1 0
            5 0 0 0 0 0 0 0 0 0
            4 0 1 0 0 1 0 1 0 0
            3 0 1 0 0 1 0 1 0 0
            2 0 1 0 0 1 0 1 0 0
            1 1 0 0 1 0 0 0 0 0
       j =  0 1 0 0 1 0 0 0 0 0
         i =  0 1 2 3 4 5 6 7 8

                3 1 = best of(2 0, 3 0, 2 1)

         1 2 5 3 4
         5 1 2 3 4



            9 0 0 0 0 0 0 0 0 0
            8 0 0 0 0 0 0 0 0 1
            7 0 0 0 0 0 0 0 0 0
            6 0 0 0 0 0 1 0 1 0
            5 0 0 0 0 0 0 0 0 0
            4 0 1 0 0 1 0 1 0 0
            3 0 1 0 0 1 0 1 0 0
            2 0 1 0 0 1 0 1 0 0
            1 1 1 1 2 1 1 1 1 1
       j =  0 1 0 0 1 0 0 0 0 0
         i =  0 1 2 3 4 5 6 7 8


     */

    private static final class Node {
        public final int length;
        public final String seq;
        public final Node parent;
        public final boolean step;
        Node(int l, String sel, Node n, boolean step) { this.length = l; this.seq = sel; this.parent = n; this.step = step;}
        @Override
        public String toString() {
            return seq;
        }
    }

    private static String doWork(int[]As, int[]Bs) {
        Node[] prev = new Node[Bs.length];

        String maxSeq = "";

        for(int j = 0; j < Bs.length; ++j) {
            if(As[0] == Bs[j])
                prev[j] = new Node(1, Bs[j] + "", null, true);
            else
                prev[j] = new Node(0, "", null, false);
        }

        for(int i = 1; i < As.length; ++i) {
            Node[] current = new Node[Bs.length];
            for(int j = 0; j < Bs.length; ++j) {
                // best of (i-1, j-1 | i j-1 | i -1  j)
                Node parent;

                if(j == 0) {
                    parent = prev[j];
                } else {
                    parent = prev[j - 1];
                    if(!parent.step)
                        parent = max(parent, current[j - 1], prev[j]);
                }

                int currentLength = parent.length;
                String currentSeq = parent.seq;


                /*
                0 1 2 3 4 5 6 7 9    = i
                3 9 8 3 9 7 9 7 0

                0 1 2 3 4 5 6 7 8 9   = j
                3 3 9 9 9 1 7 2 0 6

                 */
                boolean step = false;
                if(As[i] == Bs[j]) {
                    ++currentLength;
                    currentSeq += " " + As[i];
                    currentSeq = currentSeq.trim();
                    step = true;
                }

                current[j] = new Node(currentLength, currentSeq, parent, step);
                if(currentSeq.length() > maxSeq.length())
                    maxSeq = currentSeq;
            }

            prev = current;
        }

        return maxSeq;
    }

    private static Node max(Node a, Node b, Node c) {
        if(a.length > b.length && a.length > c.length) return a;
        if(b.length > c.length) return b;
        return c;
    }

//    private static String recurse(int[]As, int AsIndex, int[]Bs, int BsIndex, String sequenceSoFar) {
//        if(As.length <= AsIndex || Bs.length <=BsIndex) return sequenceSoFar;
//
//        String retval;
//        if((retval = MEMO.get(sequenceSoFar + " " + AsIndex + " " + BsIndex)) != null) return retval;
//
//        if (As[AsIndex] == Bs[BsIndex]) {
//            sequenceSoFar += " " + As[AsIndex];
//            retval = recurse(As, AsIndex + 1, Bs, BsIndex + 1, sequenceSoFar);
//        } else {
//            String recurseA = recurse(As, AsIndex + 1, Bs, BsIndex, sequenceSoFar);
//            String recurseB = recurse(As, AsIndex, Bs, BsIndex + 1, sequenceSoFar);
//            retval = recurseA.length() > recurseB.length() ? recurseA : recurseB;
//        }
//
//        MEMO.put(sequenceSoFar + " " + AsIndex + " " + BsIndex, retval);
//        return retval;
//    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i].trim());
        }
        return ret;
    }
}
