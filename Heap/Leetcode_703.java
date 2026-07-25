package Heap;

import java.util.PriorityQueue;

public class Leetcode_703 {

    class KthLargest {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int k = 0;

        public KthLargest(int k, int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if (minHeap.size() > k) minHeap.poll();
                minHeap.offer(nums[i]);
            }

            this.k = k;
        }

        public int add(int val) {
            minHeap.add(val);

            while (minHeap.size() > k) minHeap.poll();

            return minHeap.peek();

        }
    }

/**
 Time: for each of the n calls to add, you do at most one heap insert and possibly one heap removal, each O(log k). So overall O(n log k) across n additions.

 Space: O(k), since the heap never holds more than k elements.
 */
}