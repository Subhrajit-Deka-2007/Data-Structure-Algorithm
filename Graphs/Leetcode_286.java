package Graphs;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode_286
{
        public static void main(String[] args) {
            int INF = Integer.MAX_VALUE;

            int[][] test1 = {
                    {INF, -1, 0, INF},
                    {INF, INF, INF, -1},
                    {INF, -1, INF, -1},
                    {0, -1, INF, INF}
            };
            walls_gates(test1);


            printGrid(test1);

            int[][] expected1 = {
                    {3, -1, 0, 1},
                    {2, 2, 1, -1},
                    {1, -1, 2, -1},
                    {0, -1, 3, 4}
            };
            System.out.println("Match: " + java.util.Arrays.deepEquals(test1, expected1));
        }

        static void printGrid(int[][] grid) {
            for (int[] row : grid) {
                for (int val : row) {
                    System.out.print((val == Integer.MAX_VALUE ? "INF" : val) + "\t");
                }
                System.out.println();
            }
            System.out.println();
        }

        // paste your solution method here, e.g.:
        static int[][] walls_gates(int[][] rooms)
        {

            /**
             DFS APPROACH

            boolean [][] vis = new boolean[rooms.length][rooms[0].length];



            for ( int i = 0 ; i < rooms.length ; i++ )
            {
                for ( int j = 0 ; j < rooms[0].length ; j++ )
                {
                    if(rooms[i][j]==0)
                    {

                        dfs( rooms , i , j , rooms.length-1, rooms[0].length-1,0,vis);
                         vis[i][j] =true;
                    }
                }
            }
*/

            /** Now let's solve it using the BFS Approach */
            boolean [][] vis = new boolean[rooms.length][rooms[0].length];
            Queue<Integer> q = new ArrayDeque<>();
            // These is multipoint BFS means it has multiple entry points so, we will initially put all the entry points inside the queue
            for ( int i = 0 ; i < rooms.length ; i++) {
                for (int j = 0; j < rooms[0].length; j++) {
                    if (rooms[i][j] == 0) {
                        // In que we are adding three things rows, columns and the distance from it parent so initially the parent is parent of itself
                        // so the distance is 0
                        q.add(i);
                        q.add(j);
                        q.add(0);
                        vis[i][j] = true;
                    }
                }
            }
            System.out.println(q);
            bfs(q,rooms,vis);
            return rooms;
        }


        public static int[][] bfs(Queue<Integer>q , int [][] rooms , boolean [][] vis )
        {
            /** In each level we will check four directions --> left , right , up and down */

            int [][] direction = { {0,-1},{0,1},{-1,0},{1,0}};

            /** Now we will use a loop --> We can use a while loop also if we want ==> It will look while( q.isEmpty() )*/

            int k = 0;

            for ( ; !q.isEmpty() ; )
            {
                k = q.size();
                k/=3;
                /*
                The reason we have done because
                0, 2, 0,       3, 0, 0
                we need two not 6 as each triplet  is 3 element long so, we divide the total by 3 to get the number of triplets
                 */

                /**
                 we are storing current size of queue it is necessary if we use q.size() in the inner for loop the queue will grow and shrink
                 at each step so, we will never be able to get the correct answer.
                */
                int row  = 0;
                int col = 0;
                int dis  = 0;
                 /**
                  I don't want to create a variable on each iteration so, I am creating a variable out of the loop scope and the variable
                  will behave as a global variable for that scope
                 */
                for ( int i = 0 ; i < k ; i++  )
                {

                    // So Now we will put out the front element of the queue

                    row = q.poll();
                    col = q.poll();
                    dis = q.poll();

                    for ( int [] dir : direction )
                    {
                        int newRow = row+dir[0];
                        int newCol = col+dir[1];


                        if( newRow>=0 && newRow < rooms.length && newCol>=0 && newCol< rooms[0].length && !vis[newRow][newCol] && rooms[newRow][newCol]==Integer.MAX_VALUE) {
                            // Got a valid cell now put the value of its distance == parent cell distance + 1 {1 represents its own cell }
                            rooms[newRow][newCol] = dis + 1;
                            // Also mark it true so that for other cell we don't visited it again , it save us from infinite loop
                            vis[newRow][newCol] = true;

                            // Now put these cell inside the queue and also with its distance
                            q.add(newRow);
                            q.add(newCol);
                            q.add(rooms[newRow][newCol]);
                            /**
                             As we are using bfs so, it is guaranteed that if we are visiting any cell
                             we will visit it using the shortest distance.
                             */
                        }
                    }
                }

            }
            return rooms;
            /**
             * Time Complexity = O ( M*N) => EACH NODE WE ARE TRAVELLING ONLY ONCE
             * SPACE COMPLEXITY = O ( M*N) => ALL THE ELEMENTS ARE ZERO SO INITIALLY WE WILL PUT ALL THE ELEMENTS + M*N visited array
             */
        }





        static void dfs(int [][] rooms, int sr, int sc , int er , int ec , int dis , boolean[][] vis )
        {

            if( sr> er || sc>ec) return;
            else if( sr<0 || sc<0 )return;
            else if(vis[sr][sc])return;
            else if( rooms[sr][sc] == -1)return;

            vis[sr][sc] = true;
             rooms[sr][sc] = Math.min(rooms[sr][sc],dis);


            // Go Left
            dfs(rooms,sr,sc-1,er,ec,dis+1,vis);
            //Go right
            dfs(rooms,sr,sc+1,er,ec,dis+1,vis);
            //Go Up
            dfs(rooms,sr-1,sc,er,ec,dis+1,vis);
            //Go Down
            dfs(rooms,sr+1,sc,er,ec,dis+1,vis);
            vis[sr][sc] =false;
        }
    }

