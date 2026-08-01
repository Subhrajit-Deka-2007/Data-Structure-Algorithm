package Graphs;

public class Leetcode_261
{
    /**
    Note: Also a locked/premium problem, but very commonly asked.

        Problem:
    You have a graph of n nodes labeled from 0 to n-1. You're given an integer n and a list of edges where edges[i] = [ai, bi] indicates there's an undirected edge between nodes ai and bi.

        Return true if the edges given form a valid tree, and false otherwise.

    A valid tree means:

    All n nodes are connected (one single component)
    There are no cycles

        (Recall: a tree with n nodes must have exactly n-1 edges — this is a quick first check you can use before
     even running DFS/BFS.)


     Same as LeetCode : 684
     */
    public static void main(String[] args)
    {
        int [][] edges = {{0,1},{1,2},{2,3},{1,3},{1,4}};
        System.out.println(" Valid Tree "+ validTree(edges) );
    }
    public static boolean validTree(int[][] edges) {
        /** Solve using DSU(Disjoint Set Union ) */
        int n = edges.length;
        int [] parent ,size;
        parent = new int[n+1];// as 1 based indexing
        size = new int [n+1];
        for(int i =1;i<n+1;i++){
            parent[i]=i;
            size[i]=1;
        }
        int [] ans = new int [2];
        for(int [] arr:edges){
            int u = arr[0],v = arr[1];
            if(leader(u,parent)==leader(v,parent)){
                // if there is already same so
//                ans[0] =u;
//                ans[1] =v;
//                break;
                return false;
            }else union(u,v,parent,size);
        }
        return true;
    }
    /**======================================     DSU START ================================= */
    static int leader(int u, int [] parent){
        if(parent[u]==u) return u;
        else return parent[u] = leader(parent[u],parent);
        /* we will also make the leader as parent on backtracking we call it path compression */
    }
   static  void union(int u, int v,int [] parent,int [] size){
        u = leader(u,parent);
        v = leader(v,parent);
        if(u!=v){
            /* We only do union when leader are differnet and also we do union on the basis of size */
            if(size[u]>size[v]){
                parent[v]= u;
                size[u]+=size[v];
            }else{
                parent[u]=v;
                size[v]+=size[u];
            }
        }
    }
}

