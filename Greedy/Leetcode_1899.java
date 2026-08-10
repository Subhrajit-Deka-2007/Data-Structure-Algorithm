package Greedy;

public class Leetcode_1899
{

    class Solution
    {
        /*
        Core IntuitionThe operation takes the element-wise maximum of two triplets:$$\text{result} = [Max(a1, a2), Max(b1, b2), Max(c1, c2)]$$Because the max operation can never decrease a value, any triplet $[a, b, c]$ that has any single element strictly greater than its target counterpart (a > x, or b > y, or c > z) is invalid and can never be used. Including it would permanently overshoot the target in that coordinate.Once you filter out all invalid triplets, you can safely combine all valid triplets. We simply need to check if, among the valid triplets:
        At least one has a = x
        At least one has b = y
        At least one has c = z

        If all three requirements are met, taking the element-wise max of all valid triplets will reconstruct [x, y, z].
        */
        public boolean mergeTriplets(int[][] triplets, int[] target)
        {
            boolean foundA = false;
            boolean foundB = false;
            boolean foundC = false;

            for (int[] t : triplets) {
                // Step 1: Skip any triplet that overshoots target in ANY position
                if (t[0] > target[0] || t[1] > target[1] || t[2] > target[2]) {
                    continue;
                }

                // Step 2: Check if this valid triplet contributes to matching any target element
                if (t[0] == target[0]) foundA = true;
                if (t[1] == target[1]) foundB = true;
                if (t[2] == target[2]) foundC = true;

                // Early Exit: All 3 elements found
                if (foundA && foundB && foundC) {
                    return true;
                }
            }

            return foundA && foundB && foundC;
        }
/*
Time Complexity = O(n)
Space Complexity = O(1)
*/
    }

}
