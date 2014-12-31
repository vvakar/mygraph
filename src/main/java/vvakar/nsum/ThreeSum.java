package vvakar.nsum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 12/26/14
 */
public class ThreeSum {

    public static Set<Triple> getTriples(int[] arr, int sum) {
        Set<Triple> triples = new HashSet<Triple>();

        // O(n^3)
//        for(int i = 0; i < arr.length -2; ++i) {
//            for(int j = i + 1; j < arr.length - 1; ++j) {
//                for(int k = j + 1; k < arr.length; ++k) {
//                    if(arr[i] + arr[j] + arr[k] == sum) {
//                        triples.add(new Triple(arr[i], arr[j], arr[k]));
//                    }
//                }
//            }
//        }


        // O(n^2)
        for(int i = 0; i < arr.length - 2; ++i) {
            int targetSum = sum - arr[i];
            Set<TwoSum.Pair> pairs = TwoSum.getPairs(Arrays.copyOfRange(arr, i + 1, arr.length), targetSum);
            for(TwoSum.Pair pair : pairs) {
                triples.add(new Triple(pair.a, pair.b, arr[i]));
            }
        }

        return triples;
    }

    public static class Triple {
        public final int a,b,c;
        public Triple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Triple triple = (Triple) o;

            return (a == triple.a || a == triple.b || a == triple.c) &&
                   (b == triple.a || b == triple.b || b == triple.c) &&
                    (c == triple.a || c == triple.b || c == triple.c);
        }

        @Override
        public int hashCode() {
            return 31 * a + 31 * b + 31 * c;
        }

        @Override
        public String toString() {
            return "{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }
}
