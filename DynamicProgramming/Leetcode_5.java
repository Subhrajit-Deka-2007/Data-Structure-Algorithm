package DynamicProgramming;

public class Leetcode_5
{



        public String longestPalindrome0(String s) {
            int[] start =  {0};
            int []maxLen = {0};
            if (s == null || s.length() < 1) return "";

            for (int i = 0; i < s.length(); i++) {
                expandFromCenter(s, i, i,start,maxLen);       // odd length
                expandFromCenter(s, i, i + 1,start,maxLen);   // even length
            }

            return s.substring(start[0], start[0] + maxLen[0]);
        }

        private void expandFromCenter(String s, int left, int right, int[] start , int [] maxLen) {
            // base case: stop expanding
            if (left < 0 || right >= s.length() || s.charAt(left) != s.charAt(right)) {
                return;
            }

            // this left/right is currently valid — check if it's the longest so far
            int currentLen = right - left + 1;
            if (currentLen > maxLen[0]) {
                maxLen[0] = currentLen;
                start[0] = left;
            }

            // recurse outward
            expandFromCenter(s, left - 1, right + 1,start,maxLen);
        }
    /*
    Time Complexity: O(n²)

Here's the reasoning, step by step:

Outer loop: In longestPalindrome, you loop through every index i from 0 to n-1, calling expandFromCenter twice (once for odd, once for even). That's 2n calls total to kick things off — O(n).
Each call's recursion depth: For a given center, the recursive calls (left-1, right+1 each time) can go at most O(n) deep in the worst case — e.g., if the entire string is one repeated character like "aaaaaaa", the palindrome could span the whole string, so the recursion expands outward roughly n/2 times before hitting a base case.
Total work: O(n) centers × O(n) expansion depth per center = O(n²).

This matches the iterative while-loop version exactly — recursion here doesn't add or remove complexity, it's the same amount of work, just expressed as function calls instead of a loop.

Space Complexity: O(n)

This is the one thing that's different from the iterative version. Since each recursive call adds a new stack frame, the call stack itself can grow up to O(n) deep (again, worst case being something like "aaaaaaa" where the palindrome spans the whole string). The iterative while loop version uses only O(1) space, since it just reuses left and right variables with no stack growth.

So the recursion trades a bit of space (O(n) stack) for slightly more elegant code — but doesn't change the time complexity at all, still O(n²).
*/



            private Boolean[][] memo;
            private String s;
            private int start = 0, maxLen = 1;

            public String longestPalindrome1(String s) {
                this.s = s;
                int n = s.length();
                memo = new Boolean[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = i; j < n; j++) {
                        if (isPalindrome(i, j) && (j - i + 1) > maxLen) {
                            maxLen = j - i + 1;
                            start = i;
                        }
                    }
                }

                return s.substring(start, start + maxLen);
            }

            private boolean isPalindrome(int i, int j) {
                if (i >= j) return true; // base case: 0 or 1 character, always a palindrome

                if (memo[i][j] != null) return memo[i][j]; // already computed — reuse it

                boolean result = (s.charAt(i) == s.charAt(j)) && isPalindrome(i + 1, j - 1);
                memo[i][j] = result; // store before returning
                return result;
            }

/*
Time Complexity = O(N^2)
Space Complexity = O(N^2)
*/



            public String longestPalindrome2(String s) {
                int n = s.length();
                if (n < 1) return "";

                boolean[][] dp = new boolean[n][n];
                int start = 0, maxLen = 1;

                // base case: every single character is a palindrome
                for (int i = 0; i < n; i++) {
                    dp[i][i] = true;
                }

                // fill table by increasing length
                for (int len = 2; len <= n; len++) {
                    for (int i = 0; i <= n - len; i++) {
                        int j = i + len - 1; // end index, derived from start + length

                        if (len == 2) {
                            dp[i][j] = (s.charAt(i) == s.charAt(j));
                        } else {
                            dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
                        }

                        if (dp[i][j] && len > maxLen) {
                            maxLen = len;
                            start = i;
                        }
                    }
                }

                return s.substring(start, start + maxLen);
            }
        }

/*
Time Complexity = O(N^2)
Space Complexity = O(N^2)
*/


