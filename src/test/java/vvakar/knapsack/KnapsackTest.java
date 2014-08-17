package vvakar.knapsack;

import com.google.common.collect.Lists;
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

        List<Integer> solution = new Knapsack().computeDP(3, items);

        assertEquals(items.length, solution.size());
        assertArrayEquals(new Integer[]{8, 0, 0}, solution.toArray(new Integer[0]));
    }

    @Test
    public void testGreedyHeuristic() {

        int offByCount = 0;
        for(int i = 0; i < 1000; ++i) {
            int W = 20;
            Knapsack.Item[] items = generateItems(20);

            List<Integer> solutionDP = new Knapsack().computeDP(W, items);
            List<Integer> solutionGreedy = new Knapsack().computeHeuristic(W, false, items);

            int dp = solutionDP.get(0);
            int heur = solutionGreedy.get(0);
            double offBy =  Math.round(Math.abs((100d * (dp - heur)) / dp));

            String message = "";
            if(offBy > 30) {
                ++offByCount;
                message = " OH NOOOOO!";
            }
//            System.out.println("Off by " + offBy + "%" + message);
        }

        System.out.println("Was off " + offByCount + " times");

    }

    private Knapsack.Item[] generateItems(int howmany) {
        List<Knapsack.Item> list = Lists.newArrayListWithCapacity(howmany);
        for(int i = 0; i < howmany; ++i) {
            list.add(new Knapsack.Item( 1+(int)Math.floor(Math.random() * 10), 1+(int)Math.floor(Math.random() * 10)));
        }
        return list.toArray(new Knapsack.Item[0]);
    }
}
