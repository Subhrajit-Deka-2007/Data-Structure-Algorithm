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
    /*Brut force we are finding the length and after that we are traversing till length - n -1
    and then removing it

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
     /*
     Count == 0 means the loop is never executed and it true  when we are trying to remove
     an element present at the nth index from last that means t,he first element or the 0th index  .
     So, we are just returning head.next , the element behind it or nul if there is only one element
     in the linked list

      return null;
      */

      /*
      Time Complexity = O (N)
      Space Compleixty = O (1)
      */

      /*
      Optimize appraoch : Uisng two pointer
      and the gap between the two pointer will be n
      */

            ListNode  p1 = head;
            ListNode  p2 = head;

            while(n-->0)p2 = p2.next;
            if(p2 == null ) return head.next;
    /*
    p2 == null means we are trying to remove the nth element means the first element
    */
            while(p2.next!=null)
            {
                p1 = p1.next;
                p2 = p2.next;
            }
            p1.next = p1.next.next;
            return head;
        }
    }
}
