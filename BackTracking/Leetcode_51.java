package BackTracking;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_51
{

    class Solution {
        public List<List<String>> solveNQueens(int n)
        {
            char[][] c = new char[n][n];

            for ( int i = 0 ; i < n ; i++ )
                for ( int j = 0 ; j< n ; j++ )c[i][j]='.';


            List<List<String>> ans = new ArrayList<>();
            ways(c , 0 ,0 ,n-1 ,n-1 ,ans );
            return ans;
        }
        public void ways( char[][] c, int sr, int sc , int er , int ec , List<List<String>> ans )
        {
            if( sr > er  )
            {
                // We got one valid combo now put these thing in the list
                List<String> ls = new ArrayList<>(copy(c) );
                ans.add(ls);
                return;
            }

            for ( int i = sc ; i < c[0].length ; i++ )
            {
                if( valid(  c, sr , i  ) )
                {
                    c[sr][i] = 'Q';
                    ways( c, sr+1,0,er,ec,ans);
                    c[sr][i]='.';
                }

            }

        }

        public boolean valid( char[][] c , int sr, int sc )
        {
            // Check Left to right
            int n = c[0].length;
            for ( int i = 0 ; i< n ; i++ )
            {
                if( sc == i )continue;
                else if( c[sr][i] =='Q') return false;
            }

            // Check Up to Down

            for ( int j = 0 ; j < n ; j++ )
            {
                if( sr == j ) continue;
                else if ( c[j][sc] == 'Q') return false;
            }

            // Check diagonally from left up to right down
            int i = sr;
            int j = sc;
            while( i-1>=0&&j-1>=0 )
            {
                if(c[i-1][j-1]=='Q') return false;
                i--;
                j--;
            }


            i = sr;
            j = sc;
            while( i+1<= c[0].length-1 && j+1<=c[0].length-1)
            {
                if( c[i+1][j+1]=='Q')return false;
                j++;
                i++;
            }

            i = sr;
            j = sc;
            while( i-1>=0 &&j+1<=c[0].length-1)
            {
                if( c[i-1][j+1]=='Q') return false;
                i--;
                j++;
            }

            i = sr;
            j = sc;

            while( j-1>=0 &&i+1<=c[0].length-1)
            {
                if( c[i+1][j-1]=='Q')return false;
                i++;
                j--;
            }

            return true;

        }


        public List<String> copy( char[][] c )
        {
            StringBuilder s = new StringBuilder();
            List<String> ls = new ArrayList<>();
            for ( int i = 0 ; i<c.length ; i++ )
            {
                for ( int j = 0 ; j<c.length ; j++ )
                {
                    if( c[i][j]=='Q' )s.append("Q");
                    else s.append(".");
                }
                ls.add( s.toString());
                s.setLength(0);
            }
            return ls ;
        }
    }
}
