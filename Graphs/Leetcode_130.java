package Graphs;

public class Leetcode_130
{
    class Solution {
        public void solve1(char[][] board)
        {
            for ( int i = 0 ; i< board.length ; i++ )
                for ( int j = 0 ; j < board[0].length ; j++ )
                    if( ( i == 0 || j == 0 || i == board.length -1 || j == board[0].length-1 ) && board[i][j]=='O')
                        dfs(board, i, j,board.length-1, board[0].length-1 );

            // Now transforming the hash mark element into X
            for( int i = 0 ; i< board.length ; i++){
                for( int j = 0 ; j< board[0].length ; j++ ){
                    if(board[i][j]=='O')board[i][j]='X';
                    else if (board[i][j] =='#')board[i][j]='O';
                }
            }
/*
Time Complexity = O ( M*N)
Space Complexity = O (1)
*/

        }
        public void dfs( char[][] board , int sr, int sc , int er , int ec )
        {
            if( sr>er || sc>ec )return;
            else if( sr < 0 || sc < 0 )return;
            else  if(board[sr][sc]=='#')return;
            else if( board[sr][sc]=='X') return;
            board[sr][sc]='#';

            // move left
            dfs(board,sr,sc-1,er,ec);
            //move right
            dfs(board,sr,sc+1,er,ec);
            //Move down
            dfs(board,sr+1,sc,er,ec);
            //Move Up
            dfs(board,sr-1,sc,er,ec);


        }


        private int[] parent;
        private int[] rank_;

        public void solve(char[][] board) {
            if (board == null || board.length == 0) return;

            int m = board.length;
            int n = board[0].length;
            int dummy = m * n; // the "Border" node

            parent = new int[m * n + 1];
            rank_ = new int[m * n + 1];
            for (int i = 0; i <= m * n; i++) {
                parent[i] = i; // everyone starts as their own root
            }

            // Step 1: union border O's with dummy
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] != 'O') continue;

                    boolean isBorder = (r == 0 || r == m - 1 || c == 0 || c == n - 1);
                    int id = r * n + c;

                    if (isBorder) {
                        union(id, dummy);
                    }

                    // Step 2: union with right neighbor and down neighbor if also 'O'
                    if (r + 1 < m && board[r + 1][c] == 'O') {
                        union(id, (r + 1) * n + c);
                    }
                    if (c + 1 < n && board[r][c + 1] == 'O') {
                        union(id, r * n + c + 1);
                    }
                }
            }

            // Step 3: final pass — flip captured O's
            int dummyRoot = find(dummy);
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (board[r][c] == 'O') {
                        int id = r * n + c;
                        if (find(id) != dummyRoot) {
                            board[r][c] = 'X'; // not connected to border -> captured
                        }
                    }
                }
            }
        }

        private int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        private void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) return;

            // union by rank
            if (rank_[rootA] < rank_[rootB]) {
                parent[rootA] = rootB;
            } else if (rank_[rootA] > rank_[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank_[rootA]++;
            }
        }
    }
/*
Time Complexity = O ( M*N )
Space Complexity = O (M*N)
*/
}
