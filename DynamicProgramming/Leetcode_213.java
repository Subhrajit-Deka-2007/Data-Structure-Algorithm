package DynamicProgramming;

public class Leetcode_213
{

    class Solution
    {
        public int rob(int[] nums)
        {
            if( nums.length == 1 ) return nums[0];
            // int [] dp1 = new int [nums.length-1];
            // int [] dp2 = new int [nums.length];

            //  int i = 0;
            // for (  i = 0 ; i < dp1.length ; i++ )
            // {
            //     dp1[i] = -1;
            //     dp2[i] =-1;
            // }
            // dp2[i]=-1;
            // return Math.max( max1( 0, nums.length-2,nums ,dp1) , max1( 1, nums.length-1, nums,dp2) );
            return max2(nums);
        }
        public int max0( int st , int ed , int [] nums , int sum )
        {
            if( st >ed ) return sum;

            int take = max0( st+2, ed, nums, sum+nums[st] );
            int skip = max0( st+1 , ed , nums, sum);

            return Math.max(take,skip);
        /*
        Time Complexity of Brut Force is
        2 *( 2 + 2 ^1 + 2^2 +---- + 2^n-1)=> 2*2^n-1=> 2^n+1-1 => 2^n
        Space Complexity recusrive stack => O(N-1)
        */
        }


        public int max1( int st , int ed , int [] nums, int [] dp  )
        {
            // Solve Using ToP - Down DP memoization
            if( st > ed )return 0;
            else if( dp[st]!=-1 )return dp[st];
            else return dp[st] = Math.max( nums[st]+ max1(st+2,ed,nums,dp) , max1(st+1,ed,nums,dp));
         /*
          Time Complexity = O(2N) EACH ELEMENT WILL BE VISITED TWICE EXCEPT FIRST AND LAST ELEMENT
            Space Complexity  = o(n+n+n)
         */
        }

        public int max2( int [] nums )
        {
            // Now solve using Bottom Up Dp
            int take  = 0;
            int skip  = nums[nums.length-2];
            int temp = 0;
            for ( int i = nums.length - 3 ; i>= 0 ; i--)
            {
                // First iteration from 0 to n-2
                temp = skip;
                skip = Math.max( nums[i]+take , skip);
                take = temp;
            }

            int firstAns = Math.max( take,skip );

            // Second Iteration from 1 to n-1

            take = 0;
            skip = nums[nums.length-1];
            temp = 0;

            for( int i = nums.length-2 ; i>=1; i-- )
            {
                temp = skip;
                skip = Math.max( nums[i]+take,skip);
                take = temp;
            }

            int secondAns = Math.max(take,skip);

            return Math.max(firstAns,secondAns);
       /*
       Time Complexity = O(N)
       Space Complexity = O(1)
       */
        }
    }

}
