package DynamicProgramming;

public class Leetcode_152
{
    class Solution {

        // Let's first dolve it suing kadane's algo
        public int maxProduct0(int[] nums) {
            int n = nums.length;
            int result = nums[0];

            int prefix = 0;
            int suffix = 0;

            for (int i = 0; i < n; i++)
            {
                // reset to 1 if we hit a zero or start of array
                prefix = (prefix == 0 ? 1 : prefix) * nums[i];
                suffix = (suffix == 0 ? 1 : suffix) * nums[n - 1 - i];

                result = Math.max(result, Math.max(prefix, suffix));
            }

            return result;
        }
/*
Time Complexity = O(n)
Space Complexity = O(1)
*/
        /* Kadane's Style */


        public int maxProduct(int[] nums) {
            int maxSoFar = nums[0];
            int minSoFar = nums[0];
            int result = nums[0];

            for (int i = 1; i < nums.length; i++) {
                int curr = nums[i];

                if (curr < 0) {
                    int temp = maxSoFar;
                    maxSoFar = minSoFar;
                    minSoFar = temp;
                }

                maxSoFar = Math.max(curr, maxSoFar * curr);
                minSoFar = Math.min(curr, minSoFar * curr);

                result = Math.max(result, maxSoFar);
            }

            return result;
        }
    /*
    Time Complexity = O(N)
    Spce Complexity = O(1)
    */

    /*
    Let's isolate just that one piece, since it's the part that feels like a "trick" until you see why it's forced by the math.

## The setup

At every index, we're carrying forward two numbers:
- `maxSoFar` — the best (largest) product of a subarray ending at the previous index
- `minSoFar` — the worst (most negative) product of a subarray ending at the previous index

Now a new number `curr` shows up, and we want to extend both of these by multiplying: `maxSoFar * curr` and `minSoFar * curr`.

## The problem, with real numbers

Say at the previous step:
```
maxSoFar = 6   (this was our best product so far)
minSoFar = -12 (this was our worst product so far)
```

Now suppose `curr = -2` (negative).

Multiply both forward:
```
maxSoFar * curr = 6 * -2 = -12
minSoFar * curr = -12 * -2 = 24
```

Look what just happened: **multiplying our "worst" number by this negative gave us 24 — a much bigger number than what we get from multiplying our "best" number (-12).**

The labels flipped. The thing we were calling `minSoFar` (the bad one) produced the actual best result. The thing we were calling `maxSoFar` (the good one) produced the worst result.

## Why this makes sense conceptually

Multiplying by a negative number **reverses order**. If `A > B`, then `A * (negative) < B * (negative)` — the bigger number becomes the smaller one, and vice versa. This is just basic arithmetic (like how `5 > 3`, but `5 * -1 = -5` and `3 * -1 = -3`, and now `-5 < -3`).

So the moment `curr` is negative, whatever was your biggest running product is about to become your smallest, and whatever was your smallest is about to become your biggest.

## Why we swap *before* computing max/min

Now look at the actual formula we apply after this:

```java
maxSoFar = Math.max(curr, maxSoFar * curr);
minSoFar = Math.min(curr, minSoFar * curr);
```

This formula assumes `maxSoFar` (going in) represents "the value that will help me get a big result" and `minSoFar` (going in) represents "the value that will help me get a small result." But we just showed that when `curr` is negative, it's actually the **old minSoFar** that produces the big result, and the **old maxSoFar** that produces the small result.

So if we *don't* swap first, the formula computes garbage — it would compute `Math.max(curr, maxSoFar * curr)` using the wrong operand, missing the fact that `minSoFar * curr` is actually the better candidate.

By swapping first:
```java
if (curr < 0) {
    int temp = maxSoFar;
    maxSoFar = minSoFar;  // the old min is now correctly labeled as the "max producer"
    minSoFar = temp;      // the old max is now correctly labeled as the "min producer"
}
```

...we relabel the two values so that whichever one is *actually* going to produce the bigger result after multiplying is the one sitting in the `maxSoFar` variable when we run the formula. Then the exact same `Math.max`/`Math.min` formula works correctly, without needing a separate formula for the negative case.

## Concrete trace, putting it together

```
maxSoFar = 6, minSoFar = -12, curr = -2
```

**Step 1 — swap (because curr < 0):**
```
maxSoFar = -12, minSoFar = 6
```

**Step 2 — apply formula:**
```
maxSoFar = max(-2, -12 * -2) = max(-2, 24) = 24
minSoFar = min(-2, 6 * -2)   = min(-2, -12) = -12
```

Final result: `maxSoFar = 24` — correctly capturing that the old "bad" number became great once multiplied by this negative.

## The one-line version to say in an interview

**"Multiplying by a negative number flips the ranking of any two values — my biggest becomes my smallest and vice versa — so I swap max and min before applying the update formula, otherwise the formula would compare the wrong candidates."**

Want to trace one more example where `curr` is positive, just to contrast and confirm no swap is needed there?
*/
    }
}
