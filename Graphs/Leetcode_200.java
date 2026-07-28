package Graphs;

import java.util.HashSet;
import java.util.Set;

public class Leetcode_200
{
    public class UnionFind
    {

        int[] parent;
        int[] size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // everyone is their own leader initially
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return; // already same group

            // union by size: attach smaller group under bigger group
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }
    class Solution {
        public int numIslands(char[][] grid)
        {
/*
index = row * cols + col        // encode
row = index / cols               // decode: how many full rows fit before this index
col = index % cols               // decode: leftover after removing those rows
*/
            int rows = grid.length;
            int cols = grid[0].length;
            UnionFind uf = new UnionFind(rows * cols);
            //  int index = i * cols + j;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        int index = i * cols + j;

                        if (j + 1 < cols && grid[i][j+1] == '1') {
                            uf.union(index, index + 1);
                        }
                        if (i + 1 < rows && grid[i+1][j] == '1') {
                            uf.union(index, index + cols);
                        }
                    }
                }
            }

            Set<Integer> islandRoots = new HashSet<>();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == '1') {
                        islandRoots.add(uf.find(i * cols + j));
                    }
                }
            }

            return islandRoots.size();

        }
    }
/**
 Time	                                      Space
 DFS	        O(rows×cols)	                    O(rows×cols) worst case (recursion stack)
 BFS   	    O(rows×cols)	                    O(rows×cols) worst case (queue)
 Union-Find	O(rows×cols × α(n)) ≈ O(rows×cols)	O(rows×cols) (fixed, not traversal-dependent)

 Total = O(n) + O(rows×cols×α(n)) + O(rows×cols×α(n))
 = O(rows×cols) + O(rows×cols×α(n))
 = O(rows×cols×α(n))          [since rows×cols×α(n) dominates rows×cols, as α(n)≥1]
 ≈ O(rows×cols)
 */
}
