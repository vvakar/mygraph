import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 1/26/15
 */
public class Solution_Equal {

    public static void main(String[]asdf) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int loops = Integer.parseInt(br.readLine().trim());

        for(int i = 0; i < loops; ++i) {
            Integer.parseInt(br.readLine().trim()); // IGNORE
            String[]strs = br.readLine().trim().split("\\s+");
            System.out.println(doWork(strArray2intArray(strs)));
        }
    }


    private static Set<List<Integer>> MEMOEDARRAYS;
    private static int doWork(int[]arr) {
        Integer[]arr2 = new Integer[arr.length];
        MEMOEDARRAYS = new HashSet();
        for(int i = 0; i < arr.length; ++i) arr2[i] = arr[i];
        return recurse(arr2);
    }

    private static int recurse(Integer[]arr2) {
        int depth = 0;
        List<List<Integer>> currentLevel = new ArrayList<List<Integer>>();
        currentLevel.add(Arrays.asList(arr2));

        while(true) {
            List<List<Integer>> nextLevel = new ArrayList();
            for(List<Integer> arr : currentLevel) {
                for (int i = 0; i < arr.size(); ++i) {
                    for (int incr : new int[]{1, 2, 5}) {
                        List<Integer> incremented = incrementAllExcept(arr, i, incr);
                        if (allValuesEqual(arr)) return depth;
                        if (!MEMOEDARRAYS.contains(incremented)) {
                            nextLevel.add(incremented);
                            MEMOEDARRAYS.add(incremented);
                        } else {
                            int asdf = 0;
                        }
                    }
                }
            }
            ++depth;
            currentLevel = nextLevel;
        }
    }

    private static boolean allValuesEqual(List<Integer> arr) {
        for(int i = 1; i < arr.size(); ++i) {
            if(arr.get(0) != arr.get(i)) return false;
        }
        return true;
    }
    private static List<Integer> incrementAllExcept(List<Integer>arr, int except, int increment) {
        List<Integer> ret = new ArrayList<Integer>();
        for(int i = 0; i < arr.size(); ++i) {
            int val = arr.get(i);
            if(i != except) {
                val += increment;
            }
            ret.add(val);
        }
        return ret;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i].trim());
        }
        return ret;
    }
}
