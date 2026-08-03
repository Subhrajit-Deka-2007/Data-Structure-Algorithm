package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class Leetcode_1584
{
    class Solution {
        int[] parent, rank;

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int rx = find(x), ry = find(y);
            if (rx == ry) return;
            if (rank[rx] < rank[ry]) parent[rx] = ry;
            else if (rank[rx] > rank[ry]) parent[ry] = rx;
            else { parent[ry] = rx; rank[rx]++; }
        }

        int kruskal(int V, int[][] edges) {
            Arrays.sort(edges, (a, b) -> a[2] - b[2]);
            parent = new int[V];
            rank = new int[V];
            for (int i = 0; i < V; i++) parent[i] = i;

            int totalWeight = 0, edgesUsed = 0;
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (find(u) != find(v)) {
                    union(u, v);
                    totalWeight += w;
                    edgesUsed++;
                    if (edgesUsed == V - 1) break;
                }
            }
            return totalWeight;
        }

        public int minCostConnectPoints1(int[][] points) {
            List<int[]> edges = new ArrayList<>();

            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    int dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                    edges.add(new int[]{i, j, dist});
                }
            }

            return kruskal(points.length, edges.toArray(new int[0][]));
        }

/*
Prim's:    O(V² log V) or O(E log V) depending on implementation — efficient when the graph is dense (which it is here, since every pair connects)
Kruskal's: O(E log E) for sorting all edges — here E = O(V²) since it's a complete graph, so this becomes O(V² log V) too
*/
        /* Prims with heap => O( V^2 log V ) */

        public int minCostConnectPoints2(int[][] points) {
            int n = points.length;
            boolean[] visited = new boolean[n];
            int[] minDist = new int[n];
            Arrays.fill(minDist, Integer.MAX_VALUE);
            minDist[0] = 0; // start at node 0

            int totalCost = 0;

            for (int i = 0; i < n; i++) {
                // Step 1: find unvisited node with smallest minDist — O(n) scan
                int u = -1;
                int best = Integer.MAX_VALUE;
                for (int v = 0; v < n; v++) {
                    if (!visited[v] && minDist[v] < best) {
                        best = minDist[v];
                        u = v;
                    }
                }

                // Step 2: lock it in
                visited[u] = true;
                totalCost += best;

                // Step 3: relax all unvisited neighbors — O(n) scan
                for (int v = 0; v < n; v++) {
                    if (!visited[v]) {
                        int dist = Math.abs(points[u][0] - points[v][0])
                                + Math.abs(points[u][1] - points[v][1]);
                        if (dist < minDist[v]) {
                            minDist[v] = dist;
                        }
                    }
                }
            }

            return totalCost;
        }

        /* Prims with array Better => O(N^2)*/

        public int minCostConnectPoints3(int[][] points) {
            int n = points.length;
            boolean[] visited = new boolean[n];
            int[] minDist = new int[n];
            Arrays.fill(minDist, Integer.MAX_VALUE);
            minDist[0] = 0; // start at node 0

            int totalCost = 0;

            for (int i = 0; i < n; i++) {
                // Step 1: find unvisited node with smallest minDist — O(n) scan
                int u = -1;
                int best = Integer.MAX_VALUE;
                for (int v = 0; v < n; v++) {
                    if (!visited[v] && minDist[v] < best) {
                        best = minDist[v];
                        u = v;
                    }
                }

                // Step 2: lock it in
                visited[u] = true;
                totalCost += best;

                // Step 3: relax all unvisited neighbors — O(n) scan
                for (int v = 0; v < n; v++) {
                    if (!visited[v]) {
                        int dist = Math.abs(points[u][0] - points[v][0])
                                + Math.abs(points[u][1] - points[v][1]);
                        if (dist < minDist[v]) {
                            minDist[v] = dist;
                        }
                    }
                }
            }

            return totalCost;
        }
    }
}
