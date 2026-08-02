package Graphs;

public class FloydWarshall
{
    public int[][] floydWarshall(int V, int[][] adjMatrix) {
        int[][] dist = new int[V][V];

        // Initialize: direct edges, 0 for self, infinity otherwise
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) dist[i][j] = 0;
                else dist[i][j] = adjMatrix[i][j] == 0 ? Integer.MAX_VALUE : adjMatrix[i][j];
                // (assumes 0 in adjMatrix means "no direct edge" — adjust representation as needed)
            }
        }

        // Try routing through every possible intermediate node k
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }


        /**
         * If we want to check negative cycle then
         */
        /*
         The reason we are checking the diagonal for the negative cycle as in positive cycle
         the node to itself is 0 which is the minimum no one can beat but in case of negative cycle
         it beats the zero which means the sum is less than zero
         And each cell dis[i][j] tell us the minimum cost of travelling from the ith node to the jth node
         and in the condition I am checking dis[i][k] + dis[k][j] < dis[i][j]
         It means moving from the ith node to kth node and from the kth node to jth node
         the cost of using these intermediate node k is less than the direct path from i to j


         dist[i][j] is not necessarily the direct edge from i to j. It's whatever the current best known
          cost from i to j is at this point in the algorithm — which might already be:
         the direct edge (if no better route has been found yet), or
         some multi-hop route discovered in an earlier k pass (e.g., from k=0 or k=1's updates)
         */
        boolean hasNegativeCycle = false;

        for (int i = 0; i < V; i++) {
            if (dist[i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }
        return dist;
    }
/*
Time Complexity = O( V^3)
Space Complexity = O(V+E)
 */
}
/**
 * 	Scope	Handles negative weights?	Time
 * Dijkstra's	Single source	No	O(E log V)
 * Bellman-Ford	Single source	Yes (detects negative cycles too)	O(V × E)
 * Floyd-Warshall	All pairs	Yes	O(V³)



 Comparison Table
 Feature	                     Dijkstra's	                                                 Bellman-Ford	                                                              Floyd-Warshall
 Problem type	                 Single-source shortest path	                             Single-source shortest path	                                              All-pairs shortest path
 Directed graphs	             ✅ Yes                                                      ✅ Yes	                                                                      ✅ Yes
 Undirected graphs	             ✅ Yes (treat each edge as two directed edges)	             ✅ Yes (same trick)                        	                              ✅ Yes (same trick)
 Negative edge weights           ❌ No — breaks correctness (greedy assumption fails)	     ✅ Yes, handles them correctly	                                              ✅ Yes, handles them correctly
 Negative weight cycles	         ❌ Cannot detect, undefined behavior if present	         ✅ Detects them (extra Vth pass)	                                          ✅ Detects them (check if dist[i][i] < 0 after running)
 Core mechanism	                 Greedy + min-heap, finalize nearest vertex	                  Brute-force relax all edges, V−1 times     	                              Dynamic programming: "can I improve i→j by routing through k?"
 Time Complexity	             O(E log V) with binary heap	                              O(V · E)	                                                                  O(V³)
 Space Complexity	             O(V + E)	                                                  O(V) or O(E) for edge list	                                              O(V²) for the distance matrix
 When it fails / can't be used	Any negative edge present	                                  Negative cycle → paths undefined (but it tells you)	                      Negative cycle → paths undefined (but it tells you)
 Best for	                    Sparse graphs, non-negative weights, single source	          Graphs with negative weights, need cycle detection, single source	          Dense graphs, need distances between every pair, V is small (≤ ~400-500)








 */