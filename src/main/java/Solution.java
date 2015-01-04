import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author vvakar
 *         Date: 12/22/14
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine();
        int Qcount = Integer.parseInt(QcountStr);

        for(int i = 0; i < Qcount; ++i) {
            String[] line = br.readLine().split("\\s+");
            System.out.println(doWork(Integer.parseInt(line[0]), line[1], line[2]));
        }
    }

    /*

    torino
    012345
    tungnrin
    01234567

  5 0 0 0 0 0 0 0 0
  4 0 0 1 0 1 0 0 1
  3 0 0 0 0 0 0 1 0
  2 0 0 0 0 0 1 0 0
  1 0 0 0 0 0 0 0 0
  0 1 0 0 0 0 0 0 0
    0 1 2 3 4 5 6 7


     */
    static int doWork(int distance, String left, String right) {
        MyString[] arrA = toSuffixArray(left);
        MyString[] arrB = toSuffixArray(right);

        int i = 0, j = 0;
        int backlinkA = -1, backlinkB = -1; // locations of overlapping strings
        int maxOverlap = -1; // max amount of overlap recorded so far
//        while(i < arrA.length && j < arrB.length) {
        for(MyString a : arrA)  {
            for(MyString b : arrB) {
//            MyString a = arrA[i];
//            MyString b = arrB[j];

                int overlapCount = computeOverlap(a.str, b.str, distance);
                if (overlapCount > maxOverlap) {
                    maxOverlap = overlapCount;
                    backlinkA = a.backlink;
                    backlinkB = b.backlink;
                }

//                int compRes = a.str.compareTo(b.str);
//                if (compRes == 0) {
//                    ++i;
//                    ++j;
//                } else if (compRes < 0) { // a < b
//                    ++i;
//                } else {
//                    ++j;
//                }
            }
        }

//        return resolve(left, right, distance, backlinkA, backlinkB, maxOverlap);
        return maxOverlap;
    }

    private static int resolve(String a, String b, int distance, int backlinkA, int backlinkB, int overlap) {
        int idxA = backlinkA, idxB = backlinkB;
        int finalOverlap = overlap;

        int availableDistance = distance;

        while(availableDistance > 0) {
            if(idxA > 0 && idxB > 0) {
                --idxA; --idxB; ++finalOverlap;
                if(a.charAt(idxA) != b.charAt(idxB))
                    --availableDistance;
            } else if(((idxA + finalOverlap) < a.length() - 1) && ((idxB + finalOverlap) < b.length() - 1)) {
                ++finalOverlap;
                if(a.charAt(idxA + finalOverlap) != b.charAt(idxB + finalOverlap))
                    --availableDistance;
            } else {
                break;
            }
        }
        return finalOverlap;
    }

    private static int computeOverlap(String a, String b, int distance) {
        int i = 0;
        while(i < a.length()
                && i < b.length()
                && (a.charAt(i) == b.charAt(i) || distance-- > 0))
            ++i;

        return i;
    }

    private static final class MyString implements Comparable<MyString>{
        public final int backlink;
        public final String str;
        MyString(int backlink, String str) { this.backlink = backlink; this.str = str;}

        @Override
        public int compareTo(MyString other) {
            return str.compareTo(other.str);
        }
        @Override
        public String toString() {
            return str;
        }
    }

    private static MyString[] toSuffixArray(String s) {
        MyString[] arr = new MyString[s.length()];
        for(int i =0; i < s.length(); ++i) {
            arr[i] = new MyString(i, s.substring(i));
        }
        Arrays.sort(arr);
        return arr;
    }
}