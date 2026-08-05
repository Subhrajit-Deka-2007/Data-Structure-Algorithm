package DynamicProgramming;

import java.util.*;

public class Leetcode_139
{

    class Solution {
        public boolean wordBreak0(String s, List<String> wordDict) {
            // Brut Force
            Set<String> dict = new HashSet<>(wordDict);
            return solve0(s, 0, dict);
        }

        private boolean solve0(String s, int start, Set<String> dict) {
            if (start == s.length()) return true;

            for (int end = start + 1; end <= s.length(); end++) {
                String word = s.substring(start, end);
                if (dict.contains(word) && solve0(s, end, dict)) {
                    return true;
                }
            }
            return false;
        /*
        Time Complexity = O(2^N)
        Space Complexity = O(n) Recursive Stack
        */
        }

/*
Good — that's actually a really useful pattern to check, because for small `n` it can *look* like it matches, but it breaks apart fast. Let's verify it directly against the real numbers instead of just eyeballing.

## What you're describing

You're looking at the tree and seeing something like: each level seems to have roughly `n` nodes, and there are about `n` levels, so total ≈ `n × n = n²`. That's a very reasonable thing to see visually — let's actually count the tree level by level and check if that holds.

## Counting the actual tree, level by level (by depth, not by solve-index)

Using our `s = "aaaa"` tree (n=4), let's count **how many nodes exist at each depth** of the tree (depth = how many recursive calls deep you are, starting from `solve(0)` at depth 0):

```
Depth 0:  solve(0)
          → 1 node

Depth 1:  solve(1), solve(2), solve(3), solve(4)
          → 4 nodes

Depth 2:  (children of solve(1)): solve(2), solve(3), solve(4)
          (children of solve(2)): solve(3), solve(4)
          (children of solve(3)): solve(4)
          (solve(4) is a base case — no children)
          → 3 + 2 + 1 = 6 nodes

Depth 3:  (children of solve(1)→solve(2)): solve(3), solve(4)
          (children of solve(1)→solve(3)): solve(4)
          (children of solve(2)→solve(3)): solve(4)
          → 2 + 1 + 1 = 4 nodes

Depth 4:  (children of solve(1)→solve(2)→solve(3)): solve(4)
          → 1 node
```

## Total nodes by depth

```
Depth 0: 1
Depth 1: 4
Depth 2: 6
Depth 3: 4
Depth 4: 1
─────────────
Total:   16
```

## Here's the key thing to notice

`1, 4, 6, 4, 1` — **this is NOT a flat "n repeated n times" pattern.** It goes up, peaks in the middle, then comes back down. This shape is actually the **binomial coefficients** — `C(4,0), C(4,1), C(4,2), C(4,3), C(4,4)` — and their sum is always exactly `2ⁿ` (that's a well-known identity: the sum of one full row of Pascal's triangle is `2ⁿ`).

## Why "n+n+n+n" *looks* right for n=4 but is actually a coincidence

For `n=4`: your intuition `n×n = 4×4 = 16`. The real answer is also `16`. **They match — but only by coincidence**, because `4² = 16` and `2⁴ = 16` happen to be equal at this specific value of `n`. This is exactly the kind of trap that makes small examples misleading.

## Let's break the coincidence — try n=6

```
n² = 36
2ⁿ = 64
```

Already diverging. Let's go further:

```
n=8:  n² = 64      2ⁿ = 256
n=10: n² = 100     2ⁿ = 1,024
n=15: n² = 225     2ⁿ = 32,768
n=20: n² = 400     2ⁿ = 1,048,576
```

By `n=20`, `2ⁿ` is **over 2,600 times bigger** than `n²`. If this were really `O(n²)`, these two columns would stay proportionally close as `n` grows. They don't — the gap explodes.

## The actual visual signal to look for

Don't count "nodes per solve-index value" (that gave you the misleading `1,2,3,4` pattern earlier) — count **nodes per tree-depth-level**, like I just did (`1, 4, 6, 4, 1`). This shape — small, growing, peaking in the middle, shrinking back down, but with a total that keeps roughly doubling as `n` grows — is the real signature of `2ⁿ`. A genuinely `O(n²)` tree would look completely different: it'd be a **flat, narrow chain** — roughly constant width at every depth, not a bulge that grows explosively as `n` increases.

## Try this yourself as the real test

Extend this depth-by-depth count to `n=5` on paper (5 rows of Pascal's triangle: `1,5,10,10,5,1`, summing to `32 = 2⁵`), and compare that sum to `5² = 25`. You'll see the gap already opening up at just one step beyond where we were. That's the moment the "n+n+n" pattern reveals itself as a small-number coincidence rather than the real growth rate.

Good — let's nail down space complexity for the same brute-force recursive version.

## What counts as "space" here

For recursion, space complexity comes from the **call stack** — every active (not-yet-returned) function call sits on the stack, taking up memory, until it returns.

## How deep can the call stack go?

Look at the recursion: `solve(i)` calls `solve(end)` where `end > i`. The stack only grows when you go **deeper** into one continuous chain of calls — it does *not* grow from branching sideways (siblings don't stack on top of each other; they run one after another, and each one's stack frame is popped before the next sibling starts).

The deepest possible chain is when you take the smallest possible step forward every time — i.e., `end = start + 1` at every call:

```
solve(0) → solve(1) → solve(2) → solve(3) → ... → solve(n)
```

That's `n+1` frames deep in the worst case (one call per character, in the worst path).

## Why branching doesn't add to space (this is the key insight)

Even though the **tree** has exponentially many total nodes (2ⁿ), at any given **moment in time**, only one root-to-current-node path is actually sitting on the call stack. Once a branch finishes and returns, its stack frame is popped before the next branch even starts. So the *total* number of nodes in the tree (which determines time complexity) is completely different from the *maximum depth* of any single path (which determines space complexity).

## Answer

**Space complexity: O(n)**

This comes purely from the maximum recursion depth — each stack frame holds a small constant amount of data (`start` index, loop variable, etc.), and the deepest possible chain of nested calls is `n` frames.

## Quick comparison across all the versions we covered

| Approach | Time | Space |
|---|---|---|
| Brute-force recursion | O(2ⁿ) | **O(n)** — call stack only |
| Memoization | O(n²) | O(n) — call stack + memo array (both O(n), so still O(n) overall) |
| Tabulation | O(n²) | O(n) — just the `dp` array, no call stack |

Good pattern to notice: even though brute-force recursion is catastrophically slow in **time**, it's not catastrophic in **space** — it stays linear the whole way through, because depth (not breadth) is what determines stack usage, and depth never exceeds `n`.
*/


