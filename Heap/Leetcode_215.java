package Heap;

import java.util.PriorityQueue;

public class Leetcode_215
{
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            // we are doing it using min heap
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 0; i < nums.length; i++){
                pq.add(nums[i]);
                if (pq.size() > k)pq.remove();
            }
            return pq.peek();
        }
    /* T.c =n log k(for addition) + n-k log k (for removal)
     *     = O (2n-k(log k))
     * s.c =o(k)
     if we use best sorting algorithms t.c =o(nlogn) logn<k is sammler then then k but s.c =o(n) but here s.c is o(k) which is better
     this is approxiamte calcualtion i ahd done non approximate calcualtion also in my intellih=j kth samllest element
     }*/
    }
// 0 ms answer
/*
   public int findKthLargest(int[] nums, int k) {
        int [] count=new int[20001];
        for(int num:nums)
        {
            count[num+10000]++;
        }
        for(int i=count.length-1;i>=0;i--)
        {
            if(count[i]>0)
            {
                k -= count[i];
                if(k<=0)
                {
                    return i-10000;

                }
            }
        }
        return -1;

    }
}
*/
}
