import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 12/24/14
 */
public class FindSquareRoot {
    public static void main(String[]asdf) throws Exception  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        while(true) {
            final double num = Double.parseDouble(br.readLine().trim());
            System.out.println(sqrt(num));
        }
    }


    static final double EPSILON = 0.001d;
    static double sqrt(double num) {
        return recurse(num, num);
    }

    private static double recurse(double num, double initialCandidate) {

        double candidate = initialCandidate;
        double squared = candidate * candidate;

        while(Math.abs(squared - num) > EPSILON) {
            if(squared > num)
                candidate = candidate * 0.75;
            else
                candidate = candidate * 1.5;

            squared = candidate * candidate;
        }

        return candidate;
    }
}
