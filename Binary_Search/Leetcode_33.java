package Binary_Search;

public class Leetcode_33
{

    class Solution {
        public int search(int[] nums, int target)
        {
        /*
        Brut Force : O (N)
        Space Complexity = O(1)
        Linear Search

        for ( int i = 0 ; i < nums.length ; i++ )
        if( nums[i] == target )return i;

        return -1;
        */


        /*
        Second Optimize Appraoch :
        Binary Search : O(Log N )
        Space Complexity : O(1)
        */

            int left = 0, right = nums.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (nums[mid] == target) {
                    return mid;
                }

                // Determine which half is sorted
                if (nums[left] <= nums[mid]) {
                    // LEFT half is sorted
                    if (nums[left] <= target && target < nums[mid]) {
                        right = mid - 1;  // target is in the sorted left half
                    } else {
                        left = mid + 1;   // target must be in the right half
                    }
                } else {
                    // RIGHT half is sorted
                    if (nums[mid] < target && target <= nums[right]) {
                        left = mid + 1;   // target is in the sorted right half
                    } else {
                        right = mid - 1;  // target must be in the left half
                    }
                }
            }

            return -1; // target not found
        }
    }
}
