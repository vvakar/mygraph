package vvakar.jobs;

import org.junit.Test;
import vvakar.graph.Util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author vvakar
 *         Date: 8/24/14
 */
public class JobsTest {
    @Test
    public void testGreedyByDifference() throws Exception {
        List<Util.JobBean> coll = Util.getJobs("jobs.txt");
        Collections.sort(coll, new Comparator<Util.JobBean>() {
            @Override
            public int compare(Util.JobBean o1, Util.JobBean o2) {
                int a = o1.getWeight() - o1.getLenght();
                int b = o2.getWeight() - o2.getLenght();

                if(a == b) {
                    // if two jobs have equal difference (weight - length), you should schedule the job with higher weight first.
                    return o2.getWeight() - o1.getWeight();
                } else {
                    // schedules jobs in decreasing order of the difference (weight - length)
                    return b - a;
                }
            }
        });

        int total = getTotalWeightedCompletionTimes(coll);
        System.out.println("TOTAL: " + total);

    }

    @Test
    public void testGreedyByRatio() throws Exception {
        List<Util.JobBean> coll = Util.getJobs("jobs.txt");
        Collections.sort(coll, new Comparator<Util.JobBean>() {
            @Override
            public int compare(Util.JobBean o1, Util.JobBean o2) {
                int a = o1.getWeight() / o1.getLenght();
                int b = o2.getWeight() / o2.getLenght();
                // in decreasing order of the ratio (weight/length)
                return b - a;
            }
        });

        int total = getTotalWeightedCompletionTimes(coll);
        System.out.println("TOTAL by RATIO: " + total);

    }

    private int getTotalWeightedCompletionTimes(List<Util.JobBean> list )  {
        int actualLength = 0;

        for(Util.JobBean bean : list) {
            actualLength += actualLength + bean.getLenght();
        }
        return actualLength;
    }
}
