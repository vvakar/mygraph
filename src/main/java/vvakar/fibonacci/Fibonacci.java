package vvakar.fibonacci;

/**
 * @author vvakar
 *         Date: 8/10/14
 */
public class Fibonacci {

    // naive O(2^n) implementation
    public static long recursive(long n) {
        if(n == 0) return 0;
        if(n == 1) return 1;

        return recursive(n-1) + recursive(n-2);
    }


    // 0 -> 0, 1-> 1, 2 -> 1, 3 -> 2, 4 -> 3 ...
    // O(n) factorial implementation based on dynamic programming approach
    public static long dynamic(long n) {
        if(n == 0) return 0;
        if(n == 1) return 1;

        long prev = 1, prevprev = 0;

        for(int i = 2; i < n; ++i) {
            long sum = prev + prevprev;
            prevprev = prev;
            prev = sum;
        }

        return prev + prevprev;
    }
}
