package DynamicProgramming;

public class Leetcode_746
{

    class Solution
    {
        public int minCostClimbingStairs( int[] cost )
        {
            return min3(cost);
        /*
        int [] dp = new int[cost.length+1];
        dp[dp.length-1]= 0;
        dp[dp.length-2]=cost[cost.length-1];
        for ( int i = 0 ; i < dp.length-2 ; i++ )dp[i]=-1;
        return Math.min( min2(0,cost,dp), min2(1,cost,dp));
        */
        }

        public int min0( int step  , int [] cost , int sum   )
        {
            // On each cell we can do two things one jump or two jump
            if( step == cost.length-1 ) return sum + cost[step];
            else if( step == cost.length ) return sum+0;
            else return  Math.min( min0( step+1 , cost, sum+cost[step] ), min0( step+2, cost,sum+cost[step]));
        }
   /*
   Time Complexity of these Brut Force Aprroach is (2 + 2 ^2 + ---+ 2 ^N )*2=> One for the starting point 0 and other for the starting point 1
   Space Complexity Recursive Space => O(N) or log(n) where n is the length of the array tHink about it
   for log n i have to write in terms of all the nodes n will be the all nodes in the tree
   */

        /* Now Let's solve it using Top - Down DP Memoization */


        public int min1( int step , int [] cost , int sum ,  int [] dp )
        {
    /*
    We can solve the problem from the n th index also so starting point will start from n-1 and n
    Initially we put the dp[n-1] = cost[n-1]
    and dp[n]= 0

    In worst case for each call a particular subcall is called it can update on each time so time complexity is O(2^N)
    it is o(n) when the condition dp[step]<=sum not true
    */

            if( step == cost.length -1 )return sum + dp [step];
            else if( step == cost.length )return sum + dp[step];
            else if( dp[step]!=-1 && dp[ step ]<=sum) return dp[ step ];
            else return dp[step] = Math.min( min1( step+1 , cost, sum+cost[step] , dp  ), min1( step+2, cost,sum+cost[step] ,dp ) );
    /*
    Time Complexity :
    */
        }

        public int min2( int step , int [] cost , int [] dp )
        {
            /* Top - Down DP */

            if( step == cost.length-1 )return dp[step];
            else if( step == cost.length )return dp[step];
            else if( dp[step]!=-1 ) return dp [step];
            else return dp[step]= cost[ step ]+ Math.min( min2( step+1 , cost, dp ), min2( step+2 , cost, dp ));

/*
 Time Complexity = Each element traverse once = O(N)+ N-2 for filling the values with -1=> O( N )
 Space Compelxity = N+1 { Size of the array }+ Recurse Stack {N} => O(N)
 */

        }
        public int min3( int [] cost )
        {
    /*
    Time Complexity = O(N)
    Space Complexity = O(1)
    */
            int twoStep = 0;
            int oneStep = cost[ cost.length-1 ];
            int temp = 0;
            for ( int i = cost.length-2  ; i >= 0 ; i-- )
            {
                temp = oneStep;
                oneStep = cost[i]+ Math.min( oneStep,twoStep);
                twoStep = temp;
            }
            return Math.min(oneStep,twoStep);
        }


    }

}
