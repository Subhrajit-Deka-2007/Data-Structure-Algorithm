package Intervals;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_57
{


    class Solution {
        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> result = new ArrayList<>();
            int i = 0;
            int n = intervals.length;

            // 1. Add all intervals that end before newInterval starts
            while (i < n && intervals[i][1] < newInterval[0]) {
                result.add(intervals[i]);
                i++;
            }

            // 2. Merge all overlapping intervals
            while (i < n && intervals[i][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
                i++;
            }
            result.add(newInterval);

            // 3. Add remaining intervals that start after newInterval ends
            while (i < n) {
                result.add(intervals[i]);
                i++;
            }

            return result.toArray(new int[result.size()][]);
        }
    }
/*
Time Complexity: O(N) Each interval is processed at most once as the index i increments linearly from 0 to N across the three while loops.Space Complexity: O(N)
Overall Space:O(N) to store the output ArrayList and convert it to the final 2D array (holding up to N + 1 intervals).
Auxiliary Space: O(1) extra space, as only a few scalar variables (i, n) are used beyond the output container.
*/
}
