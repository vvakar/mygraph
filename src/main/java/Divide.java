import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author vvakar
 *         Date: 12/24/14
 */
public class Divide {
        public static void main(String[]asdf) throws Exception  {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


            while(true) {
                String[] lineStrs = br.readLine().split("\\s+");
                int[] Ns = strArray2intArray(lineStrs);

                System.out.println(divide2(Ns[0], Ns[1]));
            }
        }


    /*

                    10 2

                     numerator 0
                     denominator 2

                     mask 1
                     quotient 5

     */
    static int divide2(int origNumerator, int origDenominator) {

        // 1. make denominator as big as it can fit
        // 2. reduce denominator by 2x in each iteration:
        //        if(denominator <= numerator) {
        //           quotient |= current shift position
        //           numerator -= denominator;
        //         }

        int denominator = origDenominator;
        int numerator = origNumerator;
        int shiftPos = 1;
        int quotient = 0;

        while(denominator <= numerator) {
            denominator <<= 1;
            shiftPos <<= 1;
        }

        while(origDenominator <= numerator) {
            if(denominator <= numerator) {
                numerator = sub(numerator, denominator);
                quotient |= shiftPos;
            }
            shiftPos >>= 1;
            denominator >>= 1;

        }
        return quotient;
    }

    private static int sub(int a, int b) {
//        return a - b; // what we want
        return add(a, add(~b, 1));
    }

    /*
                10010  + 101100
     */
    private static int add(int a, int b) {
        int res = 0;
        int carry = 0;
        for(int shift = 0; shift < 32; ++shift) {
            int pos = 1 << shift;
            int abit = a & pos;
            int bbit = b & pos;
            final int resBit;

            if(abit == 0 && bbit == 0) {
                if(carry > 0) {
                    resBit = pos;
                    carry = 0;
                } else {
                    resBit = 0;
                }
            } else if(abit > 0 && bbit > 0) {
                if(carry > 0) {
                    resBit = pos;
                } else {
                    resBit = 0;
                }
                carry = pos;
            } else { // abit or bbit is > 0
                if(carry > 0) {
                    carry = pos;
                    resBit = 0;
                } else {
                    resBit = pos;
                }
            }

            res |= resBit;

        }

        return res;
    }



























    static int divide(int numerator, int denominator) {

        int mask = 0x1;
        int quotient = 0;

        while (denominator <= numerator) {
            denominator <<= 1;
            mask <<= 1;
        }

        while (mask > 1) {
            denominator >>= 1;
            mask >>= 1;
            if (numerator >= denominator) {
                numerator -= denominator;
                quotient |= mask;
            }
        }

        return quotient;
    }

    private static int[] strArray2intArray(String[] strs) {
        int[] ret = new int[strs.length];
        for(int i = 0; i < strs.length; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }

}
