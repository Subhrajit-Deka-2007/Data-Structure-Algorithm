package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class Radix_Sort
{

    public void radixSort(int[] nums) {
        // Step A: find the largest number in the array.
        // We need this to know how many digit-passes to run —
        // e.g., if max = 802, it has 3 digits, so we need 3 passes
        // (ones, tens, hundreds).
        int max = nums[0];
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }

        // Step B: run one counting-sort pass per digit position,
        // starting from the ones place (exp=1), then tens (exp=10),
        // then hundreds (exp=100), and so on.
        // The loop stops once exp exceeds the largest number
        // (i.e., there are no more digits left to process).
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(nums, exp);
        }
    }

    // Sorts nums by ONE specific digit, determined by `exp`.
    // exp=1   → looks at the ones digit
    // exp=10  → looks at the tens digit
    // exp=100 → looks at the hundreds digit, and so on.
    private void countingSortByDigit(int[] nums, int exp) {
        int n = nums.length;

        // output will hold the result of this pass —
        // numbers rearranged according to this digit.
        int[] output = new int[n];

        // count[d] will eventually tell us how many numbers
        // have digit d (0 through 9) — hence size 10.
        int[] count = new int[10];

        // ---- STEP 1: Tally how many numbers have each digit ----
        // For every number, extract its digit at this position,
        // and increment that digit's counter.
        for (int i = 0; i < n; i++) {
            int digit = (nums[i] / exp) % 10;
            count[digit]++;
        }
        // After this loop: count[d] = "how many numbers have digit d"

        // ---- STEP 2: Convert tallies into cumulative positions ----
        // Each slot absorbs the running total of everything before it.
        // This turns "how many numbers have EXACTLY digit d"
        // into "how many numbers have digit d OR SMALLER" —
        // which tells us the correct ending position for that digit group.
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        // After this loop: count[d] = "how many numbers have digit ≤ d"

        // ---- STEP 3: Place each number into its correct position ----
        // We walk BACKWARD through nums (last element first).
        // This preserves the original relative order of numbers
        // that share the same digit (this is what makes the sort "stable").
        for (int i = n - 1; i >= 0; i--) {
            int digit = (nums[i] / exp) % 10;

            // count[digit] tells us the 1-indexed position this number
            // belongs at, so we subtract 1 to get the 0-indexed array slot.
            output[count[digit] - 1] = nums[i];

            // Decrement so that if another number with the SAME digit
            // appears later in this backward walk, it claims the
            // slot just before this one — not the same slot.
            count[digit]--;
        }

        // ---- STEP 4: Copy this pass's sorted result back into nums ----
        // This becomes the starting point for the NEXT digit pass.
        for (int i = 0; i < n; i++) {
            nums[i] = output[i];
        }
    }
}
/*
Let's walk through this the same careful way — connecting the complexity directly back to the four steps you now understand.

## Cost of `countingSortByDigit` — one single pass

**Step 1 — tallying:**
```java
for (int i = 0; i < n; i++) {
    int digit = (nums[i] / exp) % 10;
    count[digit]++;
}
```
This loop touches every element in `nums` exactly once. If there are `n` numbers, this is **n steps**.

**Step 2 — cumulative sum:**
```java
for (int i = 1; i < 10; i++) {
    count[i] += count[i - 1];
}
```
This loop **always** runs exactly 9 times — because there are always exactly 10 possible digits (0-9), no matter how many numbers you're sorting. This is a **fixed constant**, independent of `n`. We call this **k** (and here, `k = 10`, always).

**Step 3 — placing (backward):**
```java
for (int i = n - 1; i >= 0; i--) {
    ...
    output[count[digit] - 1] = nums[i];
    count[digit]--;
}
```
Again touches every element in `nums` exactly once → **n steps**.

**Step 4 — copying back:**
```java
for (int i = 0; i < n; i++) {
    nums[i] = output[i];
}
```
Touches every element once more → **n steps**.

## Adding up one single pass

```
Step 1: n
Step 2: k  (constant, 10)
Step 3: n
Step 4: n

Total for ONE pass = n + k + n + n = 3n + k
```

Big-O drops constants, so one pass costs:
```
O(n + k)
```

## Now — how many passes are there?

Look at the outer function:
```java
for (int exp = 1; max / exp > 0; exp *= 10) {
    countingSortByDigit(nums, exp);
}
```
This loop runs once per **digit** in the largest number. If the largest number has, say, 3 digits (like 802), this loop runs **3 times**. Let's call the number of digits **d**.

## Putting it together — total cost across all passes

```
Total cost = (cost of one pass) × (number of passes)
           = O(n + k) × d
           = O(d · (n + k))
```

## Simplifying — why `k` basically disappears

Since `k` is **always exactly 10** (a fixed constant that never grows, no matter how big your input array or numbers get), we can simplify:
```
O(d · (n + k)) = O(d · (n + 10)) ≈ O(d · n)
```
(dropping the constant 10, same way we always drop constants in Big-O)

## Final answer

```
Time Complexity: O(d · n)
```
where:
- **n** = number of elements in the array
- **d** = number of digits in the largest number

## Why this is often "as good as O(n)" in practice

`d` grows **extremely slowly**, even for huge numbers. Consider:
```
A number up to 1,000              → d = 4
A number up to 1,000,000            → d = 7
A number up to 1,000,000,000        → d = 10
```
Even for numbers close to the limits of a 32-bit integer (~2.1 billion), `d` is only about **10**. So for most practical inputs, `d` behaves like a small constant — meaning `O(d · n)` is **effectively O(n)**, genuinely linear, which is why Radix Sort can outperform comparison-based sorts (which are always at least O(n log n)) when the number of digits is small relative to the array size.

## Space Complexity — quick pass, since you already know where everything lives

```
output array:  O(n)   — created fresh inside EACH pass, but only one exists at a time (same "sequential, not cumulative" reasoning we covered with merge sort earlier)
count array:   O(k) = O(10) = O(1)  — constant size, always 10 slots

Total space: O(n)
```

## Summary table

| | Complexity |
|---|---|
| Time | O(d · n) — effectively O(n) since d grows very slowly |
| Space | O(n) |

Does connecting each step's cost back to n and k, then multiplying by the number of passes (d), make the final formula click as something you derived rather than something to memorize?
 */