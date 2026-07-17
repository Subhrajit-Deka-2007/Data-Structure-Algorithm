package Arrays;

import java.util.ArrayDeque;
import java.util.Deque;

public class Leetcode_239
{

    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            int[] result = new int[n - k + 1];
            Deque<Integer> deque = new ArrayDeque<>();

            for (int right = 0; right < n; right++) {
                while (!deque.isEmpty() && nums[deque.peekLast()] < nums[right]) {
                    deque.removeLast();
                }

                deque.addLast(right);

                if (deque.peekFirst() <= right - k) {
                    deque.removeFirst();
                }

                if (right >= k - 1) {
                    result[right - k + 1] = nums[deque.peekFirst()];
                }
            }

            return result;
        }
    }
/**

 Let's do this as a pure walkthrough first — full trace, all 8 steps, no code at all. Then we'll map it to the code afterward.

 ## The example

 ```
 nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 0  1   2   3  4  5  6  7
 ```

 We're going to maintain a **list of "candidate" indices** — kept in an order where their VALUES go from **biggest to smallest**, left to right. Let's call this list our "candidates."

 ## Step, index 0 (value 1)

 **Candidates list is empty.** Nothing to compare against. Just add index 0.
 ```
 Candidates: [0]  (values: [1])
 ```

 ## Step, index 1 (value 3)

 **Before adding, check: is the LAST candidate's value smaller than 3?** Last candidate is index 0, value 1. Is `1 < 3`? **YES.** Since 1 is smaller, and 3 just showed up and will outlast 1 anyway (1 is now useless, gone forever) — **remove index 0 from candidates.**
 ```
 Candidates: []  (removed 0)
 ```
 Now add index 1.
 ```
 Candidates: [1]  (values: [3])
 ```

 ## Step, index 2 (value -1)

 **Check: is the LAST candidate's value smaller than -1?** Last candidate is index 1, value 3. Is `3 < -1`? **NO.** So we don't remove anything. Just add index 2 to the end.
 ```
 Candidates: [1, 2]  (values: [3, -1])
 ```

 **Window check: our window is now indices [0,1,2] — full size (k=3)!** Time to report the max. **The max is always the FIRST candidate in our list** (since we maintain biggest-to-smallest order). First candidate is index 1, value **3**.
 ```
 Result so far: [3]
 ```

 ## Step, index 3 (value -3)

 **Check: is the LAST candidate's value smaller than -3?** Last candidate is index 2, value -1. Is `-1 < -3`? **NO.** Don't remove anything. Add index 3.
 ```
 Candidates: [1, 2, 3]  (values: [3, -1, -3])
 ```

 **Window check: window is now [1,2,3]. Did index 0 fall out of the window? YES (window no longer includes index 0).** **Check: is our FIRST candidate index 0?** No, first candidate is index 1. So nothing to remove from the front.

 **Report max: first candidate is index 1, value 3.**
 ```
 Result so far: [3, 3]
 ```

 ## Step, index 4 (value 5)

 **Check: is the LAST candidate's value smaller than 5?** Last candidate index 3, value -3. Is `-3<5`? YES → remove index 3.
 ```
 Candidates: [1, 2]
 ```
 **Check again: is the NEW last candidate's value smaller than 5?** Last is now index 2, value -1. Is `-1<5`? YES → remove index 2.
 ```
 Candidates: [1]
 ```
 **Check again:** last candidate is index 1, value 3. Is `3<5`? YES → remove index 1.
 ```
 Candidates: []
 ```
 Nothing left smaller — add index 4.
 ```
 Candidates: [4]  (values: [5])
 ```

 **Window check: window is now [2,3,4]. Did index 1 fall out? YES.** Is our first candidate index 1? No (it's index 4, since we just removed it above anyway). Nothing extra to remove.

 **Report max: first candidate is index 4, value 5.**
 ```
 Result so far: [3, 3, 5]
 ```

 ## Check-in before continuing

 **Does this trace — maintaining a list where you keep removing smaller "useless" candidates from the end before adding a new one, and always reading the max from the front — make sense as a repeatable pattern for these 5 steps?**

 If yes, I'll continue the trace for the remaining 3 indices (5, 6, 7), and THEN we'll map every single one of these actions back to the exact code lines. Want to continue?



 Time: O(n) — each index enters the deque exactly once and leaves at most once, total across the whole run
 Space: O(k) — the deque never holds more than k elements at a time



 */


}
