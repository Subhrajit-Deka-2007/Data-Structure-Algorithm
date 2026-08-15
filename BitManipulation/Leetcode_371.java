package BitManipulation;

public class Leetcode_371
{
    class Sol{
        public int getSum(int a, int b) {
            while (b != 0) {
                int sumWithoutCarry = a ^ b;
                int carry = (a & b) << 1;
                a = sumWithoutCarry;
                b = carry;
            }
            return a;
        }
    }
/*
## Time Complexity: **O(1)**, bounded by O(32)

Let's derive this using our recurrence recipe, applied to the loop.

## Step 1 — Define $T$

Let $T$ = number of times the loop runs (i.e., how many times we go through the XOR + AND + shift steps before `b` becomes `0`).

## Step 2 — What shrinks each iteration?

Each pass, the **carry** (`b`) gets shifted left by 1 (`(a & b) << 1`). This means the carry's lowest possible bit position keeps moving further left — it can never "settle" at a lower position, only advance.

## Step 3 — What's the upper bound?

Since we're working with fixed-width 32-bit integers in Java, a carry can only be shifted left so many times before it **falls off the end** — once a carry would need to sit at position 32 or higher, it can no longer exist (it overflows out of the register/gets discarded). So the carry chain can propagate through **at most 32 bit positions**.

## Step 4 — Result

$$T \le 32$$

Since `32` is a **fixed constant**, unrelated to the actual values of `a` or `b` (whether they're `2+3` or `2{,}000{,}000{,}000 + 5`, the loop can never run more than 32 times):

$$T = O(1)$$

## Why this matches your earlier traces

In both examples you traced (`2+3` took 2 passes, `4+4` took 2 passes), the loop finished in just a handful of iterations — nowhere close to the worst-case bound of 32. That's expected: the **worst case** (maximum possible carry propagation) happens only when many consecutive bit positions are `1` in both `a` and `b` simultaneously, causing a long chain of cascading carries — similar to adding `0111...1 + 0000...1` in decimal, where a single `+1` can trigger a carry chain all the way through a long run of 9s (e.g., `999 + 1 = 1000`).

## Space complexity

Just a few extra `int` variables (`sumWithoutCarry`, `carry`) — no data structures that grow with input:

$$\text{Space} = O(1)$$

## Summary

| | Complexity | Why |
|---|---|---|
| Time | O(1) (bounded by 32) | carry can propagate through at most 32 bit positions in a fixed-width int |
| Space | O(1) | only a few scalar variables used |
*/
}
