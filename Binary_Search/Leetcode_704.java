package Binary_Search;

public class Leetcode_704
{
    class Solution {
        public int search(int[] arr, int target) {
            int st =0;
            int end = arr.length-1;
            int mid;
            while(st<=end){
                mid= st+(end-st)/2;
                if(arr[mid]==target)return  mid;
                else if(arr[mid]<target)st=mid+1;
                else end = mid-1;
            }
            return -1;
        }
    }
}
