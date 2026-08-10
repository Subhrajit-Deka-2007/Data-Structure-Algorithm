package Greedy;

import java.util.TreeMap;

public class Leetcode_846
{

    class Solution
    {

            public boolean isNStraightHand(int[] hand, int groupSize) {
            if (hand.length % groupSize != 0) return false;

            TreeMap<Integer, Integer> count = new TreeMap<>();
            for (int card : hand) {
                count.put(card, count.getOrDefault(card, 0) + 1);
            }

            while (!count.isEmpty()) {
                int smallest = count.firstKey();   // O(log n) — TreeMap keeps sorted order

                for (int i = smallest; i < smallest + groupSize; i++) {
                    if (!count.containsKey(i)) return false;   // gap found, impossible

                    int c = count.get(i);
                    if (c == 1) {
                        count.remove(i);            // fully consumed, drop it
                    } else {
                        count.put(i, c - 1);         // one less copy remains
                    }
                }
            }
            return true;
        }
        }
    }
/*
Let's do this rigorously, the same way we did for `canJump` — count exact operations first, then collapse to Big-O.

## The algorithm we're analyzing

```java
public boolean isNStraightHand(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0) return false;

    TreeMap<Integer, Integer> count = new TreeMap<>();
    for (int card : hand) {
        count.put(card, count.getOrDefault(card, 0) + 1);
    }

    while (!count.isEmpty()) {
        int smallest = count.firstKey();
        for (int i = smallest; i < smallest + groupSize; i++) {
            if (!count.containsKey(i)) return false;
            int c = count.get(i);
            if (c == 1) count.remove(i);
            else count.put(i, c - 1);
        }
    }
    return true;
}
```

Let `n = hand.length`, `k = groupSize`, `m` = number of **distinct** card values (so `m ≤ n`).

## Step 1: Break the code into pieces and cost each one

**Piece A — building the frequency map:**
```java
for (int card : hand) {
    count.put(card, count.getOrDefault(card, 0) + 1);
}
```
- Loop runs `n` times (once per card).
- Each iteration: one `getOrDefault` (O(log m), since map has ≤ m keys) + one `put` (O(log m)).
- **Cost of Piece A = n · O(log m) = O(n log m)**

**Piece B — the main while/for loop:**

Here's the key thing to count carefully: **how many total group-forming operations happen across the whole algorithm's lifetime?**

- Every successful group consumes exactly `k` cards (one copy of `k` consecutive values), and every card in `hand` belongs to exactly one group by the time the algorithm finishes successfully.
- So total number of "cards consumed" across all groups, summed over the whole run = exactly `n` (every card processed once, ever — nothing is revisited).
- Each single card-consumption step does: `containsKey` (O(log m)) + `get` (O(log m)) + either `remove` or `put` (O(log m)) — so **O(log m)** per card.
- Also, `firstKey()` is called once per **group** (not per card) — number of groups = `n/k`, each call costing O(log m).

**Total for Piece B:**
$$n \cdot O(\log m) \;+\; \frac{n}{k}\cdot O(\log m) \;=\; O\!\left(n\log m + \frac{n}{k}\log m\right)$$

Since `n/k ≤ n`, the first term dominates:
$$\text{Piece B} = O(n \log m)$$

## Step 2: Combine

$$\text{Total Time} = O(n\log m) + O(n\log m) = O(n \log m)$$

## Step 3: Bound `m` in terms of `n` (this is the "approximate" step you asked for)

`m` = number of distinct values in `hand`, and by definition `m ≤ n` (can't have more distinct values than total elements). So in the **worst case**, `m = n` (all cards distinct except where duplicates are needed for grouping), giving:

$$\boxed{\text{Time} = O(n \log n)}$$

This is the standard way you "approximate" — bounding a variable (`m`) by a simpler one (`n`) you already understand, when the tighter bound isn't needed for the final answer.

## Space complexity, same rigor

- `TreeMap<Integer,Integer> count` stores at most `m` distinct keys → **O(m)**, worst case **O(n)**.
- No recursion here (this is iterative, unlike `canJump`), so no call-stack cost to add.
- The `for` loop inside uses O(1) extra variables (`smallest`, `i`, `c`).

$$\boxed{\text{Space} = O(n)}$$

## Summary table

| Piece | Exact cost | Big-O |
|---|---|---|
| Build frequency map | `n` insertions × O(log m) each | O(n log m) |
| Main loop (all groups combined) | `n` card-consumptions × O(log m), plus `n/k` firstKey() calls × O(log m) | O(n log m) |
| **Total time** | | **O(n log m) ≈ O(n log n)** |
| **Total space** | TreeMap holding ≤ m keys | **O(m) ≈ O(n)** |

## The one subtlety worth flagging

Notice the total time does **not** depend on `k` (groupSize) at all in the final bound — even though the inner `for` loop literally runs `k` times per group. That's because **more groups (`n/k`) exactly compensates for fewer cards per group (`k`)** — `(n/k) × k = n` regardless of what `k` is. This is the same style of accounting trick as amortized analysis: don't multiply worst-case-per-call by number-of-calls blindly: recognize that the *total* work across all calls telescopes to something clean (`n`, not `n·k`).

Want to verify this by tracing the exact operation count on `hand=[1,2,3,6,2,3,4,7,8], groupSize=3` — count every `put`/`get`/`containsKey`/`firstKey` call by hand and confirm it matches this formula?
*/

class Solution3 {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;
        if (n % groupSize != 0)
            return false;

        quickSort(hand, 0, n - 1);

        int i = 0;
        for (; i < n; i++) {
            if (hand[i] >= 0) {
                if (!findSucessors(hand, groupSize, i, n)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean findSucessors(int[] hand, int groupSize, int i, int n) {

        int next = hand[i] + 1;
        hand[i] = -1;
        int count = 1;
        i += 1;
        while (i < n && count < groupSize) {
            if (next == hand[i]) {
                next = hand[i] + 1;
                hand[i] = -1;
                count++;
            }
            i++;
        }

        if (count != groupSize)
            return false;

        return true;
    }

    public void quickSort(int[] hand, int low, int high) {
        if (low < high) {

            int pi = partition(hand, low, high);

            quickSort(hand, low, pi - 1);
            quickSort(hand, pi + 1, high);
        }
    }

    public int partition(int[] hand, int low, int high) {
        int pivot = hand[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (hand[j] < pivot) {
                i++;
                int temp = hand[i];
                hand[i] = hand[j];
                hand[j] = temp;
            }
        }
        int temp = hand[i + 1];
        hand[i + 1] = hand[high];
        hand[high] = temp;
        return i + 1;
    }
}
