import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vvakar
 *         Date: 1/7/15
 */
public class Solution_StringSimilarity {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String QcountStr = br.readLine().trim();
        int Qcount = Integer.parseInt(QcountStr);

        for (int i = 0; i < Qcount; ++i) {
            String QStr = br.readLine();
            System.out.println(stringSimilarity(QStr.trim()));
        }
    }

    /*



       ababaa
       abaa
                             ""
                           /    \
                       1 a*      b
                       /  \       \
                   2  b   a*       a
\                   /               \
                 3 a                 a*
                  / \
               4 b  a*
                /
             5 a
              /
           6 a*

            1 (*) + 1 (fork) + 3 (fork) + 6 (*)

     */

    static int stringSimilarity(final String str) {
        int count = 0;

        final Node origRoot = getSuffixTree(str);
        Node root = origRoot;

        for(int i = 1; i <= str.length(); ++i) {   // dadbccabcdeccbcdbedaaabbdccdddcbdbebdeca
            char c = str.charAt(i - 1);
            int totalTracks = 0;
            for(Node n : root.children.values()) {
                totalTracks += n.tracks;
            }

            root = root.children.get(c);

            count+= (i * root.wordsEndingHere); // exact sub-words

            int tracks = totalTracks - root.tracks;
            if (tracks > 0) count+= (i * (tracks)); // partial sub-words
        }

        return count;
    }


    private static Node getSuffixTree(String str) {
        Node root = new Node();
        for(int i = 0; i < str.length(); ++i) {
            String sub = str.substring(i, str.length());
            addToTree(root, sub.toCharArray(), 0);
        }

        return root;
    }

    private static void addToTree(Node root, char[] arr, int offset) {
        if(arr.length == offset) return;

        boolean isLastChar = arr.length == offset + 1;

        char c = arr[offset];
        Node node = root.children.get(c);
        if(node == null) {
            node = new Node();
            root.children.put(c, node);
        }

        ++node.tracks;
        if(isLastChar) ++node.wordsEndingHere;

        addToTree(node, arr, offset + 1);


    }

    private static final class Node {
        public int wordsEndingHere, tracks;
        public final Map<Character, Node> children = new HashMap<Character, Node>();
    }

    static int stringSimilarityN2(final String str) {
        int count = 0;


        for (int startCountingAt = str.length() - 1; startCountingAt >= 0; --startCountingAt) {
            for (int i = 0, j = startCountingAt; j < str.length(); ++i, ++j) {
                if (str.charAt(i) != str.charAt(j)) break;
                ++count;
            }
        }

        return count;
    }
}