package BitManipulation;

public class Leetcode_268
{
    public int missingNumber0(int[] nums) {
        int sum =0;
        for(int i =0;i<nums.length;i++)sum+=nums[i];
        return (nums.length*(nums.length+1)/2)-sum;
    }
    public int missingNumber(int[] nums) {
        int result = nums.length;   // start with n, since index i only goes up to n-1
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }
}
