package Graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/** Number of connected components in Undirected Graph
 *
  Input: n = 5, edges = [[0,1],[1,2],[3,4]]

  Graph:
  0 - 1 - 2       3 - 4
  Output: 2
 (two separate components: {0,1,2} and {3,4})


 Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]

 Graph:
 0 - 1 - 2 - 3 - 4

 Output: 1
 (all nodes connected in one component)

 */
public class Leetcode_323
{
    public static void main(String[] args)
    {
        int n = 5;
        int [][] edges = {{0,1},{1,2},{3,4}};
        /*
        We have to find the number of connected components in these
        Undirected Graph.
        First I need to create the adjacency list or adjacency matrix

         */
        int [][] adjMatrix = new int[n][n];

        int u = 0;
        int v = 0;

        boolean [] vis = new boolean[n];
        for ( int [] ele : edges)
        {
            u = ele[0];
            v = ele[1];
            adjMatrix[u][v]++;
            adjMatrix[v][u]++;

        }
        for ( int i = 0 ; i < adjMatrix.length ; i++ ) {
            for (int j = 0; j < adjMatrix[0].length; j++) {
                System.out.print(adjMatrix[i][j]+" ");
            }
            System.out.println();
        }
        int count = 0;
        for ( int i = 0 ; i < n ; i++)
        {

             /**  Solve using DFS Approach.
            if(!vis[i]){
                count+=dfs(i,adjMatrix,vis);

            }
        }
        System.out.println(count);
*/

            /**
             * Solve using BFS
*/
            if(!vis[i]) {
                count += bfs(i, adjMatrix, vis);
            }


        }

        System.out.println(count);
    }

    public  static int  dfs( int ele, int [][] adjMatrix , boolean[]vis )
    {

        vis[ele]= true;
        for ( int i = 0; i < adjMatrix[0].length ; i++ )
        {

            if( ele == i )continue;
            else if ( !vis[i] && adjMatrix[ele][i]!=0 )dfs(i,adjMatrix,vis);
        }
        return 1;
    }



