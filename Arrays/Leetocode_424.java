package Arrays;

public class Leetocode_424
{
}

/**

class Solution {
    public int characterReplacement(String s, int k)
    {
        /*
        Sliding Window :
        Algo : Things I will manage :
        MaxFrequency  Number, A Frequnency Array  , MaxLength Variable

        int [] arrFreq  = new int[26];
        int i = 0;
        int j = 0;
        // In classic sliding windows problem both the pointer start at same index called 0 and the window size shrink and expand in variable size
        int maxLength = 0 ;
        int maxFrequency = 0;
        int idx = 0;
        for ( ; j < s.length() ; )
        {
            idx = s.charAt( j ) - 65 ;
            arrFreq[idx]++;
            maxFrequency = Math.max(arrFreq[idx],maxFrequency);

            if( ( j - i +  1 ) - maxFrequency <=  k )
            {
                maxLength = Math.max( (j-i+1),maxLength);
                j++;
              continue;
            }
            arrFreq[s.charAt(i) - 65 ] = 0;
            i = j;
            arrFreq[ s.charAt(j) - 65 ] = 0;

        }
        return maxLength;
        My approach where i have made the mistake





        int[] arrFreq = new int[26];
        int i = 0, j = 0;
        int maxLength = 0;
        int maxFrequency = 0;

        for ( ; j < s.length(); j++) {
            int idx = s.charAt(j) - 65;
            arrFreq[idx]++;
            maxFrequency = Math.max(arrFreq[idx], maxFrequency);

            if ((j - i + 1) - maxFrequency > k) {
                arrFreq[s.charAt(i) - 65]--;   // just remove ONE character
                i++;                            // shrink by ONE step
            }

            maxLength = Math.max(maxLength, j - i + 1);
        }
        return maxLength;
        /*
        T.C = O (N)
        S.C = O (1)
        size is always 26
        and we can implement the same frequency thing using hashmap

    }
}
*/