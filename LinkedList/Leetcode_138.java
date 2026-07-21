package LinkedList;

import java.util.HashMap;

public class Leetcode_138
{

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}


    class Solution {
        public Node copyRandomList(Node head) {
            // Algo first we make a deep copy and there will be no random pointers there will be only next okay
            // then make a map of map<original_LL,duplicate_LL>
            // then just do duplicate.random = map.get(original.random);
            if(head == null) return null;
            Node head2 = new Node(head.val);
            Node temp2 =head2;
            Node temp = head.next;
            while(temp!=null){// deep copy get constructed
                Node dup = new Node(temp.val);
                temp2.next = dup;
                temp2 =dup;
                temp = temp.next;
            }
            // now make a hasmap
            HashMap<Node,Node>map = new HashMap<>();
            temp2 = head2;
            temp = head;
            while(temp!=null&&temp2!=null){
                map.put(temp,temp2);
                temp = temp.next;
                temp2 = temp2.next;
            }
            // now connect the random pointers
            // we can traverse in map also or we can traverse linked list also
            for(Node original :map.keySet()){
                // first find the duplicate of the current node
                Node duplicate = map.get(original);
                if(original.random!=null)duplicate.random = map.get(original.random);
            }
            return head2;
        }
    }
}