    public static int bfs( int ele , int [][] adjMatrix , boolean [] vis )
    {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(ele);
        // Adding the first element in the queue

        int element = 0;
        while ( !q.isEmpty() )
        {
            element = q.poll();
            vis[element]= true;

            for ( int i = 0 ; i < adjMatrix[0].length; i++ )
            {
                 if(element == i )continue;
                 if( !vis[i] && adjMatrix[element][i]!=0) q.add( i);

            }
        }
        return 1;
    }
    /*
    Time Complexity = O ( V^2) BECAUSE WE ARE USING AN ADJ MATRIX
    SPACE COMPLEXITY = O ( V{ ALL THE V-1 ELEMENTS IF THEY ARE CONNECTED TO ALL V-1 ELEMENTS } + V^2 {FOR ADJ MATRIX } )

     */
}






    /**
     * Time Complexity is O(V^2) as I am using a adj matrix
     * Space Complexity =
     * Good catch — but there's something more important here first: **your code uses an adjacency MATRIX, not an adjacency list.** That changes the complexity completely. Let me clear up both things.
     *
     * ---
     *
     * ### 1. Your code's actual complexity: O(V²), not O(V+E)
     *
     * ```java
     * for (int i = ele; i < adjMatrix[0].length; i++)   // <- this loops V times, ALWAYS
     * ```
     *
     * This loop runs across **all V columns** every single time `dfs()` is called, regardless of how many actual edges the node has. It doesn't matter if node `ele` has 0 edges or 100 edges — you still scan the entire row.
     *
     * ```
     * dfs(node 0): scan V columns
     * dfs(node 1): scan V columns
     * dfs(node 2): scan V columns
     * ...
     * dfs(node V-1): scan V columns
     *
     * Total = V × V = O(V²)
     * ```
     *
     * This is the fundamental tradeoff of **adjacency matrix** representation:
     * ```
     * Adjacency Matrix -> O(V²) traversal (scan full row every time)
     * Adjacency List   -> O(V+E) traversal (only scan actual neighbors)
     * ```
     *
     * So with matrix-based DFS, you should say **O(V²)**, full stop. The V+E notation doesn't apply here at all — that notation is specifically for **adjacency list** traversal.
     *
     * ---
     *
     * ### 2. When is it V+E vs V+2E? (This applies to adjacency LIST only)
     *
     * ```
     * Building the adjacency list itself:
     *
     * Directed edge (u -> v):     add v to list[u]                    -> 1 entry created
     * Undirected edge (u - v):    add v to list[u], add u to list[v]  -> 2 entries created
     * ```
     *
     * ```java
     * // Directed graph
     * graph.get(u).add(v);              // 1 insertion per edge -> total E insertions
     *
     * // Undirected graph
     * graph.get(u).add(v);
     * graph.get(v).add(u);              // 2 insertions per edge -> total 2E insertions
     * ```
     *
     * So during **traversal** (DFS/BFS), when you visit a node and loop through its neighbor list, you end up processing:
     * ```
     * Directed graph:    E total neighbor-list entries across all nodes  -> O(V + E)
     * Undirected graph:  2E total neighbor-list entries across all nodes -> O(V + 2E)
     * ```
     *
     * **But** — since 2E is still just a constant multiple of E, in Big-O we drop the constant:
     * ```
     * O(V + 2E) = O(V + E)     (2 is a constant, gets dropped)
     * ```
     *
     * That's why you'll see **both** written in textbooks/solutions for undirected graphs — `V+2E` is the more "literal" count of work done, but `V+E` is the simplified Big-O form. They mean the same asymptotic thing. People write V+2E mainly to be pedagogically explicit that undirected edges get double-counted, not because it's a different complexity class.
     *
     * ---
     *
     * ### Quick summary table
     *
     * | Representation | Graph type | Traversal cost |
     * |---|---|---|
     * | Adjacency Matrix | directed or undirected | **O(V²)** always |
     * | Adjacency List | directed | O(V + E) |
     * | Adjacency List | undirected | O(V + 2E) → simplified to O(V + E) |
     *
     * Your code falls in that first row — it's O(V²) regardless of directed/undirected, because the matrix forces you to scan every possible column.
     *
     * Want me to show you the adjacency-list version of this same DFS so you can see the O(V+E) behavior directly in code?



     Let's break down space complexity for your specific code, then the general cases.

     ### Your code's space complexity

     ```java
     public static int dfs(int ele, int[][] adjMatrix, boolean[] vis) {
     vis[ele] = true;
     for (int i = ele; i < adjMatrix[0].length; i++) {
     if (ele == i) continue;
     else if (!vis[i] && adjMatrix[ele][i] != 0) dfs(i, adjMatrix, vis);
     }
     return 1;
     }
     ```

     Three things to account for:

     ```
     1. adjMatrix itself:     O(V²)   <- V x V matrix, passed by reference (not extra per call)
     2. vis[] array:          O(V)    <- one boolean per node, passed by reference too
     3. Recursion call stack: O(V)    <- worst case, in the worst case
     ```

     **Recursion stack — why O(V)?**
     Each recursive call to `dfs()` adds a new frame to the call stack. In the worst case (e.g., a graph shaped like a long chain: 0-1-2-3-...-V-1), you'd recurse V times deep before unwinding:

     ```
     dfs(0) calls dfs(1) calls dfs(2) calls dfs(3) ... calls dfs(V-1)
     ```

     Stack depth = V frames = **O(V)** space.

     ---

     ### Total space complexity

     ```
     adjMatrix:  O(V²)   <- this is the dominant term
     vis:        O(V)
     call stack: O(V)

     Overall: O(V²)
     ```

     Since the adjacency matrix itself already takes O(V²) to store, that dominates everything else. So for matrix-based graphs:

     ```
     Space Complexity = O(V²)
     ```

     ---

     ### General comparison (adjacency list vs matrix)

     | Representation | Storage space | vis[] array | Call stack (worst case) | Total |
     |---|---|---|---|---|
     | Adjacency Matrix | O(V²) | O(V) | O(V) | **O(V²)** |
     | Adjacency List | O(V+E) | O(V) | O(V) | **O(V+E)** |

     This is actually the *real* reason adjacency lists are preferred for sparse graphs (few edges relative to nodes) — you save massively on space. If `E << V²` (e.g., E = V, like in your redundant-connection problem), an adjacency list costs O(V) space while a matrix costs O(V²) — a huge difference at scale.

     **Quick intuition check:** if V = 1000 and the graph is sparse (say E = 999, basically a tree):
     ```
     Adjacency Matrix: 1000 x 1000 = 1,000,000 cells stored
     Adjacency List:   1000 + 999 ≈ 2000 entries stored
     ```
     That's a 500x difference in memory — this is exactly why matrix representation is usually avoided for large sparse graphs in interviews, unless the problem specifically calls for O(1) edge-lookup (like "does edge (u,v) exist?").


     */

