package Binary_Search;

public class Leetocode_74
{


    class Solution {
 /*   public boolean searchMatrix(int[][] matrix, int target)
    {
        for ( int i = 0 ; i < matrix.length ; i++ )
        {
            if( matrix[i][0]<= target && matrix[i][matrix[0].length-1] >= target ) return bs(matrix , i, target );

        }

      return false;
    }
    public boolean bs( int [][] matrix , int row , int target )
    {
        int st = 0;
        int end = matrix[0].length-1;
        int mid = 0 ;
        while ( st <=end )
        {

           mid = st + ( end - st )/2;
           if( matrix[row][mid]==target )return true;
           else if( matrix[row][mid]<target )st = mid+1;
           else end = mid-1;

        }
        return false;

        Time Complexity = O ( ROWS + LOG ( COULMNS ))
        Spce Complexity = O (1)

    }
    */


        public boolean searchMatrix(int[][] matrix, int target) {
            int rows = matrix.length;
            int cols = matrix[0].length;

            int left = 0, right = rows * cols - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                int row = mid / cols;
                int col = mid % cols;
                int value = matrix[row][col];

                if (value == target) return true;
                else if (value < target) left = mid + 1;
                else right = mid - 1;
            }
/**
 Time Complexity = O ( LOG (M*N))
 Space Complexity = O ( 1 )
 BETTER THEN MY APPRAOCH



 Reason why my formula is valid

 idx=0  → row 0
 idx=1  → row 0
 idx=2  → row 0
 idx=3  → row 0
 idx=4  → row 1
 idx=5  → row 1
 idx=6  → row 1
 idx=7  → row 1
 idx=8  → row 2
 idx=9  → row 2
 idx=10 → row 2
 idx=11 → row 2


 */
            return false;
        }
    }
}
