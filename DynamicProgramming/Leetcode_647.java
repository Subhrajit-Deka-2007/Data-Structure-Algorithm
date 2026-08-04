package DynamicProgramming;

public class Leetcode_647
{

    class Solution {
        public int countSubstrings(String s) {
    /*
    Using Manacher's Algorithm
    */
            String T = transform(s);
            int n = T.length();
            int[] P = new int[n];
            int C = 0, R = 0;

            for (int i = 0; i < n; i++) {
                int mirror = 2 * C - i;

                if (i < R) {
                    P[i] = Math.min(R - i, P[mirror]);
                }

                while (i - P[i] - 1 >= 0 && i + P[i] + 1 < n
                        && T.charAt(i - P[i] - 1) == T.charAt(i + P[i] + 1)) {
                    P[i]++;
                }

                if (i + P[i] > R) {
                    C = i;
                    R = i + P[i];
                }
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                count += (P[i] + 1) / 2;
            }

            return count;
        }

        private String transform(String s) {
            StringBuilder sb = new StringBuilder("#");
            for (char c : s.toCharArray()) {
                sb.append(c).append('#');
            }
            return sb.toString();
        }
    }
/*
Time Complexity = O(N)
Space Complexity = O(N)
cAN BE SOLVE USING RECURSION , MEMOIZATION AND TABULATION APPROACH ALSO SEE THE LEETCODE 5
*/

}
