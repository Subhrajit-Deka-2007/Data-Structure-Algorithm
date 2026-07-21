package LinkedList;

public class Leetcode_25
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
        public ListNode reverseKGroup(ListNode head, int k)
        {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode groupPrev = dummy;

            while (true) {
                ListNode kth = groupPrev;
                for (int i = 0; i < k; i++) {
                    kth = kth.next;
                    if (kth == null) return dummy.next;
                }

                ListNode groupNext = kth.next;
                ListNode prev = groupNext;
                ListNode curr = groupPrev.next;

                while (curr != groupNext) {
                    ListNode tmp = curr.next;
                    curr.next = prev;
                    prev = curr;
                    curr = tmp;
                }

                ListNode tmp = groupPrev.next;
                groupPrev.next = prev;
                groupPrev = tmp;
            }
        }




/*
Time: O(n) — every node is touched a constant number of times
Space: O(1) — TRUE in-place, no recursion stack, just a handful of pointer variables
*/



        public ListNode reverseKGroup1(ListNode head, int k) {
            ListNode node = head;
            int count = 0;

            while (count < k) {
                if (node == null) return head;
                node = node.next;
                count++;
            }

            ListNode prev = null;
            ListNode curr = head;

            for (int i = 0; i < k; i++) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            head.next = reverseKGroup(node, k);

            return prev;
        }

/**
 Time: O(n) — same as iterative, every node processed a constant number of times
 Space: O(n/k) — the recursion call stack depth (roughly one stack frame per group)
 */


    }
}
