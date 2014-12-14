import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Solution_Team_Formation {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int testCases = Integer.parseInt(br.readLine().trim());  // max 100

        for(int i = 0; i < testCases; ++i) {
            String[] lineStrs = br.readLine().split("\\s+");
            int[] countAndSkills = strArray2intArray(lineStrs);


            System.out.println(solveIt(countAndSkills));

        }
    }

    public static int solveIt(int[] countAndSkills) {
        if(countAndSkills.length == 1) return 0;

        int[] skills = Arrays.copyOfRange(countAndSkills, 1, countAndSkills.length);
        Arrays.sort(skills);



        PriorityQueue<Set<Integer>> previousSets = getPQ(); // ones ending right before current
        PriorityQueue<Set<Integer>> nextSets = getPQ(); // ending at current

        // 6
        // current 5
        // set  1234
        // previousSets
        // nextSets 34
        int min = Integer.MAX_VALUE;
        int prev = skills[0];
        for(int current : skills) {
            final Set<Integer> set;
            if(current == prev) {
                // found duplicate - check previous sets for a match
                if(!previousSets.isEmpty()) {
                     // swap in previous smallest set if there is one
                    set = previousSets.poll();
                } // otherwise start new set
                else {
                    set = new HashSet<Integer>();
                }
                set.add(current); // add current to whatever set we wind up with
                nextSets.add(set); // this set is now waiting for current + 1
            } else if(current == prev + 1) { // next in sequence
                // if empty set or next in sequence - add
                if(!nextSets.isEmpty()) {
                    set = nextSets.poll();
                } else {
                    set = new HashSet<Integer>();
                }

                if(set.contains(current))
                    throw new RuntimeException("Contains " + current);

                // add to min sized next set
                set.add(current);
                // previous sets is now fallen behind
                if(!previousSets.isEmpty())
                    min = Math.min(min, previousSets.poll().size());

                previousSets = nextSets; // TODO: save min first!
                nextSets = getPQ();
                nextSets.add(set); // waiting for current + 1
            } else {

                // step over to clean slate
                if(!previousSets.isEmpty())
                    min = Math.min(min, previousSets.poll().size());
                if(!nextSets.isEmpty())
                    min = Math.min(min, nextSets.poll().size());

                previousSets = getPQ();
                nextSets = getPQ();
                set = new HashSet<Integer>();
                set.add(current);
                nextSets.add(set);
            }

            prev = current;
        }


        if(!previousSets.isEmpty()) min = Math.min(min, previousSets.poll().size());
        if(!nextSets.isEmpty()) min = Math.min(min, nextSets.poll().size());
        return min;
    }

    private static PriorityQueue<Set<Integer>> getPQ() {
        return new PriorityQueue<Set<Integer>>(3, new Comparator<Set<Integer>>() {
            @Override
            public int compare(Set<Integer> o1, Set<Integer> o2) {
                return o1.size() - o2.size();
            }
        });
    }

    private static long[] strArray2longArray(String[] strs) {
        long[] ret = new long[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Long.parseLong(strs[i]);
        }
        return ret;
    }
    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
