package Intervals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_56
{
    class Solution {
        public int[][] merge(int[][] interval)
        {
            // First sort the array
            Arrays.sort(interval, (a, b) -> Integer.compare(a[0], b[0]));
            List<int[]> result = new ArrayList<>();


            int [] newInterval = new int[2];
            newInterval[0] = interval[0][0];
            newInterval[1] = interval[0][1];


            for ( int i = 1 ; i < interval.length ; i++ )
            {
                if( interval[i][0] <= newInterval[1] )newInterval[1]= Math.max(interval[i][1],newInterval[1]);
                else
                {
                    result.add(new int[]{newInterval[0],newInterval[1] });
                    newInterval[0] = interval[i][0];
                    newInterval[1] = interval[i][1];
                }
            }

            result.add(new int[]{newInterval[0],newInterval[1]} );
            return result.toArray(new int[result.size()][]);
        }
    }
/*

The overall time complexity of your code is O(N \log N), where N is the number of intervals (interval.length).

Breakdown by Operations

1. Sorting the intervals: Arrays.sort(...)
* Uses Timsort in Java for object arrays (`int[][]`).
* Time:O(N log N — This is the dominant operation.


2. Iterating through intervals: for (int i = 1; i < interval.length; i++)
* Runs N - 1 times, doing constant-time operations O(1) per iteration (comparison, assignment, and adding to the `List`).
* Time: O(N)


3. Converting list to array: result.toArray(...)
* Copies M merged intervals (where M <= N into the final 2D array.
* Time:O(N)




### Total Time & Space Summary

* Time Complexity: O(N log N) + O(N) + O(N) =O(N log N)
* Space Complexity:O(N)
* O(N) space to store the output `ArrayList` and final array result.
* O(N) auxiliary space used internally by Timsort during the sorting step.

*/
}
