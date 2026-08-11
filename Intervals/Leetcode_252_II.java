package Intervals;
import java.util.Arrays;
import java.util.PriorityQueue;


/**
  LeetCode 253: Meeting Rooms II
 Difficulty:Medium

  Problem Description

 Given an array of meeting time intervals `intervals` where `intervals[i] = [start_i, end_i]`,
 return the **minimum number of conference rooms required.



  Examples

 Example 1:
 Input:intervals = [[0,30],[5,10],[15,20]]`
 Output: `2`
 Explanation:
 * * Room 1: `[0, 30]`
 * * Room 2: `[5, 10]`, `[15, 20]` (Room 2 becomes free at `10`, so `[15, 20]` can reuse it).
 * * Minimum rooms needed = **2**.
 *
 *
 *
 * **Example 2:**
 *
 * * **Input:** `intervals = [[7,10],[2,4]]`
 * * **Output:** `1`
 * * **Explanation:** The meetings do not overlap, so **1** room is enough.
 *
 * ---
 *
 * #### Constraints
 *
 * * $1 \le \text{intervals.length} \le 10^4$
 * * $0 \le \text{start}_i < \text{end}_i \le 10^6$
 */
public class Leetcode_252_II
{
    public static void main(String[] args)
    {

    }
/**
    To solve **LeetCode 253: Meeting Rooms II** correctly, you need to track the end times of **all currently active rooms** simultaneously.

    There are **two standard ways** to solve this:

        ---

        ### Strategy 1: Min-Heap (PriorityQueue) — *Most Intuitive*

        #### The Core Idea

1. **Sort the meetings by START time.**
        2. Use a **Min-Heap** (`PriorityQueue`) to track the **END times** of meetings currently using a room. The top of the heap will always be the room that finishes *first*.
        3. For each meeting:
        * Look at the heap's top (earliest ending room).
        * **If meeting start time $\ge$ heap top:** That room is now free! Remove it from the heap (`poll()`) because we are reusing it.
* **If meeting start time $<$ heap top:** All occupied rooms are still busy. We must allocate a new room.
* Push the current meeting's end time onto the heap.


        4. The size of the heap at the end is the total number of rooms required.

#### Java Implementation

*/


    public class Solution {
        public int minMeetingRooms(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return 0;

            // 1. Sort meetings by START time
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

            // 2. Min-Heap stores the END times of active meetings
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();

            // Add the end time of the first meeting
            minHeap.add(intervals[0][1]);

            for (int i = 1; i < intervals.length; i++) {
                // Check if the room that frees up earliest is available
                if (intervals[i][0] >= minHeap.peek()) {
                    minHeap.poll(); // Reuse this room!
                }

                // Allocate room (either reused with updated end time, or new room added)
                minHeap.add(intervals[i][1]);
            }

            // The number of active rooms needed is the size of the heap
            return minHeap.size();
        }
    }

/**
        * **Time Complexity:** $\mathcal{O}(N \log N)$ (sorting takes $\mathcal{O}(N \log N)$, and heap operations take $\mathcal{O}(N \log N)$).
        * **Space Complexity:** $\mathcal{O}(N)$ for storing up to $N$ end times in the heap.

---

        ### Strategy 2: Two Pointers (Chronological Ordering) — *Fastest*

        #### The Core Idea

    Think of this like people entering and exiting a building:

        * Every time a meeting **starts**, we need a room (`rooms++`).
        * Every time a meeting **ends**, a room becomes free (`endPointer++`).

        1. Extract all **start times** into one array and all **end times** into another array.
2. Sort both arrays in ascending order independently.
3. Use two pointers (`startPointer` and `endPointer`):
        * If `starts[startPointer] < ends[endPointer]`: A new meeting starts before the earliest meeting finishes $\rightarrow$ Increment `rooms++` and advance `startPointer`.
        * Otherwise: A meeting has finished $\rightarrow$ Advance `endPointer` (reusing the room).



        #### Java Implementation

*/


    public class Solution3 {
        public int minMeetingRooms(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return 0;

            int n = intervals.length;
            int[] starts = new int[n];
            int[] ends = new int[n];

            // 1. Separate start times and end times
            for (int i = 0; i < n; i++) {
                starts[i] = intervals[i][0];
                ends[i] = intervals[i][1];
            }

            // 2. Sort both independently
            Arrays.sort(starts);
            Arrays.sort(ends);

            int rooms = 0;
            int endPointer = 0;

            // 3. Process chronologically
            for (int startPointer = 0; startPointer < n; startPointer++) {
                if (starts[startPointer] < ends[endPointer]) {
                    rooms++; // Need a new room
                } else {
                    endPointer++; // Reuse an existing free room
                }
            }

            return rooms;
        }
    }

/**

        * **Time Complexity:** $\mathcal{O}(N \log N)$ (due to array sorting).
        * **Space Complexity:** $\mathcal{O}(N)$ to store the `starts` and `ends` arrays.

---

        ### Summary Checklist for Interval Problems

| LeetCode # | Problem Name | Strategy | Key Comparison |
        | --- | --- | --- | --- |
        | **56** | Merge Intervals | Sort by Start Time | Merge when `current.start <= prev.end` |
        | **252** | Meeting Rooms | Sort by Start Time | Return false when `current.start < prev.end` |
        | **253** | Meeting Rooms II | Min-Heap or Two-Pointers | Reuse room when `current.start >= earliestEnd` |
        | **435** | Non-overlapping Intervals | Sort by End Time (Greedy) | Remove when `current.start < prevEnd` |
}
 */
}

