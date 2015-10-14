import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Solution_RedJohnIsBack2 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int loops = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < loops; ++i) {
            int N = Integer.parseInt(br.readLine().trim());

            System.out.println(solveIt(N));
        }
    }

    /**
     * Use n choose k for all available horizontal brick counts
     */
    private static long solveIt(int N) {
        long res = 0;
        for (int horizontalBricks = 0; horizontalBricks <= N / 4; ++horizontalBricks) {
            int n = N - (horizontalBricks * 4) + horizontalBricks;
            long b = nck(n, horizontalBricks);
            res += b;
        }

        return countPrimeNumbersUpTo(res);
    }

    /**
     * Use prime sieve to count the number of primes up to num
     */
    private static long countPrimeNumbersUpTo(long num) {
        if (num <= 1) return 0;
        if (num == 2) return 1;

        Set<Long> primes = new HashSet<Long>();
        for (long i = 2; i <= num; ++i) {
            primes.add(i);
        }

        for (long n : new HashSet<Long>(primes)) {
            for (int i = 2; i * i <= n; i += 1) {
                if (n % i == 0)
                    primes.remove(n);
            }
        }

        return primes.size();
    }

    private static long nck(int n, int k) {
        return factorial(BigInteger.valueOf(n))
                .divide(factorial(BigInteger.valueOf(k)).multiply(factorial(BigInteger.valueOf(n - k)))).longValue();
    }

    private static BigInteger factorial(BigInteger n) {
        BigInteger ret = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
            ret = ret.multiply(i);
        }
        return ret;
    }
}
