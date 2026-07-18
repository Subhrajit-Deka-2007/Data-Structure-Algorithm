package Arrays;

import java.util.Stack;

public class LeetCode_155
{

    /**
     class MinStack {
     Stack<Integer> s ;
     Stack<Integer> min ;
     public MinStack()
     {
     s = new Stack<>();
     min = new Stack<>();
     }

     public void push(int value)
     {
     if(s.isEmpty())
     {
     s.push(value);
     min.push(value);
     return;
     }
     if( value>=min.peek() )min.push( min.peek() );
     else min.push(value);
     s.push(value);
     }

     public void pop()
     {
     s.pop();
     min.pop();
     }

     public int top()
     {
     return s.peek();
     }

     public int getMin()
     {
     return min.peek();
     }

     Time Complexity = O(n)
     Space Compelxity = O(k) where k = n only if there are n number of push operation
     */

    /**
     Method 2 : Trying using only one stack
     */
    class MinStack {
        Stack<Integer> s ;
        public MinStack()
        {
            s = new Stack<>();
        }
        public void push( int value )
        {
            if( s.isEmpty() )
            {
                s.push(value);
                s.push(value);
            }
            else
            {
                // So the top of the stack represents the minimum element , among the elements present inisde thestack
                int min = Math.min( value , s.peek() );
                s.push(value);
                s.push(min);
            }
        }
        public void pop()
        {
            s.pop();
        /*
         first removing the minimum element among those elements present inside , if we don't remove that
         element it will give us wrong answer
         */
            s.pop();
        /*
        Secondly I am removing the element istelf
        */
        }
        public int top()
        {
            int temp = s.pop();
            int x =  s.peek();
            s.push(temp);
            return x;
        }
        public int getMin()
        {
            return s.peek();
        }
/**
 T.C = O (N)
 S.C = O (2N)
 */
    }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(value);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */


}
