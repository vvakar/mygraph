package vvakar.coverage;

/**
 * @author vvakar
 *         Date: 2/1/15
 */
public class Coverage {
    public static void twoFlags(boolean a, boolean b) {
        int i = 0;
        if(a || b) {
            ++i;
        } else {
            --i;
        }
    }
}
