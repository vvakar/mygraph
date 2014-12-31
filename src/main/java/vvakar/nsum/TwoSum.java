package vvakar.nsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static vvakar.nsum.TwoSum.Pair.*;

/**
 * @author vvakar
 *         Date: 12/26/14
 */
public class TwoSum {
    /**
     * Commutative pair of two numbers. All operations should anticipate a being swapped with b.
     */
    public static final class Pair {
        public final int a, b;
        private Pair(int a, int b) { this.a = a; this.b = b; }
        public static Pair Pair(int a, int b) {
            return new Pair(a,b);
        }

        @Override
        public boolean equals(Object o) {
            if(o == null || !(o instanceof Pair)) return false;

            Pair other = (Pair)o;
            return (a == other.a || a == other.b) && (b == other.a || b == other.b);
        }

        @Override
        public int hashCode() {
            return 31 * a + 31 * b;
        }

        @Override
        public String toString() {
            return "[a = " + a + ", b = " + b+ "]";
        }
    }

    public static Set<Pair> getPairs(int[] arr, int sum) {
        Set<Pair> pairs = new HashSet<Pair>();

//        Arrays.sort(arr);

        // n log n
//        for(int i = 0; i < arr.length; ++i) {
//            int maybeFoundAtIndex = Arrays.binarySearch(arr, sum - arr[i]);
//            if(maybeFoundAtIndex >= 0 && arr[maybeFoundAtIndex] + arr[i] == sum)
//                pairs.add(Pair(arr[i], arr[maybeFoundAtIndex]));
//        }

        // O(n)
        Set<Integer> seen = new HashSet<Integer>();
        for(int i = 0; i < arr.length; ++i) {
            int counterpart = sum - arr[i];
            if(seen.contains(counterpart)) {
                pairs.add(Pair(arr[i], counterpart));
            }
            seen.add(arr[i]);
        }


        return pairs;
    }
}
