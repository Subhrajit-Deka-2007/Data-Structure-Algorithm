package LinkedList;

public class Leetcode_21
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
        //public ListNode mergeTwoLists(ListNode list1, ListNode list2)
        // {

        //   /*
        //   Brut force make an extra new Linked List
        //   Time Complexity = O ( M + N )
        //   Space Complexity = O ( M + N )
        //   */
        // }
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
    /*
    Time Complexity = O ( M + N )
    Space Complexity = O (1)
    */
        }
    }
}
