package LinkedList;

public class Leetcode_287
{
    class Solution {
        public int findDuplicate(int[] nums)
        {
      /*
      Brut Force :
      Step 1 : Sorting .
      Step 2 : And for each number I will check the rest of the array does duplicate of that element
      exists if i found any duplicate i will return the element and if i don't found for that
      element I will repeat the proccess for the remaining elements
      Time Complexity = O ( N log N + N^2 )
      We don't even need to sort it will work Time Complexity Will be
      O (N^2)


       for( int i = 0 ; i<nums.length-1 ; i++ )
        for (int j = i+1; j<nums.length ; j++ )
            if( nums[i]== nums[j] ) return nums[j];

       return -1;

       /*
       Second Appraoch : I  can either use HashMap or HashSet
       Time Complexity = O (N)
       Space Complexity = O (N)

       HashSet<Integer> set = new HashSet<>();
       for ( int i = 0 ; i < nums.length ; i++ )
       {
        if( set.contains( nums[i] ) )return nums[i];
        else set.add( nums[i] );
       }

       return -1;
       */

       /*
       Appraoch 3 : Optimize Appraoch : In my approach i am changing the original array
       Time Complexity = O (N)
       Space Complexity = O (1)


       int duplicate = nums[0];
       nums[0] = 0;
       int y = 0;


      while(true){
        if( nums[duplicate]!=0 )
        {
             y = nums[duplicate];
            nums[duplicate] = 0;
            duplicate = y;

        }
        else break;

       }

       return duplicate;
       /*
       Time Complexity = O (N)
       Space Complexity = O(1)
       */






            int slow = nums[0];
            int fast = nums[0];

            // Phase 1: detect the cycle
            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            } while (slow != fast);
            // MOVING TWO STEPS JUST LIKE ME MOVE AT THE CYCLE DETECTION IN LINKED LIST
            // WE GOT THE CYCLE BUT IS NOT GURANTEED THAT THE PALCE WHERE THEY MEET IS THE POINT WHERE
            // THE CYCLE IS DETECTED THEY CAN MEET AFTER THE CYCLE POINT TO FIND THE CYCLE POINT WE NEED THE PHASE 2
            // PHASE 2 ENSURES NOW THESE TIME THEY MEET IT IS AT THE CYCLE POINT

            // Phase 2: find the cycle's ENTRY point (which equals the duplicate)
            slow = nums[0];
            while (slow != fast)
            {// NOW WE MOVE THEM JUST
                slow = nums[slow];
                fast = nums[fast];
            }

            return slow;

/*

Floyd’s Cycle-Finding Algorithm:

The Two-Stage MagicStage 1 (Detection): You run the fast and slow pointers until they meet. This confirms a cycle exists.Stage 2 (Finding the Entrance): You reset the slow pointer to the head and keep the fast pointer at the "meeting point." Then, you move both at a speed of $v$ (1 step). The node where they meet again is guaranteed to be the entrance of the cycle.
*/
        }
    }
}
