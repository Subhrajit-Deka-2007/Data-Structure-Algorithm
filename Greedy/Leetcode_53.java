package Greedy;

public class Leetcode_53
{

    class Solution {
        public int maxSubArray(int[] nums) {
            // Kadane's Algorithm Time complexity = O(N)
            // Why is Kadane's Algorithm "Greedy"?
            int sum =0;
            int ans =0;
            int count =0;
            int neg =Integer.MIN_VALUE;
            for(int i =0;i<nums.length;i++){
                // if(nums[i]<0){
                //     neg=Math.max(neg,nums[i]);
                //     count++;
                // }
                sum= sum+nums[i];
                ans = Math.max(sum,ans);
                if(sum<0){
                    neg=Math.max(neg,nums[i]);
                    count++;
                    sum =0;
                }
            }
            if(count==nums.length) return neg;
            return ans;
        }
    }

    public int maxSubArray1(int[] nums) {
        int maxOverall = Integer.MIN_VALUE;

        // Check maximum subarray ending at every possible index i
        for (int i = 0; i < nums.length; i++) {
            maxOverall = Math.max(maxOverall, maxEndingAt1(nums, i));
        }

        return maxOverall;
    }

    private int maxEndingAt1(int[] nums, int i) {
        // Base Case: Only 1 element available at index 0
        if (i == 0) return nums[0];

        // Either start a new subarray at nums[i], or extend the subarray ending at i - 1
        return Math.max(nums[i], nums[i] + maxEndingAt1(nums, i - 1));
    /*
    Time Complexity: $O(N^2)$Calling maxEndingAt(i) for every index $i$ recalculates overlapping subproblems repeatedly ($1 + 2 + 3 + \dots + N$ function calls).Space Complexity: $O(N)$Maximum call stack depth is $N$
    */
    }

// Memoization Approach

    public int maxSubArray2(int[] nums) {
        int n = nums.length;
        Integer[] memo = new Integer[n]; // null = uncomputed
        int maxOverall = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            maxOverall = Math.max(maxOverall, maxEndingAt2(nums, i, memo));
        }

        return maxOverall;
    }

    private int maxEndingAt2(int[] nums, int i, Integer[] memo) {
        if (i == 0) return nums[0];

        // Return cached result if available
        if (memo[i] != null) return memo[i];

        // Compute and store in memo array
        memo[i] = Math.max(nums[i], nums[i] + maxEndingAt2(nums, i - 1, memo));
        return memo[i];
    }
/*
Time Complexity: O(N)Each state from 0 to $N-1$ is calculated once and cached.Space Complexity: $O(N)$$O(N)$ for the memo array + O(N)for the recursion call stack.
*/

    // Tabulation
    public int maxSubArray3(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        // Base Case
        dp[0] = nums[0];
        int maxOverall = dp[0];

        for (int i = 1; i < n; i++) {
            // Transition: start new subarray at i OR extend dp[i - 1]
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);

            // Track the global maximum across all dp[i]
            maxOverall = Math.max(maxOverall, dp[i]);
        }

        return maxOverall;
    }

}
