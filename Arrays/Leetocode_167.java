package Arrays;

public class Leetocode_167 {

    class Solution {
        public int[] twoSum(int[] arr, int target) {
        /*
         Brut Force : Using nested loop

         for ( int i = 0 ; i < numbers .length-1 ; i++ )
            for ( int j = i+1 ; j<numbers.length ; j ++ )
                       if ( arr[i] + arr[j] == target )
                       {
                       arr[0] = i + 1;
                       arr[1] = j + 1;
                       return arr;
                       }

       Time Complexity = O ( n ^2 )
       Space CXompelxity = O ( 1 )

        */


        /*
         Optimize Approach 1 :  ( Binary Search ) : O ( N  Log N )
         for each element in the array we will find target - that element and then apply binary search
         on that element index + 1 to the last index

          Log N-1 + Log N-2 + ----- + 1 = Log ( (n-1) !) = > N Log N

          Time Compllexity = Log N -1 + Log N - 2 + -----+ 1 => N Log N
          Space Complexity = O ( Log N )// recursive stack


       int [] arr = new int [2];
       int ele = 0;
       for (  int i = 0 ; i < numbers.length-1 ; i++ )
       {
         arr[0] = i+1;
         ele = bs( numbers , i+1, numbers.length-1, target - numbers[i] );
         if( ele !=-1 ){
            arr[1] = ele+1;
            return arr;
         }

       }
       return arr;

*/

        /*
         Optimize Appraoch 2 : O(N) for two pointer */

            int i = 0;
            int j = arr.length - 1;
            int[] ans = new int[2];

            for (; i < j; ) {
                if (arr[i] + arr[j] == target) {
                    ans[0] = i + 1;
                    ans[1] = j + 1;
                    return ans;
                } else if (arr[i] + arr[j] > target) j--;
                else i++;
            }
         /*
         T.C = O(n)
         S.c = O(1)
        */


            return ans;
        }

        public int bs(int[] numbers, int low, int high, int t) {
            if (low > high) return -1;
            int mid = low + (high - low) / 2;
            if (numbers[mid] == t) return mid;
            else if (numbers[mid] < t) return bs(numbers, mid + 1, high, t);
            else return bs(numbers, low, mid - 1, t);
        }
    }
}