package Intervals;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class Leetcode_252 {
    /**
     * Problem Description
     * Given an array of meeting time intervals where intervals[i] = [start_i, end_i], determine if a person could attend all meetings.
     * <p>
     * Examples
     * Example 1:
     * <p>
     * Input: intervals = [[0,30],[5,10],[15,20]]
     * <p>
     * Output: false
     * <p>
     * Explanation: A person cannot attend [0,30] and [5,10] at the same time because they overlap.
     * <p>
     * Example 2:
     * <p>
     * Input: intervals = [[7,10],[2,4]]
     * <p>
     * Output: true
     * <p>
     * Explanation: No overlapping intervals, so the person can attend all meetings.
     * <p>
     * Key Idea & Java Solution
     * To determine if any meetings overlap:
     * <p>
     * Sort intervals by start time (intervals[i][0]).
     * <p>
     * Scan adjacent pairs: If a meeting starts strictly before the previous meeting ends (intervals[i + 1][0] < intervals[i][1]), there is an overlap, so return false.
     */
    public static void main(String[] args) {

    }

    public boolean canJoin(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return true;
        // First sort the array
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));



        int[] newInterval = new int[2];
        newInterval[0] = intervals[0][0];
        newInterval[1] = intervals[0][1];
        int prevEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++){
            // Overlap detected: current interval starts before prevEnd
            if (intervals[i][0] < prevEnd) return false;
            else prevEnd = intervals[i][1];
    }

      return true;
    }
    /*
    Time Complexity = O( N log N )
    Space Complexity = O( 1 + log N )
     */
}