        public boolean wordBreak1(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            Boolean[] memo = new Boolean[s.length()]; // memo[i] = answer for solve(i), or null if not yet computed
            return solve(s, 0, dict, memo);
        }

        private boolean solve(String s, int start, Set<String> dict, Boolean[] memo) {
            if (start == s.length()) return true;

            if (memo[start] != null) {
                return memo[start]; // already computed — instant return, no re-exploration
            }

            for (int end = start + 1; end <= s.length(); end++) {
                String word = s.substring(start, end);
                if (dict.contains(word) && solve(s, end, dict, memo)) {
                    memo[start] = true;
                    return true;
                }
            }

            memo[start] = false;
            return false;
        /*
        Time Complexity = O( N-1 + N-2 + N-3 + ---- + 1 )=> ~O(N^2)
        Space Complexity = O(N + N (REUSRSOVE STACK ))

        solve(0): tried end=1,2,3,4 → 4 iterations
solve(1): tried end=2,3,4   → 3 iterations
solve(2): tried end=3,4     → 2 iterations
solve(3): tried end=4       → 1 iteration
        */
        }




        public boolean wordBreak2(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            int n = s.length();
            boolean[] dp = new boolean[n + 1]; // dp[i] = "can s[i..n-1] be segmented?"
            dp[n] = true; // base case: empty remaining string is trivially segmentable

            for (int start = n - 1; start >= 0; start--) {
                for (int end = start + 1; end <= n; end++) {
                    String word = s.substring(start, end);
                    if (dict.contains(word) && dp[end]) {
                        dp[start] = true;
                        break;
                    }
                }
            }

            return dp[0];
        }
/*
    Complexity
Time: O(n²) — same derivation as before: n values of start, up to n values of end each.
Space: O(n) — just the dp array. No recursion call stack at all, which is the main advantage over the memoized version — same time complexity, but strictly better space behavior in practice (no stack frame overhead, no risk of stack overflow on very long strings).



Let's build the tabulation (bottom-up) version, converting the exact same recurrence directly into a loop.

## The core idea

Memoization computes `solve(start)` on-demand, top-down, diving in from `start=0` and working backward through recursive calls until it hits the base case, then bubbling answers back up.

Tabulation flips this: **start from the base case directly, and build forward**, filling in `dp[i]` for every `i` from the end of the string back toward the beginning — no recursive calls, no call stack, just a loop.

## The code

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1]; // dp[i] = "can s[i..n-1] be segmented?"
        dp[n] = true; // base case: empty remaining string is trivially segmentable

        for (int start = n - 1; start >= 0; start--) {
            for (int end = start + 1; end <= n; end++) {
                String word = s.substring(start, end);
                if (dict.contains(word) && dp[end]) {
                    dp[start] = true;
                    break;
                }
            }
        }

        return dp[0];
    }
}
```

## Line-by-line

```java
boolean[] dp = new boolean[n + 1];
```
Same shape as the `memo` array from before — one slot per possible `start` value, from `0` to `n`. `dp[i]` will mean exactly what `memo[i]` meant: "can the string starting at index `i` be fully segmented?"

```java
dp[n] = true;
```
This is the tabulated version of the base case. In recursion, we checked `if (start == s.length()) return true` **every time** we entered a call. In tabulation, we just set this **once**, up front, since it's always true and never needs re-checking.

```java
for (int start = n - 1; start >= 0; start--) {
```
Here's the direction flip that makes this "bottom-up": we start from `n-1` (closest to the end) and walk **backward** toward `0`. This ordering matters — by the time we compute `dp[start]`, we need `dp[end]` for every `end > start` to already be filled in, and walking right-to-left guarantees that.

```java
    for (int end = start + 1; end <= n; end++) {
        String word = s.substring(start, end);
        if (dict.contains(word) && dp[end]) {
            dp[start] = true;
            break;
        }
    }
```
Same inner loop as before — try every possible "next word" cut. But instead of recursively calling `solve(end)`, we just **look up** `dp[end]`, which was already computed on an earlier iteration of the outer loop (since `end > start`, and we're going backward). This lookup is O(1) — no function call overhead, no stack.

```java
return dp[0];
```
`dp[0]` answers "can the entire string, starting from index 0, be segmented?" — which is exactly the original question.

## Trace on `s = "aaab"`, `wordDict = ["a", "aa", "aaa"]`

```
dp[4] = true (base case)

start=3: try end=4, word="b" → not in dict → loop ends, dp[3] stays false

start=2: try end=3, word="a" → in dict, but dp[3]=false → no match
         try end=4, word="ab" → not in dict
         → dp[2] stays false

start=1: try end=2, word="a" → in dict, but dp[2]=false → no match
         try end=3, word="aa" → in dict, but dp[3]=false → no match
         try end=4, word="aab" → not in dict
         → dp[1] stays false

start=0: try end=1, word="a" → in dict, but dp[1]=false → no match
         try end=2, word="aa" → in dict, but dp[2]=false → no match
         try end=3, word="aaa" → in dict, but dp[3]=false → no match
         try end=4, word="aaab" → not in dict
         → dp[0] stays false

return dp[0] = false ✓
```

Matches what we got with recursion and memoization — good consistency check.

## Trace on the successful case: `s = "leetcode"`, `wordDict = ["leet", "code"]`

```
n = 8
dp[8] = true (base case)

start=7: try end=8, word="e" → not in dict → dp[7] = false
start=6: try end=7, word="d"  → not in dict
         try end=8, word="de" → not in dict → dp[6] = false
start=5: word="o" → no; word="od" → no; word="ode" → no → dp[5] = false
start=4: try end=8, word="code" → IN DICT, and dp[8]=true → dp[4] = true ✓
start=3: word="e" → no ... (all fail) → dp[3] = false
start=2: (all fail) → dp[2] = false
start=1: (all fail) → dp[1] = false
start=0: try end=4, word="leet" → IN DICT, and dp[4]=true → dp[0] = true ✓

return dp[0] = true ✓
```

Notice the key moment: at `start=0`, we check `dict.contains("leet") && dp[4]`. We don't need to recursively dive into anything — `dp[4]` was **already sitting there, computed and ready**, because we processed `start=4` earlier (walking right to left).

## Complexity

- **Time: O(n²)** — same derivation as before: `n` values of `start`, up to `n` values of `end` each.
- **Space: O(n)** — just the `dp` array. **No recursion call stack at all**, which is the main advantage over the memoized version — same time complexity, but strictly better space behavior in practice (no stack frame overhead, no risk of stack overflow on very long strings).

## Side-by-side with memoization — what actually changed

| | Memoization | Tabulation |
|---|---|---|
| Direction | Top-down (start at 0, recurse toward the end) | Bottom-up (start at the end, loop toward 0) |
| Base case | Checked every call (`if start==n`) | Set once, upfront (`dp[n]=true`) |
| Getting a "sub-answer" | Recursive call `solve(end)` | Array lookup `dp[end]` |
| Call stack | Yes, up to O(n) deep | None |

**The core recurrence — "can I reach the end using a valid word starting from here, by combining with something I already know works further along" — is identical in both.** Tabulation just computes it in the opposite order and stores answers in an array instead of relying on the call stack to hold them temporarily.

Want to trace one more example where you predict `dp[start]` values yourself before I confirm, to check the bottom-up direction has clicked?
*/



