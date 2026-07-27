package BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_131
{
    class Solution {
        public List<List<String>> partition(String s) {
            List<List<String>> result = new ArrayList<>();
            backtrack(s, 0, new ArrayList<>(), result);
            return result;
        }

        private void backtrack(String s, int start, List<String> path, List<List<String>> result) {
            // BASE CASE: consumed entire string
            if (start == s.length()) {
                result.add(new ArrayList<>(path));  // must copy — path is mutated later
                return;
            }

            // TRY EVERY CHOICE at this position
            for (int end = start; end < s.length(); end++) {
                if (isPalindrome(s, start, end)) {
                    path.add(s.substring(start, end + 1));   // TAKE
                    backtrack(s, end + 1, path, result);      // RECURSE
                    path.remove(path.size() - 1);              // UNDO (backtrack)
                }
                // if not a palindrome, we just don't recurse — loop moves to next `end`
            }
        }

        private boolean isPalindrome(String s, int lo, int hi) {
            while (lo < hi) {
                if (s.charAt(lo++) != s.charAt(hi--)) return false;
            }
            return true;
        }
    }
}
