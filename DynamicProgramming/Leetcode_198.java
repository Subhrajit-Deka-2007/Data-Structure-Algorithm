package DynamicProgramming;

public class Leetcode_198
{
    class Solution {
        public int rob(int[] nums)
        {
            return maxProfit2(nums);
        /*
        int [] dp = new int [nums.length];
        for ( int i = 0 ; i < dp.length ; i++ )dp[i]=-1;
       return maxProfit1(nums,0,dp);
       */

      /*
       return maxProfit0(nums, 0 , 0 );
       */
        }
        public int maxProfit0(int [] nums, int start, int sum  )
        {
            // To memoize these code we need to convert these code in the maxProfit1 format that i have thought
            // as it give us wring answer if we try to do
            // before take skip if( dp[i]!=1) return dp[i] it gives us wring answer it doesn't work think about it
            // think from the leaf node
            if( start >= nums.length )return sum;
            // We have two option take it
            int take = maxProfit0( nums , start + 2 , sum+nums[start]);
            // Or if we skip it
            int skip = maxProfit0( nums , start + 1 , sum);

            return Math.max(take,skip);
        /*
        Time Complexity of these Appraoch is O(2+ 2^2+ 2^3 + --- + 2^n)
        Space Complexity = O(N) for logn i have to put the whole nodes inside the n
        */
        }
        public int maxProfit1( int [] nums , int start , int [] dp )
        {
     /*
     Top - Down DP
     */
            if( start == nums.length-1 ) return nums[start];
            if( start >= nums.length ) return 0;

            else if( dp[start]!= -1) return dp[start];
            // We have two option take it
            int take = nums[start]+maxProfit1( nums , start + 2,dp);

            // Or if we skip it
            int skip = maxProfit1( nums , start + 1 ,dp);


            return dp[start]= Math.max(take,skip);
        }
    /*
    Time Complexity = O(n) Each element of the array will be visited only once
    Space Complexity = Recursive Space + Array => O(n)
    */

        public int maxProfit2( int [] nums )
        {
            int take = 0;
            int skip = 0;
            int temp = 0;
            for ( int i = nums.length - 1 ; i >= 0 ; i-- )
            {
                //    System.out.println( "take "+take+" skip "+skip);
                temp = skip;
                skip = Math.max(nums[i] + take,skip);
                take = temp;
            }
            return Math.max(take,skip);
         /*
         Time Complexity = O(n)
         Space Complexity = O(1)
         */
        }
    }
}
