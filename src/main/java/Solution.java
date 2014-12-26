/**
 * @author vvakar
 *         Date: 12/22/14
 */
public class Solution {
    /*

         1  3  2  7  5  4  9  8


        Min heap:   5  7  8  9
        Max heap:   4  3  2  1




                              A
                           /    \                                 a
                          B      F                         b           c
                      /    \    /                       d      d     e      f
                     D      E  G                    g     h   i j
                      \   /   / \
                      J  H   I  0
                                 \
                                  0
                                   \
                                    0
                                     \
                                     Z

                  printPaths(current, path)
                     if(current is null) return;
                     if(no children) print path + self;
                     else
                        printPaths(left, path)
                        printPaths(right, path)



         would like: A B D

         1. from root:
         2. print self
         3. go left
         4. loop 2 until null
         5. for each visited node, go right
         6.

*/


}
