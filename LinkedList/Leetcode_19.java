package LinkedList;

public class Leetcode_19
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
        public ListNode removeNthFromEnd(ListNode head, int n)
        {
            ListNode temp = head;
            int count = 0;
            int len   = 0;
            while( temp != null )
            {
                len++;
                temp = temp.next;
            }

            temp = head;
            while( count < len - n   )
            {
                if( count == len-n-1)
                {
                    temp.next = temp.next.next;
                    return head;
                }
                count++;
                temp = temp.next;
            }
            if(count == 0 ) return head.next;
            //If count is zero it mean we are trying to temove the 0th index so we are just returning the next index
            // statisfy both cases [1] , n = 1 and [1,2] n = 2
            return null;

      /*
      Time Complexity = O (N)
      Space Compleixty = O (1)
      */
        }
    }
}
