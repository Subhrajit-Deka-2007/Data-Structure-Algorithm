package DynamicProgramming;

public class Leetcode_416
{

    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) totalSum += num;
        if (totalSum % 2 != 0) return false; // odd sum can never split evenly
        int target = totalSum / 2;
        return solve(nums, 0, target);
    }

    private boolean solve(int[] nums, int i, int target) {
        if (target == 0) return true;
        if (i == nums.length || target < 0) return false;
        return solve(nums, i + 1, target - nums[i])   // include
                || solve(nums, i + 1, target);            // exclude
    }





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
/**
 *  /*
 *     Yes — here’s the **memory-friendly version**:
 *
 * When we process a number `num`, we are trying to form new sums by doing:
 *
 * ```text
 * old_sum + num = new_sum
 * ```
 *
 * So for each `new_sum`, the previous sum must be:
 *
 * ```text
 * old_sum = new_sum - num
 * ```
 *
 * That is why the loop only goes down to `num`: if `new_sum < num`, then `new_sum - num` becomes negative, and a negative previous sum is impossible.
 *
 * We go from `target` down to `num` because we must use the **old** `dp` values, not the ones just updated by the same number. If we go upward, the current number can accidentally help create a sum and then be used again immediately, which would mean using the same element twice.
 *
 * ## Easy way to remember
 *
 * - **Lower bound = `num`** because smaller sums cannot include `num`.
 * - **Go backward** because you want each number counted **once**, not reused in the same loop.
 *
 * ## Short example
 *
 * If `num = 5`:
 *
 * - `dp[5]` comes from `dp[0] + 5`
 * - `dp[10]` would come from `dp[5] + 5`
 *
 * If you go upward, `dp[5]` gets set first, and then `dp[10]` wrongly uses that same newly set `dp[5]`.
 * If you go backward, `dp[10]` checks the old `dp[5]` before `dp[5]` is updated, so reuse does not happen.
 *
 * If you want, I can turn this into a 2-line interview answer you can memorize.
 *
 *
 *
 * Each cell `dp[t]` means: **can we make sum `t` using some of the numbers we have processed so far?**
 *
 * ## Meaning
 *
 * - `dp[0] = true` because sum `0` is always possible by choosing nothing.
 * - `dp[5] = true` means some subset of the numbers seen so far adds up to `5`.
 * - `dp[11] = true` means some subset adds up to `11`.
 *
 * ## In one sentence
 *
 * `dp[t]` is a yes/no answer for whether sum `t` is currently reachable.
 *
 * ## Example
 *
 * If we have processed `[1, 5]`, then:
 *
 * - `dp[1] = true` means we can make `1`
 * - `dp[5] = true` means we can make `5`
 * - `dp[6] = true` means we can make `6` using `1 + 5`
 *
 * So the array is just tracking which sums are possible at each step.
 *
 * If you want, I can now combine everything into one final simple explanation of the whole algorithm.
 * 
  */
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
