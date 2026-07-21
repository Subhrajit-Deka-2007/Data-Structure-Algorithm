package LinkedList;

import java.util.PriorityQueue;

public class Leetcode_23
{

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {

        /**
         public ListNode mergeKLists(ListNode[] lists)
         {
         if( lists.length == 0 ) return null;
         ListNode l1 =lists[0];
         for ( int i = 1 ; i < lists.length ; i++ )
         {
         ListNode l2 = lists[i];
         l1 = mergeTwoLists(l1,l2);
         }
         return l1;
         */





     /*
     Time Complexity : If there are N nodes and each nodes of N size then
     Time Complexity will be N-1=> pairs then merging each pair = o(2n)
     T.C = O ( (N-1)*(2N))
     S.C = O (1)
     We are not using extra space




     Let's build this up carefully, step by step, using real numbers first, then generalizing.

## Setup

**You have `K` linked lists. Let's say, for simplicity, EACH list has the SAME length `n`. Total nodes across all lists: `N = K × n`.**

## Step 1 — list out EVERY merge operation, and its cost

**Merge 1:** combining list1 (size `n`) and list2 (size `n`) → **combined size after this merge: `2n`**. **Cost of THIS merge (using your `mergeTwoLists`, which is O(size of both lists combined)): `O(n + n) = O(2n)`.**

**Merge 2:** combining the PREVIOUS result (size `2n`) with list3 (size `n`) → **combined size after: `3n`**. **Cost of this merge: `O(2n + n) = O(3n)`.**

**Merge 3:** combining the previous result (size `3n`) with list4 (size `n`) → **combined size after: `4n`**. **Cost: `O(3n + n) = O(4n)`.**

**...continuing this pattern...**

**Merge (K-1):** combining the previous result (size `(K-1)n`) with the LAST list (size `n`) → **Cost: `O((K-1)n + n) = O(Kn)`.**

## Step 2 — add up ALL these individual costs

```
Total = O(2n) + O(3n) + O(4n) + ... + O(Kn)
```

**Let's factor out the `n` (since it's common to every term):**
```
Total = n × (2 + 3 + 4 + ... + K)
```

## Step 3 — solve the sum `2 + 3 + 4 + ... + K`

**You've done this exact type of sum before (Quick Sort's worst case was `1+2+...+n`).** Let's derive it the same way:

```
2 + 3 + 4 + ... + K = (1 + 2 + 3 + ... + K) - 1
                     = K(K+1)/2 - 1
```

**For Big-O purposes, we only care about the DOMINANT term as K grows large — and `K(K+1)/2` simplifies to approximately `K²/2` for large K (dropping lower-order terms and the `-1`).**

```
2 + 3 + ... + K ≈ K²/2
```

## Step 4 — substitute back

```
Total = n × (K²/2)
```

**Dropping the constant `1/2` (standard Big-O practice):**
```
Total = O(n × K²)
```

## Step 5 — express this in terms of `N` (total nodes) instead of `n` (per-list size)

**Since `N = K × n`, we can solve for `n`:**
```
n = N / K
```

**Substitute this into our formula:**
```
Total = O((N/K) × K²)
      = O(N × K)
```

## Final Answer

```
Time Complexity: O(N × K)
```

where:
- **N** = total number of nodes across ALL lists combined
- **K** = number of lists

## Quick verification with real numbers

```
K = 4 lists, each with n = 3 nodes → N = 12 total nodes

Using our formula: O(N × K) = O(12 × 4) = O(48)

Let's verify by listing the actual merges:
Merge1: 2n=6 cost
Merge2: 3n=9 cost
Merge3: 4n=12 cost
Total: 6+9+12 = 27

Hmm, 27 vs our formula's 48 — let's check why there's a gap.
```

**The gap is because Big-O ignores constants — our derivation dropped the `1/2` factor and the `-1` term along the way, which is standard practice (Big-O describes GROWTH RATE, not the exact count) — but it's worth confirming the growth PATTERN matches, not the exact numbers.**

## Let's verify the growth PATTERN matches, with a bigger K, to confirm the relationship holds

```
If K=8 (double K), n stays 3, N=24 (double N):
Actual: merges would be 2n,3n,4n,5n,6n,7n,8n → sum = n(2+3+4+5+6+7+8) = 3×35=105

Our formula: O(N×K) = O(24×8)=192

Ratio check: when K doubled (4→8) and N doubled (12→24),
actual cost went from 27→105 (roughly 4x)
formula's O(N×K) went from 48→192 (exactly 4x)

Both show the SAME roughly-4x growth pattern when K and N both double — confirming O(N×K) correctly captures the GROWTH RATE, even though exact constants differ.
```

## Summary

**Your solution's time complexity is `O(N × K)`** — derived by summing up each successive merge's cost (which grows linearly as the combined list gets bigger), using the same arithmetic series technique you've used before (Quick Sort, log(k!) for heap extraction). **This is different from `O(N²)`, though it CAN equal `O(N²)` in the specific worst case where `K` is as large as `N` itself (many tiny lists).**





     */


        public ListNode mergeTwoLists(ListNode list1, ListNode list2)
        {
            ListNode dummy = new ListNode(-1); // one throwaway placeholder node — NOT part of the final data
            ListNode tail = dummy;

            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    tail.next = list1;   // redirect tail's pointer to the EXISTING list1 node
                    list1 = list1.next;   // move list1 forward to ITS next node
                } else {
                    tail.next = list2;   // redirect tail's pointer to the EXISTING list2 node
                    list2 = list2.next;
                }
                tail = tail.next; // move tail forward to whichever node we just attached
            }

            // attach whatever's left over (no more comparisons needed)
            tail.next = (list1 != null) ? list1 : list2;

            return dummy.next;
        }


        /* 2nd Approach */
        public ListNode mergeKLists(ListNode[] lists) {
            PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (ListNode list : lists) {
                if (list != null) {
                    heap.offer(list);
                }
            }

            ListNode dummy = new ListNode(-1);
            ListNode tail = dummy;

            while (!heap.isEmpty()) {
                // Initially we are putting all the head node nodes of the k lists and each time in the min heap there
                // is only k elements
                ListNode smallest = heap.poll();
                tail.next = smallest;
                tail = tail.next;

                if (smallest.next != null)
                {
                    heap.offer(smallest.next);
                }
            }

            return dummy.next;
/*
Time: O(N log K)
Space: O(K) the heap never holds more than K elements at once
Where N is the total number of nodes
*/
        }

    }


}
