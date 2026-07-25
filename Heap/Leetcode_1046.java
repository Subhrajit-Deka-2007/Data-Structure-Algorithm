package Heap;

import java.util.Collections;
import java.util.PriorityQueue;

public class Leetcode_1046
{

    class Solution {
        public int lastStoneWeight(int[] stones)
        {
      /*
      Appraoch 1 :
      Brut force :
      Step 1: We sort the array then we will smash the n-1 rock and n-2 rock
      Step 2 : Then I will put the result of there value in the n -1 rock and
      make the n-2 rock zero
      And I will keep repeating these process

      Time Complexity = O ( N * N log N )
      Space Complexity = O ( N) by merge sort and if
      we are given not to change the given array then the Space Complexity will
      be N + N => O ( 2N )

       int count = 0;

     while(stones.length >1 && count <stones.length)
     {
      sort(stones);
      stones [stones.length - 1 ] = stones[ stones.length - 1 ] - stones[ stones.length - 2 ];
      stones[ stones.length - 2 ] = 0;
      count ++;
     }
     return stones[stones.length -1];

     */

            /* Appraoch 2 using heap */
            return heapAppraoch(stones);

        }

        public void sort( int [] stones )
        {
            mergeSort(stones, 0 , stones.length -1 );
        }


        public static void mergeSort(int[] arr, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);
                merge(arr, left, mid, right);
            }
        }

        private static void merge(int[] arr, int left, int mid, int right)
        {
            int n1 = mid - left + 1;
            int n2 = right - mid;

            int[] leftArr = new int[n1];
            int[] rightArr = new int[n2];

            for (int i = 0; i < n1; i++) leftArr[i] = arr[left + i];
            for (int j = 0; j < n2; j++) rightArr[j] = arr[mid + 1 + j];

            int i = 0, j = 0, k = left;

            while (i < n1 && j < n2) {
                if (leftArr[i] <= rightArr[j]) {
                    arr[k] = leftArr[i];
                    i++;
                } else {
                    arr[k] = rightArr[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                arr[k] = leftArr[i];
                i++;
                k++;
            }

            while (j < n2) {
                arr[k] = rightArr[j];
                j++;
                k++;
            }
        }
    /*
    Optimize Approach : Using Maxheap
    Time Complexity Will Become
    N log N ( insertion in the heap )
    + (  Log N + Log N - 1 + ---- + Log 1  )
    => N log N + N log N
    => 2N log N
    */


        public int heapAppraoch ( int [] stones )
        {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            for ( int i = 0 ; i < stones.length ; i++ )
            {
                maxHeap.offer(stones[i]);
            }

            while( maxHeap.size() > 1 )
            {
                maxHeap.offer( maxHeap.poll() - maxHeap.poll() );
            }
            return maxHeap.size()==1?maxHeap.poll():0;
        }
    }

}
