package Binary_Search;

public class Leetcode_4
{

    class Solution {
    /*
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int [] ans = new int [ nums1.length+nums2.length];
        int i =0;
        int  j =0;
        int k =0;
        while(i<nums1.length&&j<nums2.length){
            if(nums1[i]<nums2[j]){
                ans[k++]=nums1[i++];
            }
            else ans[k++]=nums2[j++];

        }
        //System.out.println(i+" "+j);
        while(i<nums1.length)ans[k++]=nums1[i++];
        while(j<nums2.length)ans[k++]=nums2[j++];

        int n = ans.length/2;

        return (ans.length %2==0)?(double)(ans[n]+ans[n-1])/2:ans[n];

        /*
        Time Complexity = O ( M + N )
        Space Complexity = O ( M + N )

    }
    */

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            // Step 1: Always binary search over the SMALLER array
            if (nums1.length > nums2.length) {
                return findMedianSortedArrays(nums2, nums1);
            }

            int m = nums1.length; // smaller array
            int n = nums2.length; // bigger array
            int totalLeft = (m + n + 1) / 2; // how many elements MUST be on the left, total

            int left = 0, right = m; // binary search range: "how many from nums1"

            while (left <= right) {
                int cut1 = left + (right - left) / 2; // GUESS: how many from nums1 go left
                int cut2 = totalLeft - cut1;             // AUTOMATIC: how many from nums2 go left

                // Step 2: find the 4 boundary values
                int leftNums1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
                int rightNums1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];

                int leftNums2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
                int rightNums2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];

                // Step 3: check validity
                if (leftNums1 <= rightNums2 && leftNums2 <= rightNums1)
                {
                    // Step 4: extract the median
                    if ((m + n) % 2 == 0) {
                        return (Math.max(leftNums1, leftNums2) + Math.min(rightNums1, rightNums2)) / 2.0;
                    } else {
                        return Math.max(leftNums1, leftNums2);
                    }
                } else if (leftNums1 > rightNums2) {
                    right = cut1 - 1; // took too many from nums1 — try fewer
                } else {
                    left = cut1 + 1; // took too few from nums1 — try more
                }
            }

            return -1; // shouldn't happen with valid input
        }
    /*
    Time Complexity = O ( log ( min (m,n))
    Space Complexity = O (1)
    */
    }
}
