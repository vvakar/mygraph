package vvakar.strings;

import javax.xml.stream.events.Characters;
import java.io.FileInputStream;
import java.io.ObjectStreamField;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vvakar
 *         Date: 12/30/14
 */
public class SuffixArrays {

    public static void main(String[]asdf) throws Exception {
        findLargestRepeatedSubstring("/Users/vvakar/Downloads/mobydick.txt");
    }

    public static void findLargestRepeatedSubstring(String filename) throws Exception {
        final FileInputStream in = new FileInputStream(filename);
        int c;
        boolean prevIsLetter = false;
        final StringBuilder sb = new StringBuilder();
        while((c = in.read()) != -1) {
            boolean isLetter = Character.isLetter(c);

            if(isLetter) {
                sb.append((char)c);
            } else if(prevIsLetter) {
                sb.append(' ');
            }

            prevIsLetter = isLetter;
        }

        final MyString wholeBook = new MyString(sb.toString().toCharArray());


        List<MyString> suffixes = new LinkedList<MyString>();
        final int length = wholeBook.length();
        for(int i = 0; i < length; ++i) {
            MyString str = wholeBook.substring(i);
            if(Character.isLetter(str.charAt(0))) suffixes.add(str);
        }

        MyString[] sorted = suffixes.toArray(new MyString[0]);
        Arrays.sort(sorted);
        int maxOverlap = -1;
        MyString maxOverlapString = null;

        for(int i = 0; i < sorted.length - 1; ++i) {
            // compare sorted[i] vs sorted[i + 1]
            // and get max overlap
            // if that's the biggest overlap, save that MyString
            int overlap = sorted[i].getOverlapLength(sorted[i+1]);
            if(overlap > maxOverlap) {
                maxOverlap = overlap;
                maxOverlapString = sorted[i];
            }
        }



        System.out.println("Max overlapping string: \n\n" + (maxOverlapString == null ? "---" :
                maxOverlapString.substring(0, maxOverlap)));
    }

    /**
     * Flyweight immutable string implementation to reuse underlying buffer.
     */
    private static final class MyString implements Comparable<MyString> {
        private final char[] arr;
        private final int from, to;

        MyString(char[] arr) {
            this(arr, 0, arr.length);
        }

        MyString(char[] arr, int from, int to) {
            this.arr = arr;
            this.from = from;
            this.to = to;
        }

        public int getOverlapLength(MyString other) {
            int i = 0;
            for(; i < length()
                    && i < other.length()
                    && charAt(i) == other.charAt(i)
                    ; ++i);

            return i;
        }


        public MyString substring(int from, int to) {
            return new MyString(arr, this.from + from, this.from + to);
        }

        public MyString substring(int from) {
            return substring(from, arr.length);
        }

        public int length() {
            return to - from;
        }

        public char charAt(int i) {
            return arr[from + i];
        }

        @Override
        public int compareTo(MyString o) {
            if(o == null) return 1;

            for(int i = 0; i < Math.min(o.length(), length()); ++i) {
                if(charAt(i) != o.charAt(i)) return Character.compare(charAt(i), o.charAt(i));
            }

            return Integer.compare(length(), o.length()); // if all is equal so far, longer string wins
        }

        @Override
        public String toString() {
            return new String(Arrays.copyOfRange(arr, from, to));
        }

    }
}
