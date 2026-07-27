package BackTracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leetcode_17
{
    class Solution {
        private Map<Character, String> phone = Map.of(
                '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
                '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
        );

        public List<String> letterCombinations(String digits) {
            List<String> result = new ArrayList<>();
            if (digits.isEmpty()) return result;   // edge case
            backtrack(digits, 0, new StringBuilder(), result);
            return result;
        }

        private void backtrack(String digits, int index, StringBuilder path, List<String> result) {
            // BASE CASE: picked a letter for every digit
            if (index == digits.length()) {
                result.add(path.toString());
                return;
            }

            String letters = phone.get(digits.charAt(index));
            for (char c : letters.toCharArray()) {
                path.append(c);                          // TAKE
                backtrack(digits, index + 1, path, result); // RECURSE
                path.deleteCharAt(path.length() - 1);      // UNDO
            }
        }
    }
}
