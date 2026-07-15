package Arrays;

public class Leetocde_121
{
    class Solution {
      /*  public int maxProfit(int[] prices)
        {

        Brut force : Nested loop

        int diff = 0;
        int maxProfit = 0;// I am assigning it a value of zero to handle the base case
        for ( int i = 0 ; i < prices.length-1 ; i++ )
        {
            for ( int j = i+1 ; j< prices.length; j++ )
            {
                diff =  prices[ j ]- prices[ i ];
                if( diff > 0 )maxProfit = Math.max(diff,maxProfit);
            }
        }
        return maxProfit;

        T.C = O ( N^2 )
        S.C = O (1)
        */



       /*
       Optimize : Two pointer approach : It doesnot work 8 test case passed out of 80
       as everything is fine execpet the if case we are just guessing some certain for whihc we are doing j--

       int i = 0 ;
       int j = prices.length-1;
       int maxProfit = 0;
       int diff = 0;


      while( i<j )
      {
        diff =  prices[ j ]- prices[ i ];
        if( diff > 0)
        {
            maxProfit = Math.max(diff,maxProfit);
            i++;

        }
        else i++ ;
      }
      return maxProfit;
    }
    */
    /*
    Appraoch 2 : Sliding Windows :
    Initally i will make two pointers and it is not two pointer approach
    initially i will put the first ointer at zero th index and the next pointer at
    1st index and check if the jth pointer value is greater than  the ith pointer value
    and if it is greater that mean the difference of the jth value and ith value will
    be positive and it could be a valid answer and if it is a negative i will move the i to jth place
    and i will make the j ++;
    we will run the loop till the j != ar.length


    int lo = 0;
    int hi = 1;
    int diff = 0 ;
    int maxProfit = 0 ;
    for ( ; hi < prices.length ; )
    {
      diff = prices [hi] - prices [lo];
      if ( diff > 0 )
      {
        maxProfit = Math.max( diff , maxProfit );
        hi++;
        continue;
      }
      lo = hi;
      hi++;

    }
    return maxProfit;
/*
T.C = O (N)
S.C = O (1)
*/
        }
    }


