package DynamicProgramming;
import java.util.Arrays;
public class Leetcode_312
{

        public int maxCoins0(int[] nums) {
            int n = nums.length;
    /*
    What i and j represent, again

Recall: solve(i, j) = max coins from bursting all balloons strictly between index i and index j.
Both i and j are fixed walls — they are not burst, they just mark the boundaries of "what am I responsible for clearing in this call."
*/
            // Step 1: pad with 1s on both sides
            int[] balloons = new int[n + 2];
            balloons[0] = 1;
            balloons[n + 1] = 1;
            for (int i = 0; i < n; i++) {
                balloons[i + 1] = nums[i];
            }

            // Step 2: solve for the full range (0, n+1)
            return solve(balloons, 0, n + 1);
        }

        private int solve(int[] b, int i, int j) {
            // base case: nothing strictly between i and j
            if (j == i + 1)
            {
        /*
Base Case : i = 2, j = 3   →  is there any index k with 2 < k < 3?  →  No.  Empty range.
i = 2, j = 4   →  is there any index k with 2 < k < 4?  →  Yes, k=3.  Non-empty.
*/
                return 0;
            }

            int best = 0;
            // try every k strictly between i and j as the LAST balloon burst
            for (int k = i + 1; k < j; k++) {
                int total = solve(b, i, k)          // clear left side first
                        + solve(b, k, j)          // clear right side first
                        + b[i] * b[k] * b[j];     // then burst k, neighbors are the walls
                best = Math.max(best, total);
            }

            return best;
        }
/*
Let's build this up from scratch, one justified step at a time, using exactly what you now understand: 2 calls per iteration, `m` iterations, `m` shrinking per node.

## Step 1 — Define precisely what we're counting

Let `C(m)` = the **total number of `solve()` calls** made in the entire subtree rooted at a node whose range has `m` balloons strictly inside it (this includes the node itself, plus every call made recursively below it, all the way to base cases).

We want `C(n)`, since the top call has all `n` balloons inside its walls.

## Step 2 — Turn the code into a recurrence for C(m)

Take a node with parameter `m`. Two cases:

**Case m = 0 (base case):**
```java
if (j == i + 1) return 0;
```
No loop runs, no recursive calls. This node is just itself.
```
C(0) = 1
```

**Case m > 0:** the loop runs `m` times (once for each valid `k`). On the `t`-th iteration (`t = 0, 1, ..., m-1`), say the left piece `solve(i,k)` has `p` balloons inside it and the right piece `solve(k,j)` has `q` balloons inside it. Since `k` itself is the one balloon "used up" as the splitting point, the remaining `m-1` balloons split between left and right:
```
p + q = m - 1
```
As `t` runs from `0` to `m-1`, `p` takes every value `0, 1, 2, ..., m-1` exactly once (and correspondingly `q = m-1-p`).

So the total calls contributed by this node's subtree:
```
C(m) = 1                              ← the node itself
     + Σ_{p=0}^{m-1} C(p)             ← all the "left" subtrees, one per iteration
     + Σ_{p=0}^{m-1} C(m-1-p)         ← all the "right" subtrees, one per iteration
```

The two sums range over the same set of values `{0, 1, ..., m-1}` (the second is just listed in reverse order), so they're equal:
```
C(m) = 1 + 2·Σ_{p=0}^{m-1} C(p)
```

This is the exact recurrence — nothing hand-waved, every term traces to a real recursive call in the code.

## Step 3 — Verify it against the trees you already counted by hand

Let `s(m) = Σ_{p=0}^{m-1} C(p)`, so `C(m) = 1 + 2·s(m)`.

```
C(0) = 1                                              [base case]

s(1) = C(0) = 1
C(1) = 1 + 2(1) = 3        ← matches: solve(1,3) node → itself + 2 leaf children = 3 ✓

s(2) = C(0) + C(1) = 1 + 3 = 4
C(2) = 1 + 2(4) = 9        ← matches: solve(0,3) full tree = 9 nodes, which you counted ✓
```

Good — this recurrence reproduces the exact tree sizes you already verified by counting nodes.

## Step 4 — Solve the recurrence in closed form

**Guess from the pattern:** `1, 3, 9, ...` looks like powers of 3. Guess `C(m) = 3^m`.

**Prove it by induction.**

*Base case:* `C(0) = 1 = 3^0`. ✓ (already established)

*Inductive hypothesis:* assume `C(p) = 3^p` holds for all `p < m`.

*Inductive step:* compute `s(m)` using the hypothesis:
```
s(m) = Σ_{p=0}^{m-1} C(p) = Σ_{p=0}^{m-1} 3^p
```
This is a geometric series with ratio 3, first term `3^0 = 1`, `m` terms. Standard geometric sum formula:
```
Σ_{p=0}^{m-1} 3^p = (3^m - 1) / (3 - 1) = (3^m - 1) / 2
```

Substitute into the recurrence:
```
C(m) = 1 + 2·s(m) = 1 + 2 · (3^m - 1)/2 = 1 + (3^m - 1) = 3^m
```

This matches the guess `C(m) = 3^m`, closing the induction. Since it holds for `m` given it holds for all smaller values, and the base case holds, **it holds for all `m ≥ 0`.**

$$C(m) = 3^m \quad \text{exactly, for all } m$$

## Step 5 — Apply to the actual problem: count total *work*, not just calls

`C(m)` counts calls, but each call with parameter `m` isn't free — it runs a loop of `m` iterations doing O(1) work each. So define a second quantity:

`W(m)` = total loop-iterations (i.e., total elementary work) summed across the entire subtree for a node with `m` balloons inside.

Same derivation, but the "1" (this node's own call) is replaced by "`m`" (this node's own loop cost):
```
W(m) = m + 2·Σ_{p=0}^{m-1} W(p)
```

This doesn't collapse to a clean closed form like `3^m`, but we can bound it. Since `W(p) ≤ C(p)·(max iterations at any single node) ≤ p·3^p ≤ m·3^p` for `p < m`:
```
W(m) ≤ m + 2·Σ_{p=0}^{m-1} m·3^p ≤ m + 2m·(3^m - 1)/2 = m + m·3^m - m = m·3^m
```
And `W(m) ≥ C(m) - 1` trivially (at least one unit of work per non-base node), which is `Θ(3^m)` on its own — but the tighter analysis (matching what convergence of the ratio `W(m)/W(m-1) → 3` showed numerically before) gives:

$$W(n) = \Theta(n \cdot 3^n)$$

## Final answer

$$\boxed{T(n) = \Theta(n \cdot 3^n)}$$

- The `3^n` factor comes from `C(n) = 3^n`, derived exactly via the recurrence `C(m) = 1 + 2Σ C(p)` solved by the geometric series.
- The extra factor of `n` comes from the fact that each call does `O(m)` work (the loop itself), not `O(1)` — so it's `work per call × structure of the tree`, not just node count.
- **Space:** `O(n)` for the call stack (max recursion depth = number of times you can shrink a range before hitting a base case, which is at most `n`).
*/








    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }

        // memo[i][j] = answer for solve(i, j); -1 means "not computed yet"
        int[][] memo = new int[n + 2][n + 2];
        for (int[] row : memo) Arrays.fill(row, -1);

        return solve(balloons, 0, n + 1, memo);
    }

    private int solve(int[] b, int i, int j, int[][] memo) {
        if (j == i + 1) return 0;               // base case, same as before

        if (memo[i][j] != -1) return memo[i][j]; // ← the ONLY new logic

        int best = 0;
        for (int k = i + 1; k < j; k++) {
            int total = solve(b, i, k, memo)
                    + solve(b, k, j, memo)
                    + b[i] * b[k] * b[j];
            best = Math.max(best, total);
        }

        memo[i][j] = best;                       // ← cache before returning
        return best;
    }



    public int maxCoins2(int[] nums) {
        int n = nums.length;
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }

        // dp[i][j] = max coins from bursting everything strictly between i and j
        int[][] dp = new int[n + 2][n + 2];
        // dp[i][i+1] = 0 for all i is the implicit base case (int[] default 0) — no loop needed

        // len = size of the gap (j - i), starting at 2 (smallest non-base case)
        for (int len = 2; len <= n + 1; len++) {
            for (int i = 0; i + len <= n + 1; i++) {
                int j = i + len;
                int best = 0;
                for (int k = i + 1; k < j; k++) {
                    int total = dp[i][k] + dp[k][j] + balloons[i] * balloons[k] * balloons[j];
                    best = Math.max(best, total);
                }
                dp[i][j] = best;
            }
        }

        return dp[0][n + 1];
    }
}



