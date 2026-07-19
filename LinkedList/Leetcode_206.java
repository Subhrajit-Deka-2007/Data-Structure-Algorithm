package LinkedList;


 class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class Leetcode_206
{

        public ListNode reverseList1(ListNode head)
        {
            ListNode temp = head;
            ListNode t = null;
            ListNode n = null ;
            while ( temp != null)
            {
                n = new ListNode(temp.val);
                n.next = t;
                t = n;
                temp = temp.next;
            }
            return n;
        /*
        Time Complexity = O (N)
        Space Complexity = O (N)
        */
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

    /*
    Approach 2 : Inplace
    Time Complexity : O (N)
    Space Complexity : O (1)
    */
        }
    }

