package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Bucket_Sort_Leetcode_347
{
    public static void main(String[] args)
    {
      /*

        Generic example first — sorting numbers in a known small range
        Say you have numbers, all guaranteed to be between 0 and 9.
        */
        int[] nums = {4, 1, 3, 1, 2, 4, 4};

        /*
        Step 1: Create buckets — one bucket per possible value (0 through 9):

        */
        List<Integer>[] buckets = new List[10]; // indices 0-9
        for (int i = 0; i < 10; i++) buckets[i] = new ArrayList<>();


       /*
       Step 2: Place each number into the bucket matching its value:
        */
     for (int num : nums) buckets[num].add(num);

        /*
        bucket[0]: []
        bucket[1]: [1, 1]
        bucket[2]: [2]
        bucket[3]: [3]
        bucket[4]: [4, 4, 4]
        bucket[5]: []
        ....
         */


        /*  Step 3: Walk the buckets in order (0 → 9), collecting everything: */
         List<Integer> sorted = new ArrayList<>();
        for (int i = 0; i < 10; i++) sorted.addAll(buckets[i]);

        /*
         sorted = [1, 1, 2, 3, 4, 4, 4]
         No comparisons happened anywhere — the sorted order came purely
         from the bucket index representing sorted position.
         That's why this beats O(n log n) — it's O(n + k) where k is the number of buckets,
         because you just distribute once and read back in order.
         */


        /* Approach 2 : Using Bucket Sort */
    }
}
