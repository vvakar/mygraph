package vvakar.strings;

import javax.xml.stream.events.Characters;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectStreamField;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vvakar
 *         Date: 12/30/14
 */
public class SuffixArrays {

    public static void main(String[]asdf) throws Exception {
//        findLargestRepeatedSubstring("/Users/vvakar/Downloads/mobydick.txt");
//        findLargestRepeatedSubstring("/Users/vvakar/Downloads/pride.txt");
//        findLargestRepeatedSubstring("/Users/vvakar/Downloads/janeeyre.txt");
//

        int N = 30;
//        countTopSubstringsOfSize("/Users/vvakar/Downloads/mobydick.txt", N);
        countTopSubstringsOfSize("/Users/vvakar/Downloads/pride.txt", N);
//        countTopSubstringsOfSize("/Users/vvakar/Downloads/janeeyre.txt", N);
    }

    public static void countTopSubstringsOfSize(String filename, int size) throws Exception {
        final MyString wholeBook = readFully(filename);

        MyString[] suffixes = buildSuffixArray(wholeBook, size);

        Map<MyString, Integer> counts = new HashMap<MyString, Integer>();
        for(int i = 0; i < suffixes.length -1; ++i) {
            int val = counts.getOrDefault(suffixes[i], 0);
            counts.put(suffixes[i], val + 1);
        }


        // top N - get a min heap
        // when heap size > N, remove one item
        // will be left with the max N items
        int N = 10;
        PriorityQueue<MyStringCount> pq = new PriorityQueue<MyStringCount>();
        for(Map.Entry<MyString, Integer> entry : counts.entrySet()) {
            pq.add(new MyStringCount(entry.getKey(), entry.getValue()));
            if(pq.size() > N) pq.poll();
        }

        System.out.println("\n\nTOP COUNTS for " + filename);
        while(!pq.isEmpty()) {
            MyStringCount c = pq.poll();
            System.out.println("--> " + c.myString + " = " + c.count);
        }

    }

    private static final class MyStringCount implements Comparable<MyStringCount> {
        public final int count;
        public final MyString myString;
        MyStringCount(MyString myString, int count) { this.myString = myString; this.count = count; }

        @Override
        public int compareTo(MyStringCount o) {
            if(o == null) return 1;
            return Integer.compare(count, o.count);
        }
    }


    public static void findLargestRepeatedSubstring(String filename) throws Exception {
        final MyString wholeBook = readFully(filename);


        List<MyString> suffixes = new LinkedList<MyString>();
        final int length = wholeBook.length();
        for(int i = 0; i < length; ++i) {
            MyString str = wholeBook.substring(i);
            if(Character.isLetter(str.charAt(0))) suffixes.add(str);
        }

        MyString[] sorted = buildSuffixArray(wholeBook, Integer.MAX_VALUE);
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



        System.out.println("Max overlapping string in " + filename + ": \n\n" + (maxOverlapString == null ? "---" :
                maxOverlapString.substring(0, maxOverlap)));
    }

    private static MyString[] buildSuffixArray(MyString master, int maxLen) {
        List<MyString> suffixes = new LinkedList<MyString>();
        final int length = master.length();
        for(int i = 0; i < length; ++i) {
            MyString str = master.substring(i, Math.min(i + maxLen, master.length()));
            if(Character.isLetter(str.charAt(0))) suffixes.add(str);
        }

        MyString[] sorted = suffixes.toArray(new MyString[0]);
        Arrays.sort(sorted);
        return sorted;
    }

    /**
     * Flyweight immutable string implementation to reuse underlying buffer.
     */
    private static final class MyString implements Comparable<MyString> {
        private final char[] arr;
        private final int from, to;
        private Integer hashcode;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyString myString = (MyString) o;
            return this.compareTo(myString) == 0;
        }

        @Override
        public int hashCode() {
            if(hashcode == null) {
                hashcode = this.toString().hashCode();
            }
            return hashcode;
        }
    }


    private static MyString readFully(String filename) throws IOException {
        final FileInputStream in = new FileInputStream(filename);
        int c;
        boolean prevIsReadable = false;
        final StringBuilder sb = new StringBuilder();
        while((c = in.read()) != -1) {
            boolean isLetter = Character.isLetter(c);

            if(isLetter || '.' == c || '!' == c || '?' == c) {
                sb.append(Character.toLowerCase((char)c));
                prevIsReadable = true;
            } else if(prevIsReadable) {
                sb.append(' ');
            }
        }
        in.close();

        return new MyString(sb.toString().toCharArray());
    }

}
