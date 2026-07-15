package Arrays;

public class Leetocde_11
{


    class Solution {
        public int maxArea(int[] height)
        {
/*
 Two Pointer approach : O(N)
 */
            int low = 0;
            int hi  = height.length-1;

            int maxArea = Integer.MIN_VALUE;

            for ( ; low < hi ; )
            {
                maxArea = Math.max( maxArea, Math.min( height[low], height[hi] )*(hi-low) );
                if ( height[hi]>height[low] ) low ++;
                else hi --;
            }
            return maxArea;
        }
/*
T.C = O (N)
S.C = O (1)
*/

/**
 A little better approach then my two pointer same time complexity but more optimize
 int i=0;
 int j=height.length-1;
 int area=0;
 while(i<j){
 int min_height = Math.min(height[i], height[j]);
 area = Math.max(area, min_height*(j-i));
 while(i<j && height[i]<=min_height) i++;
 while(i<j && height[j]<=min_height) j--;
 }
 return area;
 }




 Solves :

 In these approach first we find the minimum among the two towers present on the left side and right side
 and then find the area and checking if the  adjacent towers are smaller, then we just skip it useless to
 not find the area as the distance is becoming is smaller and the height is smaller means both the lenght
 and breadth are decreasing so useless and if we find a taller tower then on side or both side then
 we have one quantity that is increasing so finding the height becomes important what if the height of the
 tower nullifies the decreasing distance and after fulfilling it , let some  value remained and that value is greater
 then ,the height of the tower for which we find the area,  then on that case area will be bigger.

              []
              []
              []
              []
              []
              []
              []    []
              []    []
              []    []
              []    []
              []    []
              []    []
              []    []
              []    []
              []    []
              []    []
     []       []    []        []
     []       []    []        []
     [] []    []    []     [] []
 ____[]_[]_[]_[]_[]_[]__[]_[]_[]____

 */
    }
}
