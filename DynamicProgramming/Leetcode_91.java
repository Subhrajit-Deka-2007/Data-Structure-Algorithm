package DynamicProgramming;
import java.util.Arrays;
public class Leetcode_91
{

    class Solution {
        public int numDecodings0(String s) {
            return helper(s, 0);
        }

        private int helper(String s, int i) {
            // Reached the end successfully — this is one valid decoding
            if (i == s.length()) {
                return 1;
            }

            // Leading zero at this position — no valid decoding possible from here
            if (s.charAt(i) == '0') {
                return 0;
            }

            // Option 1: take a single digit
            int ways = helper(s, i + 1);

            // Option 2: take two digits, if valid (10 to 26) and in bounds
            if (i + 1 < s.length()) {
                int twoDigit = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
                if (twoDigit >= 10 && twoDigit <= 26) {
                    ways += helper(s, i + 2);
                }
            }

            return ways;
        }
    /*
    Time Complexity = O(2^N)
    Space Complexity = O(N)
    */



        public int numDecodings1(String s) {
            // Memoization
            int[] memo = new int[s.length()];
            Arrays.fill(memo, -1); // -1 means "not computed yet"
            return helper(s, 0, memo);
        }

        private int helper(String s, int i, int[] memo) {
            // Reached the end successfully — one valid decoding
            if (i == s.length()) {
                return 1;
            }

            // Leading zero — dead branch
            if (s.charAt(i) == '0') {
                return 0;
            }

            // Already solved this subproblem — return cached result
            if (memo[i] != -1) {
                return memo[i];
            }

            // Option 1: take a single digit
            int ways = helper(s, i + 1, memo);

            // Option 2: take two digits, if valid (10 to 26) and in bounds
            if (i + 1 < s.length()) {
                int twoDigit = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
                if (twoDigit >= 10 && twoDigit <= 26) {
                    ways += helper(s, i + 2, memo);
                }
            }

            // Cache before returning
            memo[i] = ways;
            return ways;
        /*
        Time Complexity = O(n)
        Space Complexity = O(n)
        */
        }


        public int numDecodings2(String s) {
            // Tabulation
            int n = s.length();
            int[] dp = new int[n + 1];
            dp[0] = 1; // empty prefix — 1 way (do nothing)

            for (int i = 1; i <= n; i++) {
                // Option 1: take s[i-1] as a single digit
                // each cell in dp array says number of ways to decode firsth ith strings in terms of ondex 0 to i-1
                if (s.charAt(i - 1) != '0') {
                    dp[i] += dp[i - 1];
                }

                // Option 2: take s[i-2..i-1] as a two-digit letter
                // i-2 because we are taking two digits
                if (i >= 2) {
                    int twoDigit = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
                    if (twoDigit >= 10 && twoDigit <= 26) {
                        dp[i] += dp[i - 2];
                    }
                }
            }

            return dp[n];
        }
    }


}
