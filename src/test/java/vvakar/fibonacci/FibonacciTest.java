package vvakar.fibonacci;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 8/10/14
 */
public class FibonacciTest {
    @Test
    public void testRecursive() {
        assertEquals(3, Fibonacci.recursive(4));
        assertEquals(5, Fibonacci.recursive(5));
        assertEquals(8, Fibonacci.recursive(6));
        assertEquals(13, Fibonacci.recursive(7));
//        assertEquals(12586269025L, Fibonacci.recursive(50)); // gonna take awhile!
    }

    @Test
    public void testDynamic() {
        assertEquals(1, Fibonacci.dynamic(2));
        assertEquals(3, Fibonacci.dynamic(4));
        assertEquals(5, Fibonacci.dynamic(5));
        assertEquals(8, Fibonacci.dynamic(6));
        assertEquals(13, Fibonacci.dynamic(7));
        assertEquals(12586269025L, Fibonacci.dynamic(50));
        assertEquals(23416728348467685L, Fibonacci.dynamic(80));


    }

    @Test
    public void testJavaSort() {

        benchmark(100000);
        benchmark(1000000);
        benchmark(10000000);
        benchmark(100000000);
        benchmark(200000000);
        benchmark(300000000);
        benchmark(400000000);
        benchmark(500000000);

    }

    private void benchmark(int TOTAL) {
        List<Integer> sortme = Lists.newArrayListWithCapacity(TOTAL);
          for(int i = 0; i < TOTAL; ++i) {
           sortme.add(i);
          }


        long start = System.currentTimeMillis();
        Collections.sort(sortme);
        System.out.println("Sorting " + TOTAL +" completed in " + (System.currentTimeMillis() - start) + "ms");
    }
}
