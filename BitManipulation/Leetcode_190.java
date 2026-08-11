package BitManipulation;

public class Leetcode_190
{
    class Solution
    {
        public int reverseBits(int n) {
            int result = 0;
            for (int i = 0; i < 32; i++) {
                int bit = n & 1;
                result = (result << 1) | bit;
                n = n >>> 1;
            }
            return result;
        }
/*
Time & space complexity
Time: the loop runs a fixed 32 times regardless of input → O(1) (or O(32) if you want to be pedantic, but since it's a fixed constant unrelated to n's value, it's formally constant)
Space: just the result variable → O(1)
*/
    }
}
