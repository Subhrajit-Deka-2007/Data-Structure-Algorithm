package Heap;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Arrays;

public class Leetcode_621
{

    class Solution {
        public int leastInterval0(char[] tasks, int n)
        {
        /*
        Approach 1 : Using Max Heap
         */
            int[] freq = new int[26];
            for (char t : tasks) freq[t - 'A']++;

            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            for (int f : freq) {
                if (f > 0) maxHeap.offer(f);
            }

            int time = 0;
            Queue<int[]> cooldownQueue = new LinkedList<>(); // [count, availableAtTime]

            while (!maxHeap.isEmpty() || !cooldownQueue.isEmpty()) {
                time++;

                if (!maxHeap.isEmpty()) {
                    int count = maxHeap.poll() - 1;
                    if (count > 0) {
                        cooldownQueue.offer(new int[]{count, time + n});
                    }
                }
                // if maxHeap was empty, this tick is idle — time still advanced above

                if (!cooldownQueue.isEmpty() && cooldownQueue.peek()[1] == time) {
                    maxHeap.offer(cooldownQueue.poll()[0]);
                }
            }

            return time;
        }
        public int leastInterval_1(char[] tasks, int n) {
            /* Approach : 2 */
            int[] freq = new int[26];
            for (char t : tasks) {
                freq[t - 'A']++;
            }

            int maxFreq = 0;
            int maxCount = 0;

            for (int f : freq) {
                if (f > maxFreq) {
                    maxFreq = f;
                    maxCount = 1;
                } else if (f == maxFreq) {
                    maxCount++;
                }
            }

            int skeleton = (maxFreq - 1) * (n + 1) + maxCount;
            return Math.max(skeleton, tasks.length);
        /*
        Time Complexity = O(1)
        Space Complexity = O (1)
        */
        }


        /*
        Approach 3 :
       */
    public int leastInterval2(char[] tasks, int n)
    {
        int[] freq = new int[26];
        for (char t : tasks) freq[t - 'A']++;

        Arrays.sort(freq); // ascending; max frequency ends up at freq[25]

        int maxFreq = freq[25];
        int idleSlots = (maxFreq - 1) * n;

        // walk backward through remaining frequencies, filling idle slots
        for (int i = 24; i >= 0 && freq[i] > 0; i--) {
            idleSlots -= Math.min(maxFreq - 1, freq[i]);
        }

        idleSlots = Math.max(0, idleSlots);
        return tasks.length + idleSlots;
    }
}
/*
Approach : 4
 */
public int leastInterval3(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) {
        freq[t - 'A']++;
    }

    int time = 0;
    int tasksLeft = tasks.length;

    while (tasksLeft > 0) {
        boolean[] usedThisBlock = new boolean[26]; // reset at the start of every block

        for (int i = 0; i < n + 1 && tasksLeft > 0; i++) {
            int maxIdx = getMaxIndex(freq, usedThisBlock);

            if (maxIdx != -1) {
                freq[maxIdx]--;
                tasksLeft--;
                usedThisBlock[maxIdx] = true;
            }

            time++; // tick passes whether a task ran or it was idle
        }
    }

    return time;
}

    private int getMaxIndex(int[] freq, boolean[] usedThisBlock) {
        int maxIdx = -1;
        int maxVal = 0;

        for (int i = 0; i < 26; i++) {
            if (!usedThisBlock[i] && freq[i] > maxVal) {
                maxVal = freq[i];
                maxIdx = i;
            }
        }

        return maxIdx;
    }
    }
/**
 * Here's the breakdown for all four:
 *
 * ## Approach 1: Max Heap (Priority Queue)
 *
 * **Time:** O(N log k), where N = total number of tasks, k = number of distinct task types (≤26).
 * Each task triggers at most one heap insert and one heap removal, each O(log k). Since k ≤ 26, this is effectively O(N).
 *
 * **Space:** O(k) = O(26) = O(1).
 * The heap and cooldown queue together never hold more than k distinct task types.
 *
 * ## Approach 2: Filling Slots and Sorting
 *
 * **Time:** O(N + k log k), where N = total tasks (for counting frequencies), k log k for sorting the frequency array (k=26, so this is a constant).
 * Effectively O(N).
 *
 * **Space:** O(k) = O(26) = O(1) for the frequency array.
 *
 * ## Approach 3: Greedy (array scan per tick)
 *
 * **Time:** O(N × k), since for every one of the N ticks (roughly), you scan all 26 letters to find the max. Since k=26 is fixed, this is O(26N) = O(N) in practice, though technically the constant factor is higher than the heap approach since there's no log speedup — you're doing a full linear scan every tick instead of O(log k).
 *
 * **Space:** O(k) = O(26) = O(1) for the frequency array and the `usedThisBlock` array.
 *
 * ## Approach 4: Math Formula
 *
 * **Time:** O(N) just to count frequencies (one pass through tasks), then O(26) to find maxFreq and maxCount. Overall O(N).
 *
 * **Space:** O(26) = O(1) for the frequency array.
 *
 * ---
 *
 * ## Summary table
 *
 * | Approach | Time | Space |
 * |---|---|---|
 * | Max Heap | O(N log k) | O(k) |
 * | Filling Slots | O(N + k log k) | O(k) |
 * | Greedy (array scan) | O(N × k) | O(k) |
 * | Math Formula | O(N) | O(k) |
 *
 * Since `k` is capped at 26 (fixed alphabet), all four are effectively **O(N) time, O(1) space** in practice — but the Math Formula is the cleanest and fastest since it avoids any per-tick work entirely,
 * just a single pass over the tasks plus a constant-size scan.
 */

