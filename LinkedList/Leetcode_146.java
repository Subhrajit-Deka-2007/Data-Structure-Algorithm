package LinkedList;


import java.util.HashMap;

class Node
{
    int key;
    int val;
    Node next;
    Node prev;
    Node( int val , int key )
    {
        this.val = val;
        this.key = key;
    }
}
class LRUCache {

    int capacity;
    HashMap<Integer,Node> map;
    Node head ;
    Node tail;

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
        map = new HashMap<>();
    }


    public void display() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val);
            temp = temp.next;
        }
        System.out.println();
        temp = tail;
        while ( temp!=null)
        {
            System.out.print(temp.val);
            temp = temp.prev;
        }
        System.out.println();
    }
    public int get(int key)
    {
        // System.out.println(" get() ");
        if(map.containsKey(key))
        {
        //    System.out.println(" Inside if ");
            Node temp  = map.get(key);


            if ( temp == head || head == tail ) return temp.val;
            else if ( temp == tail )
            {

                tail = tail.prev;

                tail.next = null;

                temp.prev = null;
                temp.next = head;
                head.prev = temp;
                head = temp;
//                System.out.println(head.val+" ");
                return temp.val;
            }
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            temp.next = head;
            temp.prev = null;
            head.prev= temp;
            head = temp;
           // System.out.println("mksmdsl"+temp.val + " "+temp.next.val+" "+temp.next.next.val );
            //System.out.println("msldmsldm"+tail.val+" "+tail.prev.val+" "+tail.prev.prev.val);
            return map.get(key).val;
        }
       // System.out.println(" Outside of if ");
        return -1;
    }


    public void put ( int key , int value )
    {

      //  System.out.println("put()");

        if ( map.containsKey( key ) )
        {
            Node temp = map.get(key);
            temp.val = value ;
            map.put(key,temp);

            if( head == tail || temp == head )return;
            else if( temp == tail )
            {
                tail = tail.prev;
                tail.next = null;

                temp.prev = null;
                temp.next = head;
                head.prev = temp;
                head = temp;
            }
            else
            {

                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;

                temp.next = head;
                temp.prev = null;

                head.prev = temp;
                temp.next = head;

                head = temp;

            }

        }
    /*
    If the key doesn't exist in the map there are two cases:
    i) Capacity is Full
    ii) Capacity is Not Full
    */
        else
        {
           System.out.println(map);
            Node temp = new Node(value,key);
            if( capacity == map.size() )
            {
                // If the capacity is full again I need to check many things
                if( head == tail  )
                {
                    map.remove(head.key);
                    head = null;
                    tail = null;
                    head = temp;
                    tail = temp;

                }
                else// means tail and head are not same
                {
                    map.remove(tail.key);
                    tail = tail.prev;
                    tail.next = null;

                    temp.next = head;
                    head.prev = temp;
                    head = temp;


                }
                map.put(key,temp);
            }
            else// If the capacity is not full and the key doesn't exist just include it in the front and make it the new head
            {
                if( head == null )
                {// means the capacity is not full and the list is empty
                  head = temp;
                  tail= temp;
                  map.put(key,temp);
                }
                else if( head == tail )
                {// capacity is not full but list is not empty
                     temp.next = head;
                     head.prev = temp;
                     head = temp;
                    map.put(key,temp);
                    return;
                }
                temp.next =  head;
                head.prev = temp;
                head = temp;
                // I forgot to put the thing inside the map
                map.put(key,temp);
            }

        }







    }
}






/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
public class Leetcode_146
{
    public static void main(String[] args)
    {
        LRUCache lRUCache = new LRUCache(3); // Capacity is 3



        lRUCache.put(1, 1);
        lRUCache.put(2, 2);
        lRUCache.put(3, 3);
        lRUCache.put(4, 4);
        lRUCache.display();
        System.out.println(" --------------------------------------------------");
        System.out.println(lRUCache.get(4));
        lRUCache.display();
        System.out.println(lRUCache.get(3));
        lRUCache.display();
        System.out.println(lRUCache.get(2));
        lRUCache.display();
        System.out.println("--------------------------------------------------------");

        System.out.println(lRUCache.get(1));


        lRUCache.put(5, 5);

        lRUCache.display();
        System.out.println(lRUCache.get(1));


        System.out.println(lRUCache.get(2));


        System.out.println(lRUCache.get(3));


        System.out.println(lRUCache.get(4));


        System.out.println(lRUCache.get(5));// Returns: 5

    }
}
