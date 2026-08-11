package Intervals;
import java.util.Arrays;
public class Leetcode_435
{
    class Solution {
        public int eraseOverlapIntervals(int[][] intervals)
        {

            if (intervals.length == 0) return 0;

            // 1. Sort intervals by their END time
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

            int removeCount = 0;
            int prevEnd = intervals[0][1]; // End time of the first chosen interval

            // 2. Iterate through remaining intervals
            for (int i = 1; i < intervals.length; i++) {
                // Overlap detected: current interval starts before prevEnd
                if (intervals[i][0] < prevEnd) {
                    removeCount++; // Remove current interval (keep the one ending earlier)
                } else {
                    // No overlap: update prevEnd to current interval's end time
                    prevEnd = intervals[i][1];
                }
            }

            return removeCount;
        }
    }
/*
Time Complexity = O(N Log N )
Space Complexity = O(1+Log N )
*/


        public int eraseOverlapIntervals(int[][] intervals) {
            int max = intervals[0][1];
            int min = max;
            for (int i = 1; i < intervals.length; i++) {
                max = Math.max(max, intervals[i][1]);
                min = Math.min(min, intervals[i][1]);
            }
            int shift = 1 - min;
            int[] rightEnds = new int[max - min + 2];
            for (int[] interval : intervals) {
                int left = interval[0] + shift;
                int right = interval[1] + shift;
                if (rightEnds[right] < left) rightEnds[right] = left;
            }
            int count = 0;
            int start = 0;
            for (int i = 1; i < rightEnds.length; i++) {
                if (start <= rightEnds[i]) {
                    count++;
                    start = i;
                }
            }
            return intervals.length - count;
        }
    }