        public boolean wordBreak3(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            int n = s.length();
            boolean[] visited = new boolean[n + 1];
            Queue<Integer> queue = new LinkedList<>();

            queue.offer(0);
            visited[0] = true;

            while (!queue.isEmpty()) {
                int start = queue.poll();

                if (start == n) {
                    return true;
                }

                for (int end = start + 1; end <= n; end++) {
                    if (!visited[end] && dict.contains(s.substring(start, end))) {
                        visited[end] = true;
                        queue.offer(end);
                    }
                }
            }

            return false;
        }
    /*
    Time Complexity = O(N^2)
    Space Complexity = O(N)


    queue = [0], visited = {0}

Pop 0. Not n(4).
  end=1 "a" → matches, visited[1]=true, queue=[1]
  end=2 "aa" → matches, visited[2]=true, queue=[1,2]
  end=3 "aaa" → matches, visited[3]=true, queue=[1,2,3]
  end=4 "aaab" → not in dict

Pop 1. Not n(4).
  end=2 "a" → matches, but visited[2] already true → SKIP
  end=3 "aa" → matches, but visited[3] already true → SKIP
  end=4 "aab" → not in dict

Pop 2. Not n(4).
  end=3 "a" → matches, but visited[3] already true → SKIP
  end=4 "ab" → not in dict

Pop 3. Not n(4).
  end=4 "b" → not in dict

Queue empty → return false ✓


Complexity
Time: O(n²) — same reasoning as before: n nodes, each one (when first visited) scans up to n possible end values.
Space: O(n) — the visited array plus the queue, both bounded by n.
    */







