package DynamicProgramming;

public class Leetcode_62
{
    class Solution {
        public int uniquePaths(int m, int n)
        {
            // 2D DP as 2 parameters are changing

            // int [][] dp  = new int [m][n];

            // return ways1(0 ,0 , m-1 , n-1,dp );

            return ways3(m,n);
        }
        public int ways0( int sr , int sc ,int er , int ec )
        {
            if( sr == er && sc == ec )return 1;
            else if( sc>ec || sr>er )return 0;
            else return ways0(sr,sc+1,er,ec)+ways0(sr+1,sc,er,ec);
        /*
        Time Complexity O(2^(m+n)),  where m = er-sr, n = ec-sc
          Space = O(m+n)        (max recursion/call-stack depth)
          m--> rows then n---> columns down on each cell two calls
          */
        }
        public int ways1( int sr , int sc ,int er , int ec , int [][] dp )
        {
            if( sr == er && sc == ec )return 1;
            else if( sc>ec || sr>er )return 0;
            else if( dp[sr][sc]!=0 )return dp[sr][sc];
            else return dp[sr][sc]= ways1(sr,sc+1,er,ec,dp)+ways1(sr+1,sc,er,ec,dp);
        /*
        Each cell is called ony once so although calls are made but
        for that particular call again a new subtree is not forrmed
        Time Complexity = O(M*N)
        Space Complexity = O(m*n+(recurse stack (m+n)=> here there is no multiplication )
        */
        }
        // Tabulation
        public int ways2 (int m , int n )
        {
            int [][] dp = new int [m+1][n+1];
            dp[m-1][n-1]= 1;
            for( int i = m-1 ; i >= 0 ; i-- )
                for ( int j = n-1 ; j >= 0 ; j-- )
                {
                    if( dp[i][j]!=0 )continue;
                    dp[i][j] = dp[i][j+1]+dp[i+1][j];
                }

            return dp[0][0];
        /*
        Time Complexity = O(M*N)
        Space Complexity = O(M*N)
        */
        }
        // Method 4 : using Combinotrics

        public int ways3(int m, int n)
        {
            // Total moves N = (m - 1) + (n - 1)
            int N = m + n - 2;

            // Take k as the smaller of (m - 1) or (n - 1) to minimize loop iterations
            int k = Math.min(m - 1, n - 1);

            long res = 1;

            // Compute N C k iteratively: res = (N * (N-1) * ... * (N-k+1)) / (1 * 2 * ... * k)
            for (int i = 1; i <= k; i++) {
                res = res * (N - k + i) / i;
            }

            return (int) res;
        /*
        Time Complexity = O(min(m,n))
        Because    int k = Math.min(m - 1, n - 1); NCk
        Space Complexity = O(1)
        */
        }

        public int ways4(int m, int n) {
            int[] dp = new int[n+1];      // one slot per COLUMN
            dp[n-1] = 1;                   // base case: dp[m-1][n-1] = 1

            for (int i = m-1; i >= 0; i--) {        // outer: sweep ROWS (collapsed dimension)
                for (int j = n-1; j >= 0; j--) {    // inner: sweep COLUMNS (kept-alive dimension)
                    if (i == m-1 && j == n-1) continue;
                    dp[j] = dp[j] + dp[j+1];
                }
            }
            return dp[0];
    /*
    Time Complexity = O(N*M)
    Space Complexity = O(N+1)
    */
        }

        public int ways5(int m, int n) {
            int[] dp = new int[m+1];      // one slot per ROW
            dp[m-1] = 1;                   // base case: dp[m-1][n-1] = 1

            for (int j = n-1; j >= 0; j--) {        // outer: sweep COLUMNS (collapsed dimension)
                for (int i = m-1; i >= 0; i--) {    // inner: sweep ROWS (kept-alive dimension)
                    if (i == m-1 && j == n-1) continue;
                    dp[i] = dp[i] + dp[i+1];
                }
            }
            return dp[0];
    /*
    Time Complexity = O(N*M)
    Space Complexity = O(M)
    */
        }
    }
}
