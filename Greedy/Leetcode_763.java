package Greedy;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_763
{

    class Solution {
        /*
        Step-by-Step Algorithm
        Precompute Last Indices: Perform a first pass through the string to store the last index of every character in a fixed array of size 26.

        Greedy Expansion: Perform a second pass:

        Maintain two pointers: start (beginning of the current partition) and maxReach (furthest index the current partition must cover).

        For each character at index i, update maxReach = Math.max(maxReach, lastIndex[s.charAt(i)]).

        When i == maxReach, you have covered all instances of every character in the current segment. Save the length i - start + 1, and reset start = i + 1.
        */
        public List<Integer> partitionLabels(String s) {
            List<Integer> result = new ArrayList<>();
            int[] lastIndex = new int[26];

            // Step 1: Store the last occurrence index of each character
            for (int i = 0; i < s.length(); i++) {
                lastIndex[s.charAt(i) - 'a'] = i;
            }

            int start = 0;
            int maxReach = 0;

            // Step 2: Traverse and cut partitions greedily
            for (int i = 0; i < s.length(); i++) {
                maxReach = Math.max(maxReach, lastIndex[s.charAt(i) - 'a']);

                // When index reaches maxReach, cut the partition
                if (i == maxReach) {
                    result.add(i - start + 1);
                    start = i + 1; // Start new partition
                }
            }

            return result;
        }
    }
/*
Complexity AnalysisTime Complexity: O(N) Requires two linear passes over string s of length N.
Space Complexity: O(1) auxiliary space
The lastIndex array uses fixed size 26 memory regardless of input string length.
*/


}