            class TrieNode {
                TrieNode[] children = new TrieNode[26];
                boolean isWord = false;
            }

            private TrieNode root = new TrieNode();

            private void insert(String word) {
                TrieNode node = root;
                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    if (node.children[idx] == null) {
                        node.children[idx] = new TrieNode();
                    }
                    node = node.children[idx];
                }
                node.isWord = true;
            }

            public boolean wordBreak4(String s, List<String> wordDict) {
                // Step 1: build the Trie from the dictionary
                for (String word : wordDict) {
                    insert(word);
                }

                int n = s.length();
                boolean[] dp = new boolean[n + 1];
                dp[0] = true; // empty prefix is trivially "breakable"

                // Step 2: fill dp[] left to right
                for (int i = 0; i < n; i++) {
                    if (!dp[i]) continue; // s[0..i) isn't breakable, so no point starting a word here

                    TrieNode node = root;
                    for (int j = i; j < n; j++) {
                        int idx = s.charAt(j) - 'a';
                        if (node.children[idx] == null) break; // no dictionary word continues this way
                        node = node.children[idx];
                        if (node.isWord) {
                            dp[j + 1] = true; // s[i..j] is a valid word, so s[0..j+1) is breakable
                        }
                    }
                }

                return dp[n];
            }
        }

/*
Great question. Let's break this down piece by piece — building the Trie, then the main DP loop, then compare to the naive approach to see why it's faster.

Setting up notation
n = length of s
w = number of words in wordDict
L = length of the longest word in wordDict
W = total characters across all words in wordDict (sum of lengths)
Time complexity — building the Trie
java
for (String word : wordDict) {
    insert(word);
}

insert walks through each character of word once. So inserting one word of length k costs O(k). Summed over all words:

Trie construction = O(W) — just proportional to total dictionary size.

Time complexity — the main DP loop

This is the part people get confused about. Look at the inner loop again:

java
for (int i = 0; i < n; i++) {
    if (!dp[i]) continue;
    TrieNode node = root;
    for (int j = i; j < n; j++) {
        int idx = s.charAt(j) - 'a';
        if (node.children[idx] == null) break;   // <-- this is the key line
        node = node.children[idx];
        if (node.isWord) dp[j + 1] = true;
    }
}

Key insight: the inner loop can never run longer than L (the length of the longest dictionary word) — because the Trie itself only has depth L. Once you've walked L characters deep, you're at a leaf with no children, so node.children[idx] == null is guaranteed to be true, and you break.

So for every value of i, the inner loop does at most L steps of work — not up to n steps.

Outer loop runs n times → each iteration costs at most O(L) → total:

Main loop = O(n × L)

Total complexity
Time:  O(W + n·L)
Space: O(W · 26)  for the Trie   +   O(n) for dp[]
     = O(26W + n)
*/



    }



