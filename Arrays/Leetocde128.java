package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class Leetocde128
{
          /*Approach 1 :
          Time Complexity : O(N LOG N + N)
          SPACE COMPLEXITY : O(N + N)

          */

    public int longestConsecutive(int[] nums)
    {
        if(nums.length==0)return 0;
       /* First I will sort the array using the array  using  Merge Sort
       and then i will traverse the array and for each element i will maintain
       the count of elements it is realted to and update that element index in the count
       and atlast i will return the answer+1 because i didnot include the elemnt iselsef in the count
        */
        mergeSort(nums,0,nums.length-1);

        int [] count = new int[nums.length];
        int curr;
        int calc ;
        int j=-1;

        for ( int i = 0 ; i<nums.length-1; i++ )
        {

            if(j==nums.length)break;
            curr = nums[i];

            for ( j = i+1; j<nums.length; j++ )
            {
                calc = Math.abs(curr-nums[j]);
                if ( calc>1)
                {
                    i = j-1;
                    break;
                }
                else if (calc==1)
                {
                    count[i]++;
                    curr = nums[j];
                }

            }
        }

        int max = count[0];
        for( int i = 1 ; i < count.length ; i++ )max = Math.max(max,count[i]);

        return max+1;
       /*
       Time Complexity = O( N LOG N + N + N )
       Space Complexity = O( N + N)
       */
    }



    public  void mergeSort(int[] arr, int left, int right)
    {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private  void merge(int[] arr, int left, int mid, int right)
    {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; i++) leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++) rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    /*
    Approach : 2 */


     /**
    public int longestConsecutive(int[] nums){

     Approach 2 : HashSet Approach
    Step 1 : Put all the elements inside the hashset removing the duplicate elements
    Step 2 :  Then i will traverse the hashset and i will check does element-1 exists
    if not exists means it is the starting point and also start the count .
    and start checking if element+1, element+2,  element + 3 exists

        HashSet<Integer> set = new HashSet<>();
        for ( int i = 0 ; i<nums.length; i++)set.add(nums[i]);

        int max = 0;// 0 because i am trying to handle if we are given an empty array
        int length ;
        int x ;
        for ( int ele : set )
        {
            if(!set.contains(ele-1))
            {
                length = 1;
                x = ele+length;
                while (set.contains(x)){
                    length++;
                    x = ele+length;
                }

                max = Math.max(max, length);
            }

        }
        return max;


    }


      Time Complexity = O( N )
      SPACE COMPLEXITY = O (M) WHERE M IS EQUAL TO N ONLY WHEN ALL THE GIVEN NUMBER OF DISTINCT ELEMENTS

     */
}
