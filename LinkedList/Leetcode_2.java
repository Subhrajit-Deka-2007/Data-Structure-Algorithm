package LinkedList;
public class Leetcode_2 {

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {


            return addTwoList(l1, l2);

        }

        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            ListNode nx = head;

            while (nx != null) {
                nx = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nx;

            }
            return prev;
        }

        public ListNode addTwoList(ListNode l1, ListNode l2) {
            ListNode t1 = l1;
            ListNode t2 = l2;
            int count = 0;
            ListNode dummy = new ListNode(-1);
            ListNode t = dummy;
            int sum = 0;
            while (t1 != null && t2 != null) {
                sum = (t1.val + t2.val + count) % 10;
                ListNode n = new ListNode(sum);
                t.next = n;
                t = n;
                count = t1.val + t2.val + count;
                /*
                Necessary as let say t1 = 4 and t2 = 5 and after count = 1  it became 10
                so if we only do count = t1.val + t2.val it beomes 9 which is wrong , coutn becomes 0
                we also need to include the count then the count will become 1
                */

                count /= 10;

                t1 = t1.next;
                t2 = t2.next;
            }
            while (t1 != null) {
                sum = (t1.val + count) % 10;
                ListNode n = new ListNode(sum);
                count = t1.val + count;
                count /= 10;

                t.next = n;
                t = n;
                t1 = t1.next;
            }
            while (t2 != null) {
                sum = (t2.val + count) % 10;
                ListNode n = new ListNode(sum);
                count = t2.val + count;
                count /= 10;
                t.next = n;
                t = n;
                t2 = t2.next;
            }
            if (count == 1) {
                ListNode n = new ListNode(count);
                t.next = n;
            }
            return dummy.next;
        }
    }
}