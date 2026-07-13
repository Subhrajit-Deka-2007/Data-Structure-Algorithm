package DATA_STRUCTURE_AND_ALGORITHM.Arrays;
import java.util.HashSet;

class Solution {
        public boolean containsDuplicate(int[] arr)
        {

        /*
        The time complexity of the code is O( N ^2 )
         And the space complexity is O(1).
         Appraoch : 1

        int ele ;
        for( int i = 0; i<arr.length-1; i++ )
        {
            ele = arr[i];

            for ( int j = i+1; j<arr.length ; j++ )
            {
               if( ele == arr[ j ] )return true;
            }
        }
        return false;
*/


       /*
       Approach 2 : Using Hashmap

       HashMap<Integer,Integer> map = new HashMap<>();
       map.put( arr[0], 0 );// the first index is seen just for the first time
       for( int i = 1 ; i<arr.length; i++)
       {
        if( map.containsKey( arr[i] ))return true;
        else map.put( arr[i], 0 );
       }
       return false;

       T.C = O(N)
       S.C = O(N)
       */

       /*
       Approach 3 : Using HashSet
       M-I

       HashSet<Integer> set = new HashSet<>();
       for ( int i = 0; i< arr.length ; i++ )set.add( arr[i] );

       return !( arr.length == set.size() );
       T.C = O (N)
       S.C =  O(N)
       */



      /*Approach 4 : Using HashSet*/

      HashSet<Integer> set = new HashSet<>();
      set.add(arr[0]);
       for ( int i = 1 ; i< arr.length ; i++ )
       {
            if( set.contains(arr[i]) )return true;
            else set.add( arr[i] );
       }
       return false;

       /*T.C = O(N)
       S.C = O(K) WHERE K = N ONLY IF ALL THE N ELEMENTS ARE DISTINCT
       */


       /*
       Approach 5: XOR operator but the approach will not be optimized
       */

      /*
      Approach 6 : Sorting --> Bubble Sort, Insertion Sort, Selection Sort
      and the best sorting algo is Merge Sort
      I can use sliding window but the window will be very small



      public void mergeSort(int[] arr, int left, int right) {
    if (left >= right) return; // base case: 1 or 0 elements, already sorted

    int mid = left + (right - left) / 2;

    mergeSort(arr, left, mid);        // sort left half
    mergeSort(arr, mid + 1, right);   // sort right half
    merge(arr, left, mid, right);     // merge the two sorted halves
}

private void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1]; // temporary array to hold merged result
    int i = left;        // pointer for left half
    int j = mid + 1;      // pointer for right half
    int k = 0;             // pointer for temp array

    // Compare elements from both halves, pick the smaller one
    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) {
            temp[k++] = arr[i++];
        } else {
            temp[k++] = arr[j++];
        }
    }

    // Copy any remaining elements from left half
    while (i <= mid) {
        temp[k++] = arr[i++];
    }

    // Copy any remaining elements from right half
    while (j <= right) {
        temp[k++] = arr[j++];
    }

    // Copy merged result back into original array
    for (int x = 0; x < temp.length; x++) {
        arr[left + x] = temp[x];
    }
}

// Driver call
public void sort(int[] arr) {
    mergeSort(arr, 0, arr.length - 1);
}
      */

        }
    }
    public class Leetcode_217
    {
        public static void main(String[] args)
        {

        }
 }
