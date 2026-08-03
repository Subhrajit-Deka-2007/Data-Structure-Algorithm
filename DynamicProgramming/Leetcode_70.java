package DynamicProgramming;

public class Leetcode_70
{

    class Solution {
        public int climbStairs(int n)
        {
            // On each turn we have two ways to climb ;

            int [] dp = new int [n+1];
            return ways1(n );
       /*
       Time Complexity = O(N)
       Space Complexity = O(n + n )

       Earlier Solution Time Complexity was = O( 2 + 2^2 + 2^3 + ---------------+ 2^n )
       Space Complexity = log n or i can directly say n as the value of each noe here is not different  if the recurse tree
       */
        }

        public int ways0( int n, int [] dp )
        {
        /* Solve using memoization : Top - Down DP
        Top-down (memoization): write the natural recursive solution first, then just add a cache (HashMap or array) to store results you've already computed — check the cache before recursing, skip if it's already there
        */
            if( n < 0 ) return 0;
            else if( n == 0 )return 1;
            else if( dp[n]!= 0 )return dp[n];
            else return dp[n] = ways0(n-1,dp) + ways0(n-2,dp);
        }

        public int ways1( int n )
        {
        /*
         Solve Using : Bottom Up
         Bottom-up (tabulation): build a table starting from the smallest base cases, and iteratively work UP to the answer you actually want — no recursion at all.
         */

            int zero = 1;
            int  one = 1;

            for ( int i = 2 ; i <= n ; i++ )
            {
                one  = one + zero;
                zero = one - zero;
            }
            return one;
        }
/*
Time Complexity = O(N-2)
Space Complexity =O(1)
*/
    }
/*
Let's derive this from counting nodes level by level, since that's where `log N` actually comes from.

## Setup — how many nodes exist at each level of a binary tree

In a binary tree, each node can have at most 2 children. So the number of nodes **can double** at every level.

```
Level 0 (root):        1 node
Level 1:                2 nodes
Level 2:                4 nodes
Level 3:                8 nodes
...
Level h:                2^h nodes
```

**The pattern: level `k` can hold at most `2^k` nodes.**

## Total nodes in a tree of height `h`

If every level is completely full (a **perfect** binary tree), the total number of nodes is the sum across all levels:

```
N = 2^0 + 2^1 + 2^2 + ... + 2^h
```

This is the same geometric series shape you've seen before — and it simplifies to:

```
N = 2^(h+1) - 1
```

## Solving for `h` — this is where log comes in

We want to know: given `N` total nodes, how tall (`h`) must the tree be?

```
N ≈ 2^(h+1)
```

(dropping the `-1`, since for Big-O purposes constants don't matter)

**To undo an exponent, you take a logarithm** — specifically, base 2, since that's the base we're working with:

```
log₂(N) ≈ h + 1
h ≈ log₂(N) - 1
```

**Dropping the constant `-1` (again, doesn't matter for Big-O):**

```
h = O(log N)
```

## Why this matters — the intuition, not just the algebra

Every time you add one more level to a **balanced** binary tree, you **double** the number of nodes it can hold. So the question "how many levels do I need to fit N nodes" is really asking "how many times do I need to double 1 to reach N" — and that's exactly what a logarithm measures.

```
Doubling: 1 → 2 → 4 → 8 → 16 → 32 → 64 → 128 → ...
Number of doublings to reach N: log₂(N)
```

## Quick sanity check with real numbers

```
N = 8 nodes (perfect tree): height should be log₂(8) = 3... let's verify
Level 0: 1, Level 1: 2, Level 2: 4 → total so far 1+2+4=7, need 1 more → Level 3 has that 1 node
Height = 3 ✓ matches log₂(8)=3
```

## The important caveat — this ONLY holds for a *balanced* tree

This is exactly the distinction we built way back with skewed vs. balanced trees. If the tree is **skewed** (each node has only 1 child, degenerating into a linked-list shape), then every level holds just 1 node — no doubling at all — and height becomes `O(N)`, not `O(log N)`.

```
Balanced tree: height = O(log N)  — this is why BST search, heap operations, etc. are O(log N)
Skewed tree:    height = O(N)      — the worst case that balanced structures (Red-Black trees, AVL) exist to prevent
```

This is exactly why heap operations, balanced BST operations (like Java's `TreeMap`), and binary search all rely on trees staying roughly balanced — the `O(log N)` guarantee only holds when the tree doesn't degenerate into something linear.
*/
}
