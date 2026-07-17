package Arrays;

import java.util.Stack;

public class LeetCode_155
{

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
/**
 Time Complexity = O(n)
 Space Complexity = O(k+k) where k = n only if there are n number of push operation
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
