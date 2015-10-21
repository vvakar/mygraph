package vvakar.algos1;

import org.junit.Test;
import org.mockito.internal.util.ArrayUtils;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by valentin.vakar on 10/20/15.
 */
public class SortingTest {
    @Test
    public void testQuickSort_first() {
        testQuickSort(new int[]{1, 3, 2}, 3, Sorting.PartitioningStrategy.FIRST);
        testQuickSort(new int[]{1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30}, 435, Sorting.PartitioningStrategy.FIRST);
        testQuickSort(new int[]{2, 3, 2, 1, 6, 8, 5, 4, 56, 65, 7, 7, 4, 33, 544, 5, 6, 4, 34, 43, 53, 6, 45, 645, 77, 6, 5675, 67, 567, 56, 75, 76, 65, 7}, 234, Sorting.PartitioningStrategy.FIRST);
    }

    @Test
    public void testQuickSort_last() {
        testQuickSort(new int[]{1, 3, 2}, 2, Sorting.PartitioningStrategy.LAST);
        testQuickSort(new int[]{1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30}, 435, Sorting.PartitioningStrategy.LAST);
        testQuickSort(new int[]{2, 3, 2, 1, 6, 8, 5, 4, 56, 65, 7, 7, 4, 33, 544, 5, 6, 4, 34, 43, 53, 6, 45, 645, 77, 6, 5675, 67, 567, 56, 75, 76, 65, 7}, 234, Sorting.PartitioningStrategy.FIRST);
    }

    @Test
    public void testQuickSort_m3() {
        testQuickSort(new int[]{1, 3, 2}, 2, Sorting.PartitioningStrategy.MEDIAN_OF_THREE);
        testQuickSort(new int[]{1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30, 31}, 181, Sorting.PartitioningStrategy.MEDIAN_OF_THREE);
        testQuickSort(new int[]{2, 3, 2, 1, 6, 8, 5, 4, 56, 65, 7, 7, 4, 33, 544, 5, 6, 4, 34, 43, 53, 6, 45, 645, 77, 6, 5675, 67, 567, 56, 75, 76, 65, 7}, 234, Sorting.PartitioningStrategy.FIRST);

    }

    private void testQuickSort(int[] arr, int comparisonsExpected, Sorting.PartitioningStrategy strategy) {
        int[] arrToSort = Arrays.copyOf(arr, arr.length);

        int comparisons = Sorting.quicksort(arrToSort, strategy);

        Arrays.sort(arr);
        assertArrayEquals(arr, arrToSort);
        assertEquals(comparisonsExpected, comparisons);
    }

    @Test
    public void testMedOfThree() {
//        int[] a = new int[]{8, 2, 4, 5, 7, 1};
        int[] a = new int[]{4,5,6,7};
        Sorting.applyMedOfThree(a, 0, a.length);
        System.out.println(a[0]);
    }


}
