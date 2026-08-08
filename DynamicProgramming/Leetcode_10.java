package DynamicProgramming;

public class Leetcode_10
{
    class Solution
    {
        public boolean isMatch0(String s, String p) {
            return solve(s, p, 0, 0);
        }

        private boolean solve(String s, String p, int i, int j) {
            // Base case: pattern exhausted — valid only if s is ALSO exhausted
            if (j == p.length()) {
                return i == s.length();
            }

            // Does the character right here even count as a match?
            boolean firstMatch = (i < s.length()) &&
                    (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                // p[j] is repeatable
                return solve(s, p, i, j + 2)                      // Option A: use zero times
                        || (firstMatch && solve(s, p, i + 1, j));      // Option B: use one more time
            } else {
                // p[j] is a one-shot character, forced match
                return firstMatch && solve(s, p, i + 1, j + 1);
            }
    /*
    Time Complexity = O( 2^(M+N))
    Space Complexity = O(M+N)=> Recursive Stack
    */
        }



        public boolean isMatch1(String s, String p) {
            // Memo Table
            int m = s.length(), n = p.length();
            // memo[i][j]: 0 = not computed, 1 = true, 2 = false
            int[][] memo = new int[m + 1][n + 1];
            return solve(s, p, 0, 0, memo);
        }

        private boolean solve(String s, String p, int i, int j, int[][] memo) {
            if (memo[i][j] != 0) {
                return memo[i][j] == 1;
            }

            boolean result;

            if (j == p.length()) {
                result = (i == s.length());
            } else {
                boolean firstMatch = (i < s.length()) &&
                        (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    result = solve(s, p, i, j + 2, memo)
                            || (firstMatch && solve(s, p, i + 1, j, memo));
                } else {
                    result = firstMatch && solve(s, p, i + 1, j + 1, memo);
                }
            }

            memo[i][j] = result ? 1 : 2;
            return result;
/*
Total time = (number of distinct states) × (work per state)
           = O(m×n) × O(1)
           = O(m×n)
Space Complexity = Space Complexity
Memo table: O(m × n) cells.
Recursion stack: bounded by the depth we derived earlier, O(m+n).

Since m×n dominates m+n for any reasonably-sized inputs:

S(m,n)=O(m×n)
*/
        }


