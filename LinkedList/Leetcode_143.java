package LinkedList;

public class Leetcode_143
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
        public void reorderList(ListNode head)
        {

    /*
    Approach :
    Step 1 : First we will find the middle of the Linked List using slow and fast pointer
    slow will move my 1 step and fast by 2 steps , why it works by the time slow reaches the
    full length slow has covered half length and we want the middle node
    Step 2 : We will reverse the node from middlw to that last node
    Step 3 : Now we ill have two separate linked list one reverse and the other half after the
    middle we will jsut connect them alterantely that we have question number 21 where
    we have merge two sorted list
    */
            ListNode slow = head;
            ListNode fast = head;

            while( fast.next!=null && fast.next.next!=null)
            {
                // ->  fast.next.next!=null -> These is for the even case the second case is to handle the odd case
                // -> we hve written the odd case before even case as if fast.next is already null then
                // null.next will give us error
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode head2 = slow.next;
            slow.next = null;
            //  reverseList(head2); after the list is return it is 3<---4 and initially head2 was pointing to
            // 3 before it was like these 3 --> 4 and after these thelist is reverse it is smoehting like
            // these 3<--4 but the head is still pointing to 3 which is incorrect now the new head is 4 not 3 so
            // correction
            head2 = reverseList(head2);

            head = mergeTwoLists(head,head2);
            // head == merge list is necessary as there are other functions whihc will check it from head
            // so atlast necessary to point to the original head other wise if we don't do it then it is not
            // good as we are passing the head in the function we will lose the track so again
            // after the fucntion returns dummy.next we are assigning it to the head again

        }


        public ListNode reverseList(ListNode head)
        {
            ListNode prev = null;
            ListNode curr = head;
            ListNode nx   = head;

            while( nx != null)
            {
                nx = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nx;

            }
            return prev;
        }
        public ListNode mergeTwoLists(ListNode list1, ListNode list2)
        {
            ListNode dummy = new ListNode(-1);
            ListNode tail = dummy;

            while (list1 != null && list2 != null) {

                tail.next  = list1;
                list1      = list1.next;
                tail       = tail.next;

                tail.next  = list2;
                list2      = list2.next;
                tail       = tail.next;

            }

            // attach whatever's left over (no more comparisons needed)
            tail.next = (list1 != null) ? list1 : list2;

            return dummy.next;
        }
    }
}
