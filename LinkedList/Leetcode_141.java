package LinkedList;

public class Leetcode_141
{
    /**
     * Definition for singly-linked list.
     * class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) {
     *         val = x;
     *         next = null;
     *     }
     * }
     */
    public class Solution {
        public boolean hasCycle(ListNode head)
        {
            ListNode  sl = head;
            ListNode  fs = head;

            if ( fs!=null&&fs.next != null ) fs = fs.next.next;

            while(fs!=null&&fs.next!=null&&fs.next.next!=null)
            {
                if( sl == fs ) return true;
                sl = sl.next;
                fs = fs.next.next;
            }
            return false;
      /*
      Time Complexity = O (N)
      Space Complexity = O (1)
      */
        }
    /*
    Better then my appraoch same time complexity but base case get's handle automatically

     public boolean hasCycle(ListNode head) {
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null && fast.next!=null)
        {
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast){
                return true;
            }

        }return false;
    }
    */
    }
}
