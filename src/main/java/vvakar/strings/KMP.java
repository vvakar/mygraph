package vvakar.strings;

/**
 * @author vvakar
 *         Date: 12/30/14
 */
public class KMP {

    public static int findAsciiCaseInsensitive(final String str, final String substring) {
        if ("".equals(substring)) return 0;
        if ("".equals(str)) return -1;


        return find(str.toLowerCase(), substring.toLowerCase());
    }

    private static int find(final String str, final String substring) {
        final int[][] dfa = buildAsciiDFA(substring);

        int currentState = 0;
        for(int i = 0; i < str.length(); ++i ) {
            final char input = str.charAt(i);
            currentState = dfa[currentState][input - 'a'];
            if(currentState == dfa.length) {
                return i - (dfa.length - 1);
            }
        }
        return -1;
    }

    /**
     * 26 letters
     * <p/>
     * 0  1   2   3   4   5(end)
     * <p/>
     * expected =   A  B   A   B   C
     * A    1  0   3   1   3
     * B    0  2   0   4   0
     * C    0  0   0   0   5
     * ...
     * <p/>
     * <p/>
     * pattern = ABABC
     * input  =  CABABABDABababcD
     */
    private static int[][] buildAsciiDFA(final String str) {
        final char[] chars = str.toCharArray();
        final int[][] dfa = new int[chars.length][26];

        for (int state = 0; state < chars.length; ++state) {
            for (char c = 'a'; c <= 'z'; ++c) {
                int nextState = 0;
                if (chars[state] == c) {
                    nextState = state + 1;
                } else {
                    /*
                    not on track, but could we be part of a shorter pattern?
                    in other words, is there a shorter starting pattern that leads up to the current character through
                    a subset of the same path that we arrived here?

                    repeat until we hit beginning of the pattern:
                    1. find previous occurrence of current character
                          a. and the char before it - compare with the one before this one
                          b. and the char before that one
                          c. ... until we reach the beginning of the pattern, or we find a mismatch
                     */

                    int currentPos = state - 1;
                    int prevOccurrence = -1;
                    while (currentPos >= 0) {

                        final int backtrackingCandidateIndex = state - (prevOccurrence - currentPos);
                        if (prevOccurrence == -1 && chars[currentPos] == c) {
                            // we don't have a previous occurrence  - starting the backtracking
                            prevOccurrence = currentPos;
                        } else if (prevOccurrence != -1 && chars[currentPos] != chars[backtrackingCandidateIndex]) {
                            // pattern: ABABC
//                                      01234
                            // input -> ABABAB
                            //          012345
                            // state = 4
                            // prevOccurrence = 2
                            // currentPos = 1
                            // we're in the middle of backtracking and wonder if char[currentPos] == chars[state - (prevOccurrence - currentPos)]

                            // didn't match  - reset
                            prevOccurrence = -1;
                        }
                        --currentPos;
                    }

                    if(prevOccurrence >= 0) {
                        nextState = prevOccurrence + 1;
                    }
                }

                dfa[state][c - 'a'] = nextState;
            }
        }

        return dfa;

    }
}
