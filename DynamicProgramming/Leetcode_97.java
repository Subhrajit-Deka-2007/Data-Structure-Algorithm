package DynamicProgramming;

public class Leetcode_97
{

    class Solution {

        public boolean isInterleave(String s1, String s2, String s3) {
            if (s1.length() + s2.length() != s3.length()) return false;
            // return solve1(s1, s2, s3, 0, 0, new boolean[s1.length()+1][s2.length()+1]);
            return solve2(s1,s2,s3);
        }

        private boolean solve(String s1, String s2, String s3, int i, int j)
        {
            // Base case: consumed all of s1 and s2 — check we also consumed all of s3
            if (i == s1.length() && j == s2.length()) {
                return true; // since lengths matched upfront, i+j == s3.length() here too
            }

            int k = i + j; // current position in s3 we're trying to match

            boolean takeFromS1 = false;
            if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
                takeFromS1 = solve(s1, s2, s3, i + 1, j);
            }

            boolean takeFromS2 = false;
            if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
                takeFromS2 = solve(s1, s2, s3, i, j + 1);
            }

            return takeFromS1 || takeFromS2;
        }
/*
Time Complexity = O(2^(M+N))
Space Complexity = o(m+n)=> recursive space
*/



        private boolean solve1( String s1 , String s2 , String s3 , int i , int j , boolean [][] dp )
        {
            // Base case: consumed all of s1 and s2 — check we also consumed all of s3
            if (i == s1.length() && j == s2.length()) {
                return true; // since lengths matched upfront, i+j == s3.length() here too
            }
            else if(dp[i][j]) return dp[i][j];

            int k = i + j; // current position in s3 we're trying to match

            boolean takeFromS1 = false;
            if (i < s1.length() && s1.charAt(i) == s3.charAt(k))  takeFromS1 = solve1(s1, s2, s3, i + 1, j,dp);


            boolean takeFromS2 = false;
            if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) takeFromS2 = solve1(s1, s2, s3, i, j + 1,dp);


            return dp[i][j] =  takeFromS1 || takeFromS2;
    /*
    Time Complexity = O(N*M)
    Space Complexity = O(N*M +(n+m))
    */
        }


// Tabulation Method


        private boolean solve2( String s1, String s2 , String s3 )
        {

            boolean [][] dp = new boolean[s1.length()+1][s2.length()+1];

            dp[s1.length()][s2.length()] = true;
            int k = 0;
            boolean takeFromS1 ;
            boolean takeFromS2;

            for ( int i = s1.length() ; i >= 0 ; i-- )
            {
                for ( int j = s2.length() ; j>=0 ; j-- )
                {
                    if( i == s1.length() && j == s2.length() )continue;
                    k = i+j;
                    takeFromS1 = false;
                    if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) takeFromS1 = dp[ i + 1][j];
                    takeFromS2 = false;
                    if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) takeFromS2 = dp[i][j + 1];
                    dp[i][j] =  takeFromS1 || takeFromS2;
                }
            }
            return dp[0][0];

        }





            public boolean isInterleave3(String s1, String s2, String s3) {
                char[] c1 = s1.toCharArray(), c2 = s2.toCharArray(), c3 = s3.toCharArray();
                int m = s1.length(), n = s2.length();
                if(m + n != s3.length()) return false;
                return dfs(c1, c2, c3, 0, 0, 0, new boolean[m + 1][n + 1]);
            }

            public boolean dfs(char[] c1, char[] c2, char[] c3, int i, int j, int k, boolean[][] invalid) {
                if(invalid[i][j]) return false;
                if(k == c3.length) return true;
                boolean valid =
                        i < c1.length && c1[i] == c3[k] && dfs(c1, c2, c3, i + 1, j, k + 1, invalid) ||
                                j < c2.length && c2[j] == c3[k] && dfs(c1, c2, c3, i, j + 1, k + 1, invalid);
                if(!valid) invalid[i][j] = true;
                return valid;
            }
        }
    }