class Solution1 {
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int n = nums.length, len = 1;
        int[] balloons = new int[n + 2];
        for (int b : nums)
            if (b > 0)
                balloons[len++] = b;
        balloons[0] = 1;
        balloons[len++] = 1;

        int[][] cache = new int[len][len];
        for (int i = 2; i < len; i++) {
            for (int left = 0; i + left < len; left++) {
                int right = left + i;
                int boundryProduct = balloons[left] * balloons[right];
                int maxCoin = 0;
                for (int j = left + 1; j < right; j++) {
                    int coin = balloons[j] * boundryProduct;
                    coin += cache[left][j];
                    coin += cache[j][right];
                    maxCoin = Math.max(maxCoin, coin);
                }
                cache[left][right] = maxCoin;
            }
        }

        return cache[0][len-1];
    }
}
/*

Good question — and it's worth being precise here, because "multiplying by n" is really "multiplying by m", and m is not the same thing for every pair. Let's rebuild this carefully so you see exactly what's being multiplied and why.

## First: what does "cost of a pair" mean?

Each pair `(i,j)` is a cell in our table, `memo[i][j]`. To *fill in* that cell — compute its actual value for the first time — the code runs this loop:

```java
for (int k = i + 1; k < j; k++) { ... }
```

The number of times this loop runs equals the number of valid `k` values strictly between `i` and `j`. That count is `m = j - i - 1`.

**So "cost of filling cell `(i,j)`" = m = j - i - 1, and this is different for every single pair.** It is NOT the same `n` for every cell. Let's check this against our actual list of 10 pairs from n=3 (indices 0-4):

| pair (i,j) | m = j-i-1 | meaning |
|---|---|---|
| (0,1) | 0 | loop runs 0 times |
| (1,2) | 0 | loop runs 0 times |
| (2,3) | 0 | loop runs 0 times |
| (3,4) | 0 | loop runs 0 times |
| (0,2) | 1 | loop runs 1 time |
| (1,3) | 1 | loop runs 1 time |
| (2,4) | 1 | loop runs 1 time |
| (0,3) | 2 | loop runs 2 times |
| (1,4) | 2 | loop runs 2 times |
| (0,4) | 3 | loop runs 3 times |

## The REAL total cost — add up this column, don't multiply

```
Total work = 0+0+0+0 + 1+1+1 + 2+2 + 3
           = 0 + 3 + 4 + 3
           = 10
```

This is the **honest, exact total** — no shortcuts. Each cell contributes its own real cost, and we just add them all up.

## So why did I earlier say "multiply by n"?

Because doing the exact sum above is annoying in general, we take a shortcut: **replace every cell's real cost with the worst-case cost**, which is `n` (the biggest `m` can ever be, happening only at the single outermost cell `(0, n+1)`).

```
real total = 10          (the honest sum, cell-by-cell)
upper bound = 10 cells × 3 (worst-case m)= 30
```

`30` is a valid **upper bound** — definitely not smaller than the true `10` — but it's not the exact answer. That's all Big-O ever promises: a guarantee that the true cost doesn't *exceed* this, not that it equals it. We multiply because it's a quick, safe way to get a bound, sacrificing precision for simplicity.

## Why the upper bound is still the right order of growth

You might worry the multiply-by-worst-case trick is way too pessimistic (`30` vs `10`, 3x off). But watch what happens as `n` grows — check `n=4` (6 indices, 0-5):

Real costs by `m`: `m=0` happens 5 times, `m=1` happens 4 times, `m=2` happens 3 times, `m=3` happens 2 times, `m=4` happens 1 time.
```
real total = 0(5) + 1(4) + 2(3) + 3(2) + 4(1) = 0+4+6+6+4 = 20
cells = 15, worst-case m = 4  →  upper bound = 15×4 = 60
```

Ratio `60/20 = 3`. For `n=3` it was `30/10 = 3` too. **The ratio between the sloppy bound and the real answer stays constant (around 3) as n grows** — it doesn't get worse and worse. That's exactly what Big-O cares about: not the exact constant, but whether the *growth rate* is right. Both the honest sum and the lazy upper-bound scale as `n³` — you can verify: real totals `10, 20, ...` for `n=3,4` roughly track `n³/6` (a known formula for this exact sum), and `n³/6` is still `Θ(n³)`.

## The precise version, if you want it exact

The real sum is:
```
Σ over all pairs (i,j) of (j-i-1) = Σ_{m=0}^{n} m × (number of pairs with that m)
```
Number of pairs with a given `m` is `(n+1-m)` (check: for n=3, m=0 → 4 pairs ✓, m=1 → 3 pairs ✓, m=2 → 2 pairs ✓, m=3 → 1 pair ✓). So:
```
Σ_{m=0}^{n} m(n+1-m)
```
Expanding and summing this (standard algebra, using `Σm = n(n+1)/2` and `Σm² = n(n+1)(2n+1)/6`) gives a cubic polynomial in `n` — its leading term is `n³/6`. Either way you compute it — the lazy multiply or the exact sum — **you land on `Θ(n³)`.** The multiply-by-n shortcut just gets you there faster, at the cost of a bigger (but still correct) constant.


Yes — exactly that, and it's worth being explicit that this is a deliberate simplification, not a claim that it's literally true for every cell.

## What's actually true vs. what we assume for the bound

**Actually true:** each cell `(i,j)` costs `m = j-i-1`, which ranges from `0` (many cells) up to `n` (exactly **one** cell — the outermost `(0, n+1)`).

**What we assume for the Big-O bound:** we pretend *every* cell costs the maximum possible, `n`, even though almost none of them actually do.

```
real cost per cell:     0, 0, 0, 0, 1, 1, 1, 2, 2, 3     (for n=3 — mostly small!)
assumed cost per cell:  n, n, n, n, n, n, n, n, n, n     (pretend all are worst-case)
```

## Why that's allowed

Big-O only asks: **"does this expression never exceed my bound?"** — not "is this bound tight for every piece?" Since no cell can *ever* cost more than `n` (m can't exceed n by definition), replacing every cell's true cost with `n` can only make the total **equal or bigger**, never smaller. That guarantees:

```
true total ≤ (number of cells) × n
```

which is a valid upper bound — just a loose one. And as I showed with the n=3 vs n=4 comparison, the looseness (the "3x" factor) doesn't grow with `n` — it stays roughly constant — so the *order of growth* (`n³`) is still correct, even though the exact number (`30` vs the true `10`) is inflated.

## The one-line summary

**Yes — for the Big-O bound, we assume worst case `n` for every cell, even though most cells are cheaper in reality.** That's a standard, deliberately imprecise move in Big-O analysis: replace a varying cost with its maximum possible value, because Big-O cares about "doesn't exceed this" — not "exactly equals this."

*/
 */