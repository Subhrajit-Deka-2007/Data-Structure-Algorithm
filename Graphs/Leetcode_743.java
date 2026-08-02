package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Leetcode_743
{

    class Solution {
        public int networkDelayTime(int[][] times, int n, int k)
        {
        /*
         First we have to create and Adjacency List
         */
            List<List<int[]>> adjList = new ArrayList<>();
            for ( int i = 0 ; i<=n; i++ )adjList.add(new ArrayList<>());

            for ( int [] time : times )adjList.get(time[0]).add(new int[] {time[1],time[2]});

            int [] minDis = new int[n+1];
       /* Boolean array is unuseful as we are aclling the dfs only when the weight sum is less
        boolean [] vis = new boolean[n+1];
        */

            Arrays.fill(minDis,Integer.MAX_VALUE);
            minDis[0] = 0;
            minDis[k]=0;
            dfs( adjList , n , k,minDis );

            int max = 0 ;
            for ( int ele : minDis)
            {

                if( ele == Integer.MAX_VALUE )return -1 ;
                max = Math.max(ele,max);
            }

            return max;

        }
        public void dfs( List<List<int[]>> ls , int n , int k , int [] minDis )
        {
            //  System.out.println(k);

            for ( int [] node : ls.get(k))
            {

                // 0th index node and 1st index is the weight between them
                if( minDis[k] + node[1] < minDis[node[0]])
                {
                    minDis[node[0]] = minDis[k]+node[1];
                    dfs(ls, n , node[0] , minDis);
                }
            }

        }
    }
}
/*



Time  = (v-1)!+(v-1)!+-----+ v times
      => v *(v-1)=> v!

Space = O(V+E)  for adjacency list
      + O(V)    for minDis array
      + O(V)    recursion stack depth (single chain, bounded since
                 weights are positive → distance strictly decreases
                 along any one call chain)




 */
