package vvakar.algos1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin.vakar on 10/20/15.
 */
public class Sorting {
    public enum PartitioningStrategy {MEDIAN_OF_THREE, FIRST, LAST}

    public static void main(String[] s) throws IOException {
        for (PartitioningStrategy strategy : new PartitioningStrategy[]{PartitioningStrategy.FIRST, PartitioningStrategy.LAST, PartitioningStrategy.MEDIAN_OF_THREE}) {
            int[] arr = readArray();
            System.out.println(strategy + " " + quicksort(arr, strategy));
        }
    }

    private static int[] readArray() throws IOException {
        List<Integer> list = new ArrayList<Integer>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Sorting.class.getClassLoader()
                                .getResourceAsStream("QuickSortAlgos1.txt")));
//        .getResourceAsStream("QuickSortAlgos1_10items.txt")));
//        .getResourceAsStream("QuickSortAlgos1_100items.txt")));
//        .getResourceAsStream("QuickSortAlgos1_1000items.txt")));

        while (reader.ready()) {
            String line = reader.readLine();
            list.add(Integer.parseInt(line));
        }

        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; ++i)
            arr[i] = list.get(i);

        return arr;
    }

    /**
     * Quicksort and return number of comparisons
     */
    public static int quicksort(int[] arr, PartitioningStrategy strategy) {
        return qsort(arr, 0, arr.length, strategy);
    }

    private static int qsort(int[] arr, int from, int to, PartitioningStrategy strategy) {
        int length = to - from;
        if (length <= 1) return 0;
        int p = partition(arr, from, to, strategy);
        return length - 1
                + qsort(arr, from, p, strategy)
                + qsort(arr, p + 1, to, strategy);
    }


    private static int partition(int[] arr, int from, int to, PartitioningStrategy strategy) {
        arrangePivot(arr, from, to, strategy);
        int p = arr[from];
        int i = from;

        for (int j = i; j < to; ++j) {
            if (arr[j] < p) {
                swap(arr, i + 1, j);
                i++;
            }
        }
        swap(arr, from, i);
        return i;
    }

    private static void arrangePivot(int[] arr, int from, int to, PartitioningStrategy strategy) {
        switch (strategy) {
            case FIRST:
                return;
            case LAST:
                swap(arr, from, to - 1);
                return;
            case MEDIAN_OF_THREE:
                applyMedOfThree(arr, from, to);
//                System.out.println(" -> " +arr[from]);
                return;
            default:
                throw new IllegalStateException();
        }

    }

    public static void applyMedOfThree(int[] arr, int from, int to)  {
        int middle = (to + from - 1) / 2;
        int a = arr[from];
        int b = arr[middle];
        int c = arr[to - 1];
//        System.out.print("M3 Candidates (" + from + "-" + to +  "): " + a + " " + b + " " + c);
        if ((a >= b && a <= c) || (a >= c && a <= b)) {
//            System.out.print(" first ");
            return;
        }
        if ((b >= a && b <= c) || (b >= c && b <= a)) {
//            System.out.print(" middle ");
            swap(arr, from, middle);
            return;
        }
        if ((c >= a && c <= b) || (c >= b && c <= a)) {
//            System.out.print(" last ");

            swap(arr, from, to - 1);
            return;
        }
        throw new IllegalStateException("Can't find median from " + a + " " + b + " " + c);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
