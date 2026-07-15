package Arrays;

import java.util.HashSet;

public class Leetcode_3
{
}
/**
class Solution {
    public int lengthOfLongestSubstring(String s)
    {

        if ( s.length() == 0 || s.length() == 1 ) return s.length();
        HashSet< Character > set = new HashSet<>();

        int i = 0;
        int j = 0;
        int maxLength = 0;

        for( ; j < s.length() ; )
        {



            if( ! set.contains( s.charAt( j ) ) )
            {
                set.add(s.charAt(j));
                maxLength = Math.max( j - i + 1 , maxLength );
                j++;
                continue;
            }

            while (set.contains(s.charAt(j)))
            {
                // start removing the characters from the set
                set.remove(s.charAt(i));
                i++;
            }
            set.add(s.charAt(j));
            j++;


        }
/*
Time Complexity = O ( N )
Space Complexity = O ( K) => WHERE K IS THE NUMBER OF UNIQUE CHARACTERS IN THE STRING

/*
INSTEAD OF CREATING HASHSET FROM FREQUENCY WE CAN CREATE AN ARRAY OF SIZE 128 INCLUDING BOTH
ALPHANUMERIC AND NON - ALPHANUMERIC AND SO WE NEED TO DEAL WITH THE ASCII AS INDEX OKAY
SO REGARDLESS OF STRING SIZE ,SIZE WILL BE ALWAYS 128 .


I CAN DO ONE MORE THING I CAN CREATE AN ARRAY OF 52 SIZE AND SUBTRACT IT SUCH A WAY THAT I WILL START STORING THE SMALL LETTERS FROM 0TH INDEX TO 25 INDEX AND UPPER CASE LETTER FROM 26 INDEX TO 53 TH INDEX NO NEED TO TELL BUT I CAN DO IT

We can do the same thing using 54 size arrays where first 26 for smaller and last 26 for upper case
=========================================================================================================================

        return maxLength;
 '0' to '9'  →  48 to 57
 'A' to 'Z'  →  65 to 90
 'a' to 'z'  →  97 to 122
    }
}
*/