package DynamicProgramming;

public class Leetcode_518
{
    class Solution
    {
        public int change(int amount, int[] coins)
        {
            return ways3( coins , amount  );
        }
        public int ways0( int [] coins , int  amount, int i  )
        {
            if( amount == 0 ) return 1;
            else if( amount< 0 ) return 0;
            else if( i == coins.length ) return 0;
            else return ways0(coins,amount-coins[i],i)+ways0(coins,amount,i+1);

        /*

         Depth => Amount/min(all the elements of the array )+ length of the array
         T.C = O( 2^( [amount/min all elements ]+n ))
         S.C = O(amount/min( among all the elements )+n)=> Recursive Stack
         */
        }
        public int ways1( int [] coins , int amount , int i , int [][] dp )
        {

            if( amount == 0 ) return 1;
            else if( amount<0 )return 0;
            else if( i == coins.length ) return 0;
            else if( dp[i][amount-1]!=0 ) return dp[i][amount-1];
            else return dp[i][amount-1] =  ways1(coins,amount-coins[i],i,dp)+ ways1(coins,amount,i+1,dp) ;
        /*
        Time Complexity = O( coins.length * amount );
        Space Complexity = O( coins.length * amount )
        */
        }

        public int ways2( int [] coins, int amount  )
        {
            int [][] dp = new int[coins.length][amount];

            int left = 0;
            int right = 0;
            for ( int i = coins.length-1 ; i >=0 ; i-- )
            {
                for ( int amt = 1 ; amt<=amount; amt++ )
                {
                    int newAmt = amt - coins[i];

                    if( newAmt == 0 ) left = 1;
                    else if( newAmt < 0 ) left = 0;
                    else left = dp[i][newAmt-1];   // newAmt-1 >= 0 here, valid index

                    right = i+1==coins.length?0:dp[i+1][amt-1];

                    dp[i][amt-1]= left + right;
                }
            }
            return dp[0][amount-1];
        }


        public int ways3(int[] coins, int amount)
        {
            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1];

            // dp[i][amt] = number of ways to make 'amt' using coins[i..n-1]

            // Base case 1: amount == 0 → exactly 1 way (use no coins), regardless of i
            for (int i = 0; i <= n; i++) dp[i][0] = 1;

            // Base case 2: i == n (no coins left) and amt > 0 → 0 ways
            // (already 0 by default int init, no code needed)

            for (int i = n - 1; i >= 0; i--)
            {
                for (int amt = 1; amt <= amount; amt++)
                {
                    int take   = (amt - coins[i] >= 0) ? dp[i][amt - coins[i]] : 0;
                    int skip   = dp[i + 1][amt];

                    dp[i][amt] = take + skip;
                }
            }

            return dp[0][amount];
        }


        public int ways4(int[] coins, int amount)
        {
            int[] dp = new int[amount + 1];
            dp[0] = 1;   // base case: 1 way to make amount 0

            for (int coin : coins)
            {
                for (int amt = coin; amt <= amount; amt++)
                {
                    dp[amt] = dp[amt]+dp[amt - coin];
                    // dp[amt] (before +=)   = skip (row i+1's value, untouched yet this pass)
                    // dp[amt-coin]          = take (row i's value, already updated this pass since amt-coin < amt)
                }
            }
            return dp[amount];
    /*
    Total ≈ coins.length × (amount + 1) - coins.length × min(coins)
      = coins.length × (amount - min(coins) + 1)

      Space Complexity = O(amount+1)
      */
        }
    }

}
