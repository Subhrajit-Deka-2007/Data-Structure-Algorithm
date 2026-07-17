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
 ([)] ----> My algo doesn't work for these case .
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

It doesn't work with frequency thing as we also need the history of the previous bracket
after we remove a valid bracket
*/
    }
}
