package Binary_Search;

public class Leetcode_153
{

    class Solution {
        public int findMin(int[] nums)
        {
   /*  int min = nums[0];

     for ( int i = 1 ; i< nums.length ; i++ )min = Math.min(min,nums[i]);

     return min;
*/




            // Brut force approach T.C = O (N)
            // Second appraoch sort and then use Binary Search => O ( N LOG N + LOG N )
            // Third approach find the value of k by which it is rotated and then

     /*
     Let's trace this very literally, step by step, with actual array states at each iteration — this will show us precisely what your loop produces.

## Setup

```
Rotated array: [4,5,1,2,3], k=2
```

## Your proposed loop — "start from k+1, go to arr.length, and rotate at each iteration"

**Let's interpret this as: loop `i` from `k+1` to `arr.length`, and at EACH iteration, perform SOME rotation.** Let's trace it assuming each iteration does a rotate-by-1 (a reasonable interpretation of "rotate the array" happening once per loop step):

```
k=2, so loop starts at i=3, goes to i=5 (arr.length)
That's iterations: i=3, i=4, i=5 → 3 total iterations
```

**Let's trace 3 rotations (right rotation by 1, repeated 3 times) starting from `[4,5,1,2,3]`:**

```
Start: [4,5,1,2,3]

Iteration 1 (rotate right by 1): [3,4,5,1,2]
Iteration 2 (rotate right by 1): [2,3,4,5,1]
Iteration 3 (rotate right by 1): [1,2,3,4,5]
```

**After 3 iterations, we DID arrive at `[1,2,3,4,5]` — the original array!**

## Let's verify WHY this specific count (3) worked

**The array was originally rotated RIGHT by k=2. To undo this, you need to rotate LEFT by 2 — which is mathematically EQUIVALENT to rotating RIGHT by `(n - k)` = `(5 - 2) = 3`.**

**Your loop, going from `k+1` to `arr.length`, produces exactly `arr.length - (k+1) + 1 = arr.length - k` iterations — which is `n - k`.** In our example: `5 - 2 = 3` — **matching exactly the number of right-rotations needed to undo the original rotation!**

## So — is your approach mathematically correct?

**Yes — genuinely correct.** `n - k` repeated single-step right rotations IS equivalent to one single left rotation by `k` (or one single right rotation by `n-k`, done all at once). **Your loop count formula (`k+1` to `arr.length`, giving `n-k` iterations) correctly identifies the right NUMBER of steps needed.**

## But let's check the efficiency — is doing it this way (many small rotations) as good as doing it in one shot?

**Each "rotate by 1" operation, if done naively (shifting every element over by one position), costs O(n) by itself.** **Doing this `n-k` times means: O(n-k) iterations × O(n) per iteration = O(n×(n-k))** — which in the worst case (when k is small) approaches **O(n²)**.

**Compare to doing ONE single rotation by `(n-k)` directly (using the reversal trick, or the modulo-index trick from before) — that's O(n) TOTAL, not O(n²).**

## The core insight

**Your approach is mathematically valid — you correctly identified that `n-k` repeated single-step rotations undoes the original rotation. But it's inefficient compared to computing the correct total rotation amount (`n-k`) and applying it ONCE, in a single O(n) pass, rather than repeating a smaller operation that many times.**

## The efficient version, using your correct insight about the AMOUNT

```
Instead of: loop n-k times, rotating by 1 each time (O(n²))
Do this:    rotate ONCE by (n-k) directly, using the reversal trick or modulo-index trick (O(n))
```

**Does seeing that your loop count (`n-k`) is mathematically correct, but that performing it as MANY small steps is less efficient than ONE big step of that same total size, clarify the distinction between "correct" and "efficient" here?**
*/


/** 4th Appraoch : Binary Search  */
            int i = 0;
            int j = nums.length - 1 ;

            int mid = 0 ;
            while ( i<j )
            {
                mid = i + ( j - i )/2;
                if( nums[mid] > nums[j] )i = mid+1;
                else j = mid;
            }
// when i and j are same we got the result
// Time complexity = O (Log N )
// Space Compelxity = O(1)
            return nums[i];
        }
    }


}