/** BFS Approach will be the best as it will travel each node only once and it will travel that node using the shortest distance
 * so time complexity will be O (M*N)
 * Space Complexity O(M*N)
  */
    /*
    Let's build it from scratch with actual numbers, not just algebra, so you can see exactly where the `−2` comes from.

## Start with a concrete grid: 3×3 (9 cells)

**Step 1: What's the longest possible path?**

A path can't revisit any cell (blocked by `vis`), so the longest possible path uses **all 9 cells**.

```
Longest path = N × M = 9 cells
```

**Step 2: How many moves does a 9-cell path have?**

If you visit 9 cells one after another, count the arrows between them:

```
cell1 → cell2 → cell3 → cell4 → cell5 → cell6 → cell7 → cell8 → cell9
```

Count the arrows: there are **8** arrows connecting 9 cells.

```
Moves = cells − 1 = 9 − 1 = 8
```

This is just a basic counting fact: `n` items in a sequence have `n − 1` gaps between them. (Like 5 fingers have 4 gaps between them.)

So in general: **Total moves = N×M − 1**

**Step 3: Out of those moves, which one is "special" (4 choices instead of 3)?**

Only the **very first move** (leaving the gate cell) has 4 possible directions, because nothing is blocking any direction yet — you haven't come from anywhere.

```
Move 1 (leaving gate):  4 choices  ← special, pulled out separately
Move 2:                 3 choices  ← "came from" direction is blocked
Move 3:                 3 choices
Move 4:                 3 choices
...
Move 8:                 3 choices
```

**Step 4: Count how many moves are left with 3 choices, after removing the 1 special move**

```
Total moves = 8
Minus the 1 special first move = 8 − 1 = 7
```

So **7 moves** have 3 choices each, for a 3×3 grid (9 cells).

**Step 5: Match this back to the formula**

```
N×M − 2 = 9 − 2 = 7 ✓
```

That matches! Because:

```
N×M − 2  =  (N×M − 1)  −  1
         =  [total moves]  −  [the one special first move]
         =  [moves with 3 choices]
```

## So to directly answer "how did N×M − 2 come":

```
Total moves in longest path        = N×M − 1     (cells − 1, basic counting)
Subtract the 1 special first move  = (N×M − 1) − 1 = N×M − 2
```

The `−1` (cells to moves) and the second `−1` (removing the special first move) combine into `−2` total.
    Space Complexity =
    Max recursion depth = N × M
    vis array space = O(N × M)

    Time Complexity  = O(3^(N×M))   ← exponential (total paths across all branches)
Space Complexity = O(N×M)       ← polynomial (only one path's depth stored at a time)
     */

