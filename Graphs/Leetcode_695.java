package Graphs;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode_695
{
    class Solution {
        public int maxAreaOfIsland(int[][] grid)
        {
            boolean [][] mark = new boolean[ grid.length ][ grid[0].length ];
            int maxArea = 0;
            int area = 0;

            for( int i = 0 ; i < grid.length ; i++ )
            {
                for ( int j = 0 ; j < grid[0].length ; j++ )
                {
                    if( grid[i][j]!=0 && !mark[i][j] )
                    {
                   /*
                    area = 0;
                    area = dfs( grid, i, j , grid.length-1 , grid[0].length-1 ,mark,area );
                    */
                        area = 0;
                        area = bfs(grid , i, j ,grid.length-1,grid[0].length-1,mark,area );
                        System.out.println(area);
                        maxArea = Math.max( area, maxArea );


                    }

                }
            }
            return maxArea;
        }

        public int dfs( int [][] grid , int sr , int sc , int er, int ec, boolean[][] mark , int area  )
        {
            if( sr > er || sc >ec ) return 0;
            else if( sr<0 || sc<0) return 0;
            else if( grid[sr][sc] == 0 || mark[sr][sc]) return 0;

            mark[sr][sc]=true;

            // Go left

            int left = dfs(grid,sr,sc-1,er,ec,mark,area);

            //Go right
            int right = dfs(grid,sr,sc+1,er,ec,mark,area);

            //Go up
            int up = dfs(grid,sr-1,sc,er,ec,mark,area);

            //Go Down
            int down =dfs(grid,sr+1,sc,er,ec,mark,area);

            return 1 + left +right +up+down;
        }
        public int bfs( int [][] grid , int sr, int sc, int er , int ec , boolean [][] check , int area )
        {
            Queue<Integer> q = new ArrayDeque<>();
            q.add(sr);
            q.add(sc);

            area = 0;
            check[sr][sc]= true;

            while( !q.isEmpty() )
            {
                int row = q.poll();
                int col = q.poll();

                area++;

                // Checking the left
                if( col-1>=0 && check[row][col-1]!=true && grid[row][col-1] !=0)
                {
                    q.add(row);
                    q.add(col-1);
                    check[row][col-1]= true;
                }
                // Checking for right
                if( col+1 <= grid[0].length-1 && check[row][col+1]!=true && grid[row][col+1] !=0 )
                {
                    q.add(row);
                    q.add(col+1);
                    check[row][col+1]= true;
                }
                //Checking Up
                if( row-1 >=0 && check[row-1][col]!=true && grid[row-1][col]!=0 )
                {
                    q.add(row-1);
                    q.add(col);
                    check[row-1][col]= true;
                }
                // Checking Down

                if( row+1<= grid.length-1 && check[row+1][col]!=true && grid[row+1][col]!=0)
                {
                    q.add(row+1);
                    q.add(col);
                    check[row+1][col]= true;
                }

            }



            return area;
        }
    }
/*
Time and Space Complexity for both approaches
Time:  O(N × M)
Space: O(N × M)
*/
}
