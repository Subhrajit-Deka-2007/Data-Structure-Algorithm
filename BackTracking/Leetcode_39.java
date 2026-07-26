package BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_39
{

    class Solution
    {
        public List<List<Integer>> combinationSum(int[] candidates, int target)
        {
            List<Integer> ls = new ArrayList<>();
            List<List<Integer>> ans = new ArrayList<>();
            int sum = 0;
        /*
        I will need these as these variable will help me in checking if the current
         element sum is equal to the given target
         */
            comboSum( candidates , 0, ls, ans , sum, target  );
            return ans ;
        }
        public void comboSum( int [] arr , int idx , List<Integer> ls , List<List<Integer>> ans , int sum ,int target )
        {
            // Base case
            if( idx == arr.length || sum > target )
            {
                // We got one Combo
                // But before adding it to the answer i need to check is the combo valid
                // so I will add one condition in the base case

                if( sum == target )
                {
                    List<Integer> s = new ArrayList<>(ls);
                    ans.add(s);
                }
                return;
            }
            // Here for each element I ahve two steps take it or skip it

            // If i take it then
            sum += arr[idx];
            ls.add(arr[idx]);


            comboSum( arr, idx , ls, ans , sum, target  );
            // If I skip it
            sum-=arr[idx];
            ls.remove( ls.size()-1 );

            comboSum( arr, idx+1 , ls ,ans , sum , target);
        }
 /*
 Time Complexity = On each call I have Three Options
 3 + 3 ^1 + 3 ^2 + --- + 3^N  + ( NUMBER OF LEAF NODES * WORK DONE TO COPY EACH LIST => N )
 = > O ( 3 )

  */
}
}
