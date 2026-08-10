package Greedy;

public class LeetCode_45
{

    class Solution
    {
        public int jump(int[] nums) {
            if (nums.length <= 1) return 0;

            int jumps = 0;
            int currentEnd = 0;
            int farthest = 0;

            // We stop at nums.length - 2 because once we reach or cross
            // nums.length - 1 inside the loop, we are already done.
            for (int i = 0; i < nums.length - 1; i++) {
                farthest = Math.max(farthest, i + nums[i]);

                // When we reach the boundary of the current jump,
                // we must make the next jump.
                if (i == currentEnd) {
                    jumps++;
                    currentEnd = farthest;

                    // Early exit if we can already reach or pass the last index
                    if (currentEnd >= nums.length - 1) {
                        break;
                    }
                }
            }

            return jumps;
        }
/*
Time Complexity = O(n)
Space Complexity = O(1)
*/
    }
}
