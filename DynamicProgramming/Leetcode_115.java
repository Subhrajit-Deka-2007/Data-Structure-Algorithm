package DynamicProgramming;

public class Leetcode_115
{

    class Solution
    {
        public int numDistinct0(String s, String t) {
            return helper0(s, t, 0, 0);
        }

        private int helper0(String s, String t, int i, int j) {
            if (j == t.length()) return 1;      // matched all of t
            if (i == s.length()) return 0;      // ran out of s

            int ways = 0;
            if (s.charAt(i) == t.charAt(j)) {
                ways += helper0(s, t, i + 1, j + 1); // use s[i]
            }
            ways += helper0(s, t, i + 1, j);         // skip s[i]
            return ways;
        }



        public int numDistinct1(String s, String t) {
            Integer[][] memo = new Integer[s.length()][t.length()];
            return helper1(s, t, 0, 0, memo);
        }

        private int helper1(String s, String t, int i, int j, Integer[][] memo) {
            if (j == t.length()) return 1;
            if (i == s.length()) return 0;
            if (memo[i][j] != null) return memo[i][j];

            int ways = 0;
            if (s.charAt(i) == t.charAt(j)) {
                ways += helper1(s, t, i + 1, j + 1, memo);
            }
            ways += helper1(s, t, i + 1, j, memo);

            return memo[i][j] = ways;
        }



        public int numDistinct2(String s, String t) {
            int m = s.length(), n = t.length();
            int[][] dp = new int[m + 1][n + 1];

            // base case: dp[i][n] = 1 for all i (empty t always matched)
            for (int i = 0; i <= m; i++) dp[i][n] = 1;
            // dp[m][j] = 0 for j < n is already the default (0), so no need to set

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    dp[i][j] = dp[i + 1][j]; // skip s[i]
                    if (s.charAt(i) == t.charAt(j)) {
                        dp[i][j] += dp[i + 1][j + 1]; // use s[i]
                    }
                }
            }

            return dp[0][0];
        }



        public int numDistinct3(String s, String t) {
            int m = s.length(), n = t.length();
            int[] dp = new int[n + 1];
            dp[n] = 1; // base case: empty t

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (s.charAt(i) == t.charAt(j)) {
                        dp[j] += dp[j + 1];
                    }
                    // if chars don't match, dp[j] stays the same (= dp[i+1][j] from before this row's update)
                }
            }

            return dp[0];
        }
    }
/**

 Fair catch — I only gave complexity for the final optimized version. Here's the full breakdown for each stage:

 ## Step 2: Brute Force Recursion
 - **Time:** O(2^m) — at each index `i`, you branch into up to 2 calls (skip, or skip+use), so the recursion tree can double in the worst case (when most characters match).
 - **Space:** O(m) — recursion stack depth, since `i` increases by 1 each call and maxes out at `s.length()`.

 ## Step 3: Memoization
 - **Time:** O(m·n) — there are `m × n` distinct `(i, j)` states, and each is computed once (O(1) work per state after subproblems are solved).
 - **Space:** O(m·n) for the `memo` table + O(m) for the recursion call stack → **O(m·n)** overall (the table dominates).

 ## Step 4: Tabulation
 - **Time:** O(m·n) — two nested loops over `i` and `j`, O(1) work each.
 - **Space:** O(m·n) — the full 2D `dp` array. No recursion stack this time since it's iterative.

 ## Step 5: 1D Space-Optimized
 - **Time:** O(m·n) — same amount of work, just restructured.
 - **Space:** O(n) — only one row is kept alive at a time.

 **Quick pattern to notice:** time complexity stays O(m·n) from memoization onward (that's the true "amount of work" the problem needs — you can't beat visiting each `(i,j)` state at least once). What changes across steps is *space*, as you strip away redundant storage: recursion stack → full table → single row.

 */

}
