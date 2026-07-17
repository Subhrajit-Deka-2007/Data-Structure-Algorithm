package Arrays;

import java.util.Stack;

public class Leetcode_20
{
    class Solution {
        public boolean isValid(String s)
        {
/*
 ASCII Numbers
  (--->  40
  )--->  41
  [--->  91
  ]--->  93
  {---> 123
  }---> 125

  Brut force :
  I will make a frequncy array of brackets and update the value
  and later on i will traverse the array and when i traverse the array i
  will check for a particular bracket index it's closing bracket index has
  also the same value if they have the same value then it is a valid array .


 int [] arrFreq = new int[6];
 int diff = 0;
 for ( int i = 0 ; i<s.length() ; i++ )
 {
    diff = s.charAt(i)%10;
     if( s.charAt(i)==91)arrFreq[2]++;
     else if( diff == 3 && s.charAt(i)==123)arrFreq[4]++;
     else arrFreq[diff]++;
 }

 for ( int i = 0 ; i<arrFreq.length ; i+=2 ) if(arrFreq[i]!=arrFreq[i+1])return false;

 return true;
 ([)] ----> My algo doesnot work for these case .
 */

            Stack<Character> st = new Stack<>();
// we can also maintain an character but we need to make the behavior same as the stack
// if it exists idx = '' and idx--;
            int val = 0 ;
            for ( int i = 0 ; i < s.length() ; i++ )
            {
                val = s.charAt(i);
                if ( val == 40  || val == 91 || val == 123 )
                {
                    if(val==40)st.push(')');
                    else if ( val == 91 )st.push(']');
                    else st.push('}');
                }
                else
                {
                    if ( st.size()>0 && s.charAt(i) == st.peek() )st.pop();
                    else return false;
                }
            }

            return (st.size()==0);
        }
/*
Time: O(n)
Space: O(n) — worst case, all opening brackets (e.g., "((((((")

It doesnot work with frequncy thing as we also need the history of the previos bracket
after we remove a valid bracket


My thought i can do with hasmap by tracking the open bracket indexes for example for open bracket ( -> present at 0,2,3,
and when we start our next loop of checking as soon as we get the closed bracket but it
will be tru for (((((())))))
but not good for ()[]{} here the open and closed brackets are adjacent not the case where all the open brackets in one direction and closed brackets in other direction


A special case where you CAN do better — single bracket type only
If the problem only involved ONE type of bracket (say, only ( and ), no [] or {}), you could solve it with O(1) space — just a single counter:
int balance = 0;
for (char c : s.toCharArray()) {
    if (c == '(') balance++;
    else balance--;
    if (balance < 0) return false; // more closes than opens at some point
}
return balance == 0;
Why this works for single-bracket-type only: since there's only one kind of bracket, you don't need to know WHICH bracket is waiting to be closed — just HOW MANY are currently open. A running count is enough.
Why this does NOT work for multiple bracket types (your actual problem): with (, [, { all mixed together, a simple count can't tell you whether a ) you're looking at should match against a ( or if it's incorrectly trying to close a [ that opened more recently. You genuinely need the stack (or equivalent) to track ORDER, not just quantity.

*/
    }
}
