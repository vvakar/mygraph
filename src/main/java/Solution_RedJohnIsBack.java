import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * @author vvakar
 *         Date: 1/13/15
 */
public class Solution_RedJohnIsBack {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int loops = Integer.parseInt(br.readLine().trim());

        for(int i = 0; i < loops; ++i) {
            System.out.println(doWork(Integer.parseInt(br.readLine().trim())));
        }
    }

    /*
    brickLength = 4

    if(N < 4) return 1;
    if(N == 4) return 2;
    if(N == 5) return 1 + 2;
    if(N == 6) return 1 + 3;
    if(N == 7) return 1 + 4;
    if(N == 8) return 1 + 5 + 1;
    ...
    else return 1 + (N - 3)




     */
    private static long doWork(int N) {
        long count = 0;

        int flats = 0;
        while(flats * 4 <= N) {
            count += countFlatPositions(flats, N - (flats * 4));
            ++flats;
        }

        return countPrimesLessThan(count);
    }

    private static long countFlatPositions(int flats, int standings) {
        BigInteger total = factorial(flats + standings)
                    .divide(factorial(flats).multiply(factorial(standings)));

        return total.longValue();
    }

    private static BigInteger factorial(int a) {
        BigInteger total = BigInteger.ONE;
        for(int i = 1; i <= a; ++i) {
            total = total.multiply(BigInteger.valueOf(i));
        }
        return total;
    }
    private static int countPrimesLessThan(long n) {
        if(n <= 2) return 0;


        int count = 1; // n > 2, at least two primes
        outer: for (int m = 3; m <= n; ++m) {   // 3 4 5 6 ... n-1
            for (int i = 2; i <= Math.sqrt(m); ++i) {
                if (m % i == 0) continue outer;
            }
            ++count;
        }
        return count;
    }
}
