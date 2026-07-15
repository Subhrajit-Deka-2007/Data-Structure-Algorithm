package Arrays;

public class Leetcode_42 {

    class Solution {
        public int trap(int[] height) {
            /* First find the  maximum arrray  from the left side */
            /* Then find the maximum array from the right side */
        /* Then traverse the both array then for each index fromm both array check
        min(max of array from left , maximum of array from right ) then after finding the
        minimum minus with element from that nums array present in that index
        Important for */
            int[] leftMax = new int[height.length];
            leftMax[0] = height[0];
            for (int i = 1; i < leftMax.length; i++) leftMax[i] = Math.max(leftMax[i - 1], height[i]);


            int[] rightMax = new int[height.length];
            rightMax[rightMax.length - 1] = height[rightMax.length - 1];
            for (int i = rightMax.length - 2; i >= 0; i--) rightMax[i] = Math.max(height[i], rightMax[i + 1]);

            int sum = 0;
            for (int j = 0; j < height.length; j++) {
                sum += Math.min(rightMax[j], leftMax[j]) - (height[j]);
            }
            return sum;
            /**
             T.C =O(N+N)
             S.C =O(2N)
             */
        }

        public int trap2(int[] height) {
            int left = 0;
            int right = height.length - 1;
            int leftMax = 0;
            int rightMax = 0;
            int water = 0;

            while (left < right) {
                if (leftMax <= rightMax) {
                    leftMax = Math.max(leftMax, height[left]);
                    water += leftMax - height[left];
                    left++;
                } else {
                    rightMax = Math.max(rightMax, height[right]);
                    water += rightMax - height[right];
                    right--;
                }
            }
/*
Time Complexity = O (N)
Space Complexity = O (1)
*/
            return water;
        }
    }
}