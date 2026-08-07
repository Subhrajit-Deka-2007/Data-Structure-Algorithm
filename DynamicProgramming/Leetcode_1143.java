package DynamicProgramming;

public class Leetcode_1143
{
    class Solution {
        public int longestCommonSubsequence(String text1, String text2)
        {
            return lcs_tabulation(text1,text2);
            //    return lcs(text1,text2,0,0);
            //  int[][] dp = new int[text1.length()][text2.length()];
            //  return  lcs_memo(  text1 , text2 ,  0,  0 , dp );
        }

        int lcs(String a, String b, int i, int j)
        {
            if (i == a.length() || j == b.length()) return 0;
            if (a.charAt(i) == b.charAt(j)) {
                return 1 + lcs(a, b, i + 1, j + 1);
            } else {
                return Math.max(lcs(a, b, i + 1, j), lcs(a, b, i, j + 1));
        /*
        Time Complexity = 2^(m+n) as depth is m+n in worst no match first we will go n levels depth then start going
        m levels depth where m is the length of the first string and n is the length of the second String

        Space Complexity = Recursive Stack O(M+N)
        */
            }
        }

        int lcs_memo( String a , String b , int i , int j , int [][] dp )
        {
            if( i == a.length() || j == b.length() ) return 0;
            else if( dp[i][j]!=0 )return dp[i][j];
            else if (a.charAt(i) == b.charAt(j))return dp[i][j] =1+ lcs_memo(a, b, i + 1, j + 1,dp);
            else return  dp[i][j]=Math.max(lcs_memo(a, b, i + 1, j,dp ), lcs_memo(a, b, i, j + 1,dp));
        /*
        Time Complexity = O(M*N)
        Space Complexity = o(m*n +(M+N)=> recursive space )
        */
        }

        public int lcs_tabulation( String a , String b  )
        {
            int [][] dp = new int[a.length()+1][b.length()+1];

            for ( int i = a.length()-1; i>= 0 ; i-- )
            {
                for ( int j = b.length()-1 ; j>=0 ; j--)
                {
                    if( a.charAt(i) == b.charAt(j) )dp[i][j] = 1 + dp[i+1][j+1];
                    else dp[i][j] = Math.max( dp[i+1][j], dp[i][j+1]);
                }
            }
            return dp[0][0];
/*
Time Complexity = O(M*N)
Space Complexity = O(M*N)
*/
        }


        public int lcs_tabulation_1D(String a, String b) {
            int n = b.length();
            int[] dp = new int[n + 1];  // dp[j] represents current "row"; starts as row i = a.length() (all 0s = base case)

            for (int i = a.length() - 1; i >= 0; i--) {
                int nextDiag = 0;  // dp[i+1][n] is always 0 (base case column), so this starts at 0 each row

                for (int j = b.length() - 1; j >= 0; j--) {
                    int temp = dp[j];  // save dp[i+1][j] BEFORE overwriting — becomes next diagonal

                    if (a.charAt(i) == b.charAt(j)) {
                        dp[j] = 1 + nextDiag;                 // nextDiag = dp[i+1][j+1]
                    } else {
                        dp[j] = Math.max(dp[j], dp[j + 1]);   // dp[j] still old (dp[i+1][j]); dp[j+1] already new (dp[i][j+1])
                    }

                    nextDiag = temp;  // move diagonal pointer for the next (smaller) j
            /*
            we are doing  prevDig = temp because on moving forward for the next iteration it will become the diagonal element am i right
            */
                }
            }
            return dp[0];
        }
        public int lcs_tabulation_1D_2(String text1, String text2) {
            int m = text1.length(), n = text2.length();
            int[] dp = new int[n + 1];

            for (int i = 1; i <= m; i++) {
                int prevDiag = 0;   // ← this is what we're examining
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j];

                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[j] = 1 + prevDiag;
                    } else {
                        dp[j] = Math.max(dp[j], dp[j - 1]);
                    }

                    prevDiag = temp;
                }
            }
            return dp[n];
        }

/*
j=0(z) j=1(y) j=2(end)
i=0(x)    1      1      0
i=1(z)    1      1      0
i=2(y)    0      1      0
i=3(end)  0      0      0
*/
    }
}
