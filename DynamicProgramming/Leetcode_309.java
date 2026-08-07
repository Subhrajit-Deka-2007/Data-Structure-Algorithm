package DynamicProgramming;

public class Leetcode_309
{
    class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            if (n == 0) return 0;

            // Day 0 base case
            int hold = -prices[0];
            int sold = 0;
            int rest = 0;

            for (int i = 1; i < n; i++) {
                int prevHold = hold;
                int prevSold = sold;
                int prevRest = rest;

                hold = Math.max(prevHold, prevRest - prices[i]);
                sold = prevHold + prices[i];
                rest = Math.max(prevRest, prevSold);
            }

            return Math.max(sold, rest);
        }
    }
}
