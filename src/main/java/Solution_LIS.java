import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author vvakar
 *         Date: 1/18/15
 */
public class Solution_LIS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int loops = Integer.parseInt(br.readLine().trim());

        int[]arr = new int[loops];
        for(int i = 0; i < loops; ++i) {
            arr[i] = (Integer.parseInt(br.readLine().trim()));
        }
        System.out.println(doWork(arr));
    }

    /*
         2 7 4 3 8

         2 7  8
         2 7 8 - sorted

         edit distance (removes only): 4 => LIS = 5 - (4/2)

         2
         2 3
         2 4
         2 7

                            3 (2)
                          /       \
                        2(1)      4(2)
                                      \
                                      7(2)


                     7
                  7     2
             2       7     0
           0   2   7   2   0
          0 0 1 2 7 0 0 2 0 0
          0 1 2 3 4 5 6 7 8 9 ... 10^5

         2 7 8
         - what's the longest subsequence ending in < 8?

     */
    static int doWork(int[]arr) {
        MaxSegmentTree sTree = new MaxSegmentTree((10 * 10 * 10 * 10 * 10) + 1);

        int max = Integer.MIN_VALUE;
        for(int a : arr) {
            int newMax = sTree.query(a - 1) + 1;
            sTree.update(a, newMax);
            max = Math.max(max, newMax);
        }

        return max;
    }

    private static final class MaxSegmentTree {
        private final int[] tree;

        MaxSegmentTree(int arraySize) {
            int nextPowerOf2 = nextPowerOfTwo(arraySize);
            tree = new int[nextPowerOf2 *2 - 1];
            Arrays.fill(tree, 0);
        }

        private static int nextPowerOfTwo(int N) {
            for(int i = 0; i < 32; ++i) {
                int num = 1 << i;
                if((num) >= N) return num;
            }
            throw new IllegalStateException();
        }

        public void update(int index, int value) {
            int indexIntoTree = indexOf(index);
            tree[indexIntoTree] = value;
            propagateUp(indexIntoTree, value);
        }

        private int indexOf(int i) {
            int zero = tree.length / 2;
            return zero + i;
        }

        private void propagateUp(int index, int value) {
            if(index == 0) return;
            int parent = getParentOf(index);
            if(tree[parent] < value) {
                tree[parent] = value;
                propagateUp(parent, value);
            }
        }

        private int getParentOf(int index) {
            return (index - 1) / 2;
        }


        /*
                      - if even, check parent
              - if odd, check --i
              - recurse until the zero-line: 0 1 3 7 15...


                       0
                  1            2
               3    4      5       6
              7 8  9 10  11 12   13 14
              0 1  2 3   4  5    6  7


         */
        public int query(int toInclusive) {
            if(toInclusive < 0) throw new IllegalStateException();

            int index = indexOf(toInclusive);
            return findMaxFromIndex(index);
        }

        private int findMaxFromIndex(int index) {
            if(isPowerOfTwoMinusOne(index)) return tree[index];

            if(index % 2 == 1)
                return Math.max(tree[index], findMaxFromIndex(index - 1));
            else
                return Math.max(tree[index], findMaxFromIndex(getParentOf(index)));
        }

        private boolean isPowerOfTwoMinusOne(int n) {
            int findMe = n + 1;

            for(int i = 0; i < 32; ++i) {
                if(1 << i == findMe) return true;
            }
            return false;
        }

    }
}
