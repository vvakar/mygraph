import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author vvakar
 *         Date: 12/15/14
 */
public class Solution_QuickSort {

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
        System.out.println(countInsertionSortShifts(ar) - countQuickSortSwaps(ar));

    }

    public static int doWork(String str) {
        return 0;
    }


    public static int countInsertionSortShifts(int[]arr) {
        int count = 0;
        int[] sorted = new int[arr.length];

        TreeSet<Integer> set = new TreeSet<Integer>();

        for(int i = 0; i < arr.length; ++i) {
            count += set.tailSet(arr[i], false).size();
            set.add(arr[i]);
        }

        return count;
    }

    private static int QS_SWAPS = 0;
    public static int countQuickSortSwaps(int[]arr) {
        QS_SWAPS = 0;
        quickSort(arr);
        return QS_SWAPS;
    }

    /**
     * Quick sort using Lomuto partitioning
     */
    public static int[] quickSort(int[] arr) {
        return quickSort(arr, 0, arr.length - 1);
    }

    static int[] quickSort(int[] arr, int from, int to) {
        if(from >= to) return arr;
        if(arr.length <= 1) return arr;

        int pivotIndx = partitionLomuto(arr, from, to);
//        System.out.println(arrToString(arr));

        quickSort(arr, from, pivotIndx - 1);
        quickSort(arr, pivotIndx + 1, to);
        return arr;
    }

    private static String arrToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for(int a : arr) {
            if(sb.length() > 0) sb.append(" ");
            sb.append(a);
        }
        return sb.toString();
    }

    private static int partitionLomuto(int[]arr, int from, int to)  {
        int p = getPivotLocation(arr, from, to);
        int pVal = arr[p];
        swap(arr, p, to, true);

        int storeIndex = from;
        for(int i = from; i<= to; ++i) {
            if(arr[i] < pVal) {
                swap(arr, i, storeIndex, true);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, to, false);
        return storeIndex;
    }

    private static int getPivotLocation(int[] arr, int from, int to) {
        if(true) return to;

        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for(int i = from ; i<=to; ++i) {
            map.put(arr[i], i);
        }

        int i = 0;
        for(Integer v : map.values()) {
            if(i == map.size() / 2) {
                return v;
            }
            ++i;
        }
        throw new RuntimeException("Cant find p");
    }

    private static void swap(int[] arr, int a, int b, boolean count) {
        if(count) ++QS_SWAPS;
        int temp = arr[b];
        arr[b] = arr[a];
        arr[a] = temp;
    }


    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
