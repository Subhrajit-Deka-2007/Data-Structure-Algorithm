package DynamicProgramming;

public class Leetcode_416
{

    class Solution {
        public boolean canPartition(int[] nums) {
            return tabulation(nums);
        }
        public  boolean  tabulation(int [] nums){

            int sum =0;
            for(int ele : nums) sum+=ele;
            if(sum%2 !=0) return false;
            /* if the sum of the elements is even then we can partitioned them into two equal subsets*/
            int target = sum/2;
            int [][] dp = new int[nums.length][target+1];// here columns represent target
            for(int i =0;i<dp.length;i++){
                for(int j =0;j<dp[0].length;j++){
                    /*
                     The j represent the target which is going from 0 to target but during algo
                     we only focussed on the index
                     */
                    boolean  ans = false;
                    boolean skip =(i>0)?(dp[i-1][j]==1):(j==0);// i<0 base case hit there also two conditions
                    if(j-nums[i]<0)ans = skip;
                    else{
                        boolean pick =(i>0)?(dp[i-1][j-nums[i]]==1):(j==0);
                        ans = skip||pick;
                    }
                    dp[i][j] = (ans)?1:0;
                }
            }
            return (dp[dp.length-1][target]==1);
        }



        public boolean canPartition1(int[] nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
            if (totalSum % 2 != 0) return false;
            int target = totalSum / 2;
            int n = nums.length;

            int[][] memo = new int[n][target + 1]; // every cell auto-starts at 0 — no fill loop needed!

            return solve1(nums, 0, target, memo);
        }

        private boolean solve1(int[] nums, int i, int target, int[][] memo) {
            if (target == 0) return true;
            if (i == nums.length || target < 0) return false;

            if (memo[i][target] != 0) {
                return memo[i][target] == 1; // 1 → true, 2 → false
            }

            boolean result = solve1(nums, i + 1, target - nums[i], memo)
                    || solve1(nums, i + 1, target, memo);

            memo[i][target] = result ? 1 : 2;
            return result;
    /*
0  =  not computed yet   (Java's natural default for int[][] — no extra setup needed!)
1  =  computed, answer is true
2  =  computed, answer is false
*/
        }


        public boolean canPartition2(int[] nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
            if (totalSum % 2 != 0) return false;
            int target = totalSum / 2;
            int n = nums.length;

            // dp[i][t] = true if using elements from index i to n-1, some subset sums to t
            boolean[][] dp = new boolean[n + 1][target + 1];
            for (int i = 0; i <= n; i++) dp[i][0] = true; // target 0 is always achievable (pick nothing)

            for (int i = n - 1; i >= 0; i--) {          // fill bottom-up: last index first
                for (int t = 1; t <= target; t++) {
                    boolean exclude = dp[i + 1][t];
                    boolean include = (t - nums[i] >= 0) && dp[i + 1][t - nums[i]];
                    dp[i][t] = include || exclude;
                }
            }
            return dp[0][target];
        }


        public boolean canPartition3(int[] nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
            if (totalSum % 2 != 0) return false;

            int target = totalSum / 2;

            // dp[t] = true if some subset of the numbers processed SO FAR sums to exactly t
            boolean[] dp = new boolean[target + 1];
            dp[0] = true; // sum 0 is always achievable — pick nothing

            for (int num : nums) {
                // MUST go right to left, so we read "old" (previous number's) values,
                // not values already updated by this same number in this same pass
                for (int t = target; t >= num; t--) {
                    dp[t] = dp[t] || dp[t - num];
                }
            }

            return dp[target];
        }
/*
Approach	                 Time	        Space	               Notes
Pure recursion	            O(2ⁿ)	       O(n)	              Only call-stack space; exponential due to no caching, full binary tree of depth n
Memoization	             O(n × target)	O(n × target) + O(n)	2D cache table (n × (target+1) cells) + recursion call stack
2D Tabulation	         O(n × target)	O(n × target)	       Full grid stored, no recursion stack
1D Tabulation	         O(n × target)	O(target)	           Only one row kept in memory; reused across all n numbers
*/
    }

}
