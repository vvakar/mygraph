package vvakar.knapsack;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 8/10/14
 */
public class KnapsackTest {
    @Test
    public void test() {
        Knapsack.Item[] items = new Knapsack.Item[] {
                new Knapsack.Item(2, 1),
                new Knapsack.Item(4, 2),
                new Knapsack.Item(8, 3)};

        List<Integer> solution = new Knapsack().compute(3, items);

        assertEquals(items.length, solution.size());
        assertArrayEquals(new Integer[]{8, 0, 0}, solution.toArray(new Integer[0]));
    }
}
