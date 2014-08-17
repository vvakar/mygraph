package vvakar.knapsack;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 8/10/14
 */
public class Knapsack {
    /**
     * Greedily favor highest v/w ratios until full.
     * If there's one single item that's better than above, use that one single item instead.
     */
    public List<Integer> computeHeuristic(int W, boolean performStep3, Item... items) {
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return - Math.round((1.0f * o1.getValue()) / o1.getWeight() -
                                  (1.0f * o2.getValue()) / o2.getWeight());
            }
        });


        List<Integer> retval = Lists.newLinkedList();
        int totalWeight = 0, totalValue = 0;
        Item max = null;
        for(Item item  : items) {
            if(totalWeight + item.getWeight() <= W) {
                totalWeight += item.getWeight();
                totalValue += item.getValue();
            }

            retval.add(0, totalValue);

            // track max value that fits
            if(item.getWeight() <= W && (max == null || max.getValue() < item.getValue())) {
                max = item;
            }
        }

        if(performStep3 && max != null && totalValue < max.getValue()) {
            retval.clear();
            retval.add(max.getValue());
        }

        return retval;
    }


    public List<Integer> computeDP(int W, Item... items) {
        // initialize matrix
        int[][] matrix = new int[items.length+1][];
        for(int i = 0; i < matrix.length; ++i) {
            matrix[i] = new int[W+1];
            matrix[i][0] = 0;
        }

        // main thing
        for(int i = 1; i < matrix.length; ++i) {
            for(int w = 0; w <= W; ++w) {
                Item current = items[i - 1];
                int previousCandidateWeight = w - current.getWeight();
                int candidateNewValue = 0;
                if(previousCandidateWeight >= 0) {
                    int previousValue = matrix[i-1][previousCandidateWeight];
                    if((previousCandidateWeight + current.getWeight()) <= w) {
                        candidateNewValue = previousValue + current.getValue();
                    }
                }

                int bestSoFar = Math.max(matrix[i-1][w], candidateNewValue);
                matrix[i][w] = bestSoFar;
            }
        }

        // Compute backtrack
        List<Integer> backtrack = Lists.newArrayListWithCapacity(items.length);
        for(int i = matrix.length - 1, w = W;
            i > 0;
            --i) {
            int value = matrix[i][w];
            backtrack.add(value);

            if(value > matrix[i-1][w]) {
                w -= items[i-1].getWeight();
            }
        }

        return backtrack;
    }

    public static class Item {
        private int value, weight;
        public Item(int value, int weight) {
            this.value=value;
            this.weight = weight;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }
    }

}
