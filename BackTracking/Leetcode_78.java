package BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_78
{
    class Solution {
        public List<List<Integer>> subsets(int[] nums)
        {


       /* List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        int total = 1<<n;
        for(int num = 0;num<total;num++){
            List<Integer> list = new ArrayList<>();
            for(int bitIdx =0;bitIdx<n;bitIdx++){
                int mask = (1<<bitIdx);
                if((num&mask)!=0)// means the bit was set now add that element to the arraylist
                    list.add(nums[bitIdx]);
            }
            ans.add(list);
        }
        return ans;
        */
            List<Integer> ls = new ArrayList<>();
            List<List<Integer>> ans = new ArrayList<>();
            subset(nums , 0, ls , ans );
            return ans;

        }
    /*
    Approach : Brut Force --> Using Nested Loop :
    Not possible as we want substring not subarray so things are
    not contiguous .
    Brut Force fail

    public List<List<Integer>> brutForce( int [] nums )
    {
        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> ls = new ArrayList<>();
        ans.add(ls);

        for ( int i = 0 ; i < nums.length - 1 ; i++ )
        {
            ls = new ArrayList<>();
            ls.add(nums[i]);
            ans.add(ls);
            ls = new ArrayList<>();
            ls.add(nums[i]);
            for( int j = i+1 ; j < nums.length ; j++ )
            {
               ls.add(nums[j]);
            }
            ans.add(ls);
        }
        ls = new

        return ans ;
    }
    */

        /*
         Since we need to find the Subset that means we don't need any
        contiguous thing we can skip ome middle part and in array when we need to
        find the subsets . So relative order doesn't matter like subsequnce in String
        */
        public void subset(int [] nums , int idx , List<Integer> ls , List<List<Integer>> ans )
        {
        /*
        Here we have two options in each step either take that element
        or skip that element
        */
            if( idx == nums.length )
            {
                // These means we got a valid combo
                List<Integer> s = new ArrayList<>(ls);
                // why we made a new arraylist here as
                // if we insert the old list here then for the other calls it will
                // keep updating the list that is inside the ans list
                // no new list will get inserted
                ans.add(s);
                return ;
            }

            // take the current element
            ls.add(nums[ idx ]);
            subset( nums, idx+1, ls , ans );
            // Or skip the element --> first remove the element we took on take step it
            // will at the current last index of the arrayList
            ls.remove( ls.size()-1 );
            subset( nums , idx+1, ls ,ans );
        }

    /*
    Time Complexity : For each element we will have 2 options
    so Time Complexity will be

     Time = (number of calls) + (number of copies × work per copy)
     Time = O(2^n) + O(2^n × n)
     = O(2^n) + O(n × 2^n)

    Total calls (branching)     = O(2^N)      ← your sum captures this correctly
    Work done AT each leaf      = O(N)         ← copying the list

     2^0 + 2^1 + ---- + 2^n

    Space Complexity : Recursive Stack --> O (N) WHERE N Is the length of the array
    how we know these see the base case
    */
    }
}