        public boolean isMatch3(String s, String p) {
            int m = s.length(), n = p.length();
            boolean[][] dp = new boolean[m + 1][n + 1];

            // Base case: empty s matches empty p
            dp[m][n] = true;

            // Fill from bottom-right corner backward to top-left
            for (int i = m; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    boolean firstMatch = (i < m) &&
                            (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

                    if (j + 1 < n && p.charAt(j + 1) == '*') {
                        dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
                    } else {
                        dp[i][j] = firstMatch && dp[i + 1][j + 1];
                    }
                }
            }

            return dp[0][0];
        }
    }
/*
# LC 10: Regular Expression Matching — Complete Summary

## The Problem

Given string `s` and pattern `p` (which may contain `.` and `*`), determine if `p` matches the **entire** `s`.
- `.` matches any single character.
- `*` means "zero or more of the character immediately preceding it in the pattern."

## The Core Idea

Track two pointers: `i` into `s`, `j` into `p`. Define:

```
solve(i, j) = does s[i:] (rest of s) match p[j:] (rest of p)?
```

We want `solve(0, 0)`.

**At each state, look at `p[j]`:**

- **If `p[j]` is followed by `*` (repeatable):** two choices exist, try both, succeed if either works:
  - **Option A — use it zero times:** skip past the character and `*` entirely. `j` jumps forward by 2, `i` stays put. No matching check needed (using it zero times means nothing needs to align).
  - **Option B — use it one more time:** only valid if the current `s` character actually matches `p[j]` (`firstMatch` must be true). `i` moves forward by 1, but `j` **stays the same** (so the `*` can be reused again next round).

- **If `p[j]` is NOT followed by `*` (plain character):** no choice — it must match `s[i]` exactly right now, and both pointers move forward together by 1.

**Base case:** if `j` reaches the end of `p`, it's only a true match if `i` has *also* reached the end of `s` at the same moment. (We never base-case on `s` running out alone, because leftover pattern like `x*` can still validly match zero characters.)

## Recursive Solution

```java
public boolean isMatch(String s, String p) {
    return solve(s, p, 0, 0);
}

private boolean solve(String s, String p, int i, int j) {
    if (j == p.length()) {
        return i == s.length();
    }

    boolean firstMatch = (i < s.length()) &&
                          (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

    if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
        return solve(s, p, i, j + 2)                     // Option A: zero uses
            || (firstMatch && solve(s, p, i + 1, j));     // Option B: one more use
    } else {
        return firstMatch && solve(s, p, i + 1, j + 1);
    }
}
```

**Time: `O(2^(m+n))`** — worst case, every call branches into 2 (Option A / Option B). Max depth is `O(m+n)`, because every move (Option A: j+=2, Option B: i+=1, plain: i+=1,j+=1) drops the combined "remaining work" `(m-i)+(n-j)` by at least 1, and that budget starts at `m+n`. A binary tree of depth `d` has `O(2^d)` total nodes.

**Space: `O(m+n)`** — the call stack only ever holds one root-to-current path at a time (not the whole tree), and that path length is bounded by the same `m+n` depth argument.

## Why Memoize

The same `(i,j)` pair can be reached via multiple different decision sequences (e.g., different splits of how many characters earlier `*` blocks consumed) — classic overlapping subproblems, fully described by just `(i,j)`.

## Memoization (Top-Down)

```java
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    int[][] memo = new int[m + 1][n + 1];   // 0=unset, 1=true, 2=false
    return solve(s, p, 0, 0, memo);
}

private boolean solve(String s, String p, int i, int j, int[][] memo) {
    if (memo[i][j] != 0) return memo[i][j] == 1;

    boolean result;
    if (j == p.length()) {
        result = (i == s.length());
    } else {
        boolean firstMatch = (i < s.length()) &&
                              (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            result = solve(s, p, i, j + 2, memo)
                   || (firstMatch && solve(s, p, i + 1, j, memo));
        } else {
            result = firstMatch && solve(s, p, i + 1, j + 1, memo);
        }
    }
    memo[i][j] = result ? 1 : 2;
    return result;
}
```

**Time: `O(m×n)`** = (number of distinct `(i,j)` states) × (work per state). Unlike LC 312, there's no inner loop here — each state does only `O(1)` work (a fixed handful of comparisons), so it's `O(m×n) × O(1) = O(m×n)`, not cubed.

**Space: `O(m×n)`** for the memo table (dominates the `O(m+n)` recursion stack).

## Tabulation (Bottom-Up)

Since `dp[i][j]` depends only on cells with **larger** `i` or `j` (`dp[i][j+2]`, `dp[i+1][j]`, `dp[i+1][j+1]`), we fill the table **backward** — from `i=m, j=n` down to `i=0, j=0`.

```java
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[m][n] = true;   // base case: both strings fully consumed together

    for (int i = m; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--) {
            boolean firstMatch = (i < m) &&
                                  (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
            if (j + 1 < n && p.charAt(j + 1) == '*') {
                dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
            } else {
                dp[i][j] = firstMatch && dp[i + 1][j + 1];
            }
        }
    }
    return dp[0][0];
}
```

**Time: `O(m×n)`** — two nested loops, `O(1)` work per cell.
**Space: `O(m×n)`** — just the table, no recursion stack.

## Full comparison table

| Approach | Time | Space | Key reason |
|---|---|---|---|
| Brute-force recursion | O(2^(m+n)) | O(m+n) | no caching, overlapping (i,j) states recomputed |
| Memoization | O(m×n) | O(m×n) | O(m×n) states, O(1) work each (no inner loop) |
| Tabulation | O(m×n) | O(m×n) | same states, filled backward due to dependency direction |

**Key contrast with LC 312 (Burst Balloons):** there, each state's own computation involved a `for` loop over a third variable `k`, making per-state work `O(n)` and total `O(n³)`. Here, there's no such inner loop — each state resolves in `O(1)` via at most 2 direct recursive/lookup calls — so the complexity stays at `O(m×n)`, one power lower.
*/
}
