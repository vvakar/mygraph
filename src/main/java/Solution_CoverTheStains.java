import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by valentin.vakar on 10/21/15.
 */
public class Solution_CoverTheStains {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] NK = strArray2intArray(br.readLine().split("\\s+"));
        int N = NK[0];
        int K = NK[1];
        List<Dot> dots = new ArrayList<Dot>();
        for(int i = 0; i < N; ++i) {
            int[] dot = strArray2intArray(br.readLine().split("\\s+"));
            dots.add(new Dot(dot[0], dot[1]));
        }

        System.out.println(doWork(dots, K));
    }

    private static int doWork(List<Dot> dots, int K) {
        Dot[] sortedByX = sortByX(dots);
        Dot[] sortedByY = sortByY(dots);
        int A = computeArea(sortedByX, sortedByY);
//        int areaSmallerThanX = get...();
//

        // TODO: finish this
        return 0 % 1000000007;
    }

    private static int computeArea(Dot[] byX, Dot[] byY) {
       return (byX[byX.length - 1].x -  byX[0].x) * (byY[byY.length - 1].y - byY[0].y);
    }

    private static Dot[] sortByX(List<Dot> list) {
        List<Dot> copy = new ArrayList<Dot>(list);
        copy.sort(new Comparator<Dot>() {
            public int compare(Dot o1, Dot o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });
        return copy.toArray(new Dot[0]);
    }

    private static Dot[] sortByY(List<Dot> list) {
        List<Dot> copy = new ArrayList<Dot>(list);
        copy.sort(new Comparator<Dot>() {
            public int compare(Dot o1, Dot o2) {
                return Integer.compare(o1.y, o2.y);
            }
        });
        return copy.toArray(new Dot[0]);
    }

    private static final class Dot {
        public final int x, y;
        public Dot(int x, int y) {
            this.x = x; this.y = y;
        }
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }
}
