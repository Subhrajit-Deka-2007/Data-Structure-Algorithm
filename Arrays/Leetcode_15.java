package Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Leetcode_15
{
    public static void main(String[] args)
    {

/**

        These is the mistake i made during sorting
        int [] arr = { 0,1,-1};
        int min = arr[0];
        // These is the reason it was not working
        for ( int x = 1 ; x < arr.length ; x++ )
        {
            if( arr[x]<min )
            {

                arr[ x-1 ] = arr[ x ];
                arr[ x ]   = min;
            }
            min        = arr[x];

        }
 */

    }

    class Solution {
        /**
        public List<List<Integer>> threeSum(int[] nums )
        {


     Brut Force : Using three nested loops
     T.C = O (N^3+ 3 log 3 )
     S.C = O ( k) = > where k is the number of unique triplets  k = N C 3
     But there is a drawback in these approach as he question is saying that we don't need duplicate elements
     . But we can solve it using hashset . So after i got my triplets i will also start adding the numbers inside the haset . But beofre i add those elements in the hashset i will sort those elements and then i will put that elements inside the HashSet and so the checking of list it exists or not will work


     List<List<Integer>> ans = new ArrayList<>();
     Set<List<Integer>> set = new HashSet<>();
     for ( int i = 0 ; i < nums.length -2  ; i++  )
     {
        for( int j = i+1; j<nums.length -1 ; j++ )
        {
            for ( int k = j+1; k<nums.length; k++ )
            {
                if( nums[i] + nums[j] + nums[k] == 0 )
                {

                    List<Integer> ls = sorting(nums[i], nums[j],nums[k]);
                    if ( !set.contains(ls) )
                    {
                        ans.add(ls);
                        set.add(ls);


                    }
                }
            }
        }



      }

      return ans;
       public List<Integer> sorting( int i , int j , int k )
    {

        int [] arr = { i, j, k };
        Arrays.sort(arr);
      List<Integer> ls = new ArrayList<>();
      ls.add(arr[0]);
      ls.add(arr[1]);
      ls.add(arr[2]);

      return ls;
     }
     */


    /**
    Approach 2 : Two Pointers
    Step 1 : First I will sort the array
    Step 2 : I will use a nested loop
    Where the outer loop will go from 0 to N-3
    and inside loop will help us in two pointers
    n-1 + n-2 + n-3 + ------+2 => total n-3 terms
    T.C = O (N LOGN + N/2( 2a + (n-1)d ))


            Arrays.sort(nums);

            int lo;
            int hi;
            long prev = Long.MAX_VALUE;
            List<Integer> ls;
            List<List<Integer>> ans = new ArrayList<>();

            long low ;
            long high ;

            for ( int i = 0 ; i< nums.length - 2 ; i++ )
            {

                if ( prev == nums[i])continue;
                lo = i+1;
                hi = nums.length-1;

                low = Long.MAX_VALUE;
                high = Long.MAX_VALUE;

                for ( ; lo<hi; )
                {
                    if( nums[i] + nums[lo]+ nums[hi] == 0 )
                    {

                        if ( nums[lo] == low && nums[hi] == high )
                        {

                no need for 2nd case as I have already sorted the array.
                nums[lo]== high && nums[hi]== low It is used when the array is unsorted but i have already
                solved the issue


                            lo++;
                            hi--;
                            continue;
                        }

                        ls = new ArrayList<>();
                        ls.add(nums[lo]);
                        ls.add(nums[hi]);
                        ls.add(nums[i]);
                        low = nums[lo];
                        high = nums[hi];
                        ans.add(ls);

                        lo++;
                        hi--;
                    }
                    else if ( nums[i]+ nums[lo]+nums[hi]>0)hi--;
                    else lo++;

                }
                prev = nums[i];
            }

      return ans;
      These is the most optimal solution
      T.C = O ( N LOG N + N^2 )
      S.C = O (1)


        }

     */

    }
}
