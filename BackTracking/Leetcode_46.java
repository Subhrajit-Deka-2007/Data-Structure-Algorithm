package BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_46 {
    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<Integer> ls = new ArrayList<>();
            List<List<Integer>> ans = new ArrayList<>();
            permutation(nums, ls, ans);
            return ans;
        }

        public void permutation(int[] nums, List<Integer> ls, List<List<Integer>> ans) {
            if (ls.size() == nums.length) {
                List<Integer> s = new ArrayList<>(ls);
                ans.add(s);
                return;
            }
            int temp = 0;
            for (int i = 0; i < nums.length; i++) {

                if (nums[i] != 11) {
                    ls.add(nums[i]);
                    temp = nums[i];
                    nums[i] = 11;
                    permutation(nums, ls, ans);
                    nums[i] = temp;
                    ls.remove(ls.size() - 1);
                }

            }
        }
    }
}


