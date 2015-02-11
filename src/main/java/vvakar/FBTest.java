package vvakar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 2/3/15
 */
public class FBTest {
    /*
Given a string consisting of '(', ')', and alphanumeric characters, remove the
smallest number of parentheses in order to make the parentheses balanced, and
return the resulting string.

For example, given the string "(foo)bar)", you should return either "(foo)bar" or "(foobar)".
Given the string ")foo(" you should return "foo".
Given the string "((foo))", you should return "((foo))" with no change.
     */

    public static void main(String[]args) {
        for(String str : new String[]{"(foo)bar)", ")foo(", "((foo))"}) {
            System.out.println(iterativeSolution(str));
            System.out.println(dpSolution(str));
        }
    }

    /*


        c = (
        - if it is part of the solution, print it
        - if not, skip it


        - how do we know it's part of the solution? because we'll find a matching closing brace later on

        - count all ')' first
        - print
     */
    private static String dpSolution(String str) {
        StringBuilder sb = new StringBuilder();
        List<Integer> opensToBlock = new LinkedList<Integer>();
        int removed = 0;
        char[] arr = str.toCharArray();

        for(int i = 0; i < arr.length; ++i) {
            char c = arr[i];
            if (c == ')') {
                if (!opensToBlock.isEmpty()) {
                    opensToBlock.remove(0);
                    sb.append(c);
                } else ++removed;
            } else if(c == '(') {
                opensToBlock.add(0, i - removed); // prepend to list so we can remove later brackets first; otherwise offsets are off
                sb.append(c);
            } else
                sb.append(c);
        }


        for(int d : opensToBlock)
            sb.deleteCharAt(d);

        return sb.toString();
    }

    private static String iterativeSolution(String str) {
        ArrayList<Integer> blacklistOpen = new ArrayList<Integer>();
        ArrayList<Integer> blacklistClosed = new ArrayList<Integer>();

        char[] arr = str.toCharArray();
        int i = 0, count = 0;
        for(char c : arr) {
            if (c == '(') {
                ++count;
                blacklistOpen.add(i);
            }
            if (c == ')')
                if (count > 0) {
                    --count;
                    blacklistOpen.remove(blacklistOpen.size() - 1);
                }
                else {
                    blacklistClosed.add(i);
                }
            ++i;
        }


        Set<Integer> blacklist = new HashSet<Integer>();
        blacklist.addAll(blacklistOpen);
        blacklist.addAll(blacklistClosed);

        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < arr.length; ++j) {
            if(!blacklist.contains(j)) sb.append(arr[j]);
        }


        return sb.toString();

    }
}
