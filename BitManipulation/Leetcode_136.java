package BitManipulation;

public class Leetcode_136
{
    class Solution {
        public int singleNumber(int[] nums)
        {
      /*
       I can use the XOR operator as the number which are same there binary representation will also be same
       so everything will be same and xor gives the output 1 only if any two bits of the number is different
       */

            int xor = nums[0];

            for ( int i = 1 ; i<nums.length ; i++ )xor^=nums[i];

            return xor;

       /*
       Time Complexity = O(N)
       Space Complexity = O(1)
       */
        }
    }
}
