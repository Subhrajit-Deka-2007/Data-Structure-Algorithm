package Arrays;

public class Leetcode_125
{
}
class Solution {
    /**
    Space and punctuation before digits (32-47):
    ' '=32  '!'=33  '"'=34  '#'=35  '$'=36  '%'=37  '&'=38
    '\''=39 '('=40  ')'=41  '*'=42  '+'=43  ','=44  '-'=45
    '.'=46  '/'=47

    Punctuation between digits and uppercase (58-64):
    ':'=58  ';'=59  '<'=60  '='=61  '>'=62  '?'=63  '@'=64

    Punctuation between uppercase and lowercase (91-96):
    '['=91  '\\'=92 ']'=93  '^'=94  '_'=95  '`'=96

    Punctuation after lowercase (123-126):
    '{'=123 '|'=124 '}'=125 '~'=126


    Alphanumeric characters = 62 total:
    Digits (10):     '0' to '9'   -> ASCII 48 to 57
    Uppercase (26):  'A' to 'Z'   -> ASCII 65 to 90
    Lowercase (26):  'a' to 'z'   -> ASCII 97 to 122
    Full breakdown:
    '0'=48  '1'=49  '2'=50  '3'=51  '4'=52
    '5'=53  '6'=54  '7'=55  '8'=56  '9'=57

    'A'=65  'B'=66  'C'=67  'D'=68  'E'=69  'F'=70  'G'=71
    'H'=72  'I'=73  'J'=74  'K'=75  'L'=76  'M'=77  'N'=78
    'O'=79  'P'=80  'Q'=81  'R'=82  'S'=83  'T'=84  'U'=85
    'V'=86  'W'=87  'X'=88  'Y'=89  'Z'=90

    'a'=97  'b'=98  'c'=99  'd'=100 'e'=101 'f'=102 'g'=103
    'h'=104 'i'=105 'j'=106 'k'=107 'l'=108 'm'=109 'n'=110
    'o'=111 'p'=112 'q'=113 'r'=114 's'=115 't'=116 'u'=117
    'v'=118 'w'=119 'x'=120 'y'=121 'z'=122
    That's all 62, with their exact ASCII values.
    */
    public boolean isPalindrome(String s)
    {
        /** A palindrome is always a  valid anagram. */
        /**
        Approach 1 : Using extra space

        StringBuilder sb = new StringBuilder(s.length());
        int ascii;
        int j = 0;
        int i ;
        char c ;

        for ( i = 0 ; i < s.length() ; i++ )
        {
            ascii = s.charAt(i);
           if ( ascii >=32 && ascii<=47 || ascii >=58 && ascii<=64 || ascii>=91&&ascii<=96 || ascii>=123&&ascii<=126 ) continue;

           if (ascii>=65&&ascii<=90)ascii+=32;
            c = (char)ascii;
           sb.append(c);
           j++;

        }



        i = 0;

      //  if ( j%2 == 0 && sb.length()>0 )return bitwise(sb,i,j);
        j--;
        while ( i < j ) if( sb.charAt( i++ )!=sb.charAt( j-- ) )return false;




        Time Complexity = O (N + N )
        Space Complexity = O (N) where n is the length of the string

      return true;
*/

        /** Approach  : 2 */
        int i = 0 ;
        int j = s.length()-1;
        int  first;
        int  last;
        for ( ; i<j ; )
        {

            first = s.charAt(i);
            last  = s.charAt(j);

            if ( first >=32 && first<=47 || first >=58 && first<=64 || first>=91&&first<=96 || first>=123&&first<=126 )
            {
                i++;
                continue;
            }

            if ( last >=32 && last<=47 || last >=58 && last<=64 || last>=91&&last<=96 || last>=123&&last<=126 )
            {
                j--;
                continue;
            }

            if (first >= 65 && first <= 90 )first += 32 ;
            if (last >= 65 && last <= 90 )last += 32 ;

            if( first!=last )return false;
            else {
                i++;
                j--;
            }

        }
         /**
          T.C = O ( N)
          S.C = O(1)
          */
        return true;
    }
    public boolean  bitwise(StringBuilder s, int i , int j )
    {

        /**

         bHvX!?!!vHbX
         IT WILL NOT WORK AS THESE METHOD IS CHECKING PALINDROME FOR VALUES BUT IT IS NOT
         CHECKING THE POSITION FOR PALINDROME.

         */
        int xor = s.charAt(i);
        for ( int k = i+1 ; k < j ; k ++ )xor= xor ^ s.charAt(k);


        return ( xor == 0 );

    }
}
