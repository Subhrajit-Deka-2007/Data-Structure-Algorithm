package Graphs;
import java.util.Arrays;
public class BellManFordAlgorithm
{
    public int[] bellmanFord(int V, int[][] edges, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        //round k only checks paths that use exactly k edges, nothing else."


        // Step 1: relax all edges V-1 times
        for (int i = 0; i < V - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], weight = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }

        // Step 2: one more pass to detect negative cycles
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                // a distance improved even after V-1 rounds → negative cycle exists
                return null; // or throw, or return a sentinel indicating "no valid answer"
            }
        }

        return dist;
    }

/*
Bellman-Ford guarantees: if a node's shortest path requires at most k edges to achieve the minimum cost,
then after round k finishes, that node's dist[] value will correctly show that minimum."
 */

}
/**
  Time: O(V × E)
  Space: O(V)



 Bellman-Ford (Single-Source, handles negative weights)
 Runs V-1 rounds. Each round checks all E edges once (relax if dist[u]+w < dist[v]).
 Round k guarantees: dist[v] is correct for any path using at most k edges (not "the k-th edge" — a cumulative cap on total path length).
 Path with k edges touches k+1 nodes total, k-1 intermediate nodes.
 V-1 rounds suffice because the longest possible simple path has V-1 edges.
 Negative cycle check: run one extra (V-th) round — if anything still relaxes, a negative cycle exists.





 A valid shortest path (no cycles) can use at most V-1 edges, since it can visit each of the V vertices at most once.
 So after V-1 full rounds of relaxing every edge, every legitimate shortest path has already been found — nothing should
 improve further. Step 2 exploits this fact: it does one extra round and checks if anything still improves. If nothing does,
 the graph behaved exactly as expected. If something does improve, that's only possible if a path is using more than V-1 edges —
 which can only happen if you're going around a loop repeatedly, and that loop only helps if it has negative total
 weight (each trip around it makes the "path" cheaper, forever).




 Let's trace both scenarios concretely — no negative cycle, and then a genuine negative cycle — using the same graph structure, so you can see exactly where Step 2's check diverges.

 ## Why V-1 rounds is the boundary that matters

 A valid shortest path (no cycles) can use at most `V-1` edges, since it can visit each of the `V` vertices at most once. So after `V-1` full rounds of relaxing every edge, every legitimate shortest path has already been found — nothing *should* improve further. **Step 2 exploits this fact**: it does one extra round and checks if anything *still* improves. If nothing does, the graph behaved exactly as expected. If something *does* improve, that's only possible if a path is using **more than `V-1` edges** — which can only happen if you're going around a loop repeatedly, and that loop only helps if it has **negative total weight** (each trip around it makes the "path" cheaper, forever).

 ## Case 1 — No negative cycle

 ```
 V = 3
 edges = [[0,1,4], [1,2,-2], [0,2,10]]
 source = 0
 ```

 **Rounds (V-1 = 2 rounds):**

 ```
 dist = [0, MAX, MAX]

 Round 1:
 edge(0,1,4): dist[0]+4=4 < MAX → dist[1]=4
 edge(1,2,-2): dist[1]=MAX still (wait — dist[1] was just set to 4 in this same pass, order matters here since edges list processes 0,1,4 first)
 ```

 Let me redo this carefully, processing edges strictly in the order given, within each round.

 ```
 dist = [0, MAX, MAX]

 Round 1:
 edge(0,1,4): dist[0]+4=4 < dist[1](MAX) → dist[1]=4
 edge(1,2,-2): dist[1]+(-2)=4-2=2 < dist[2](MAX) → dist[2]=2
 edge(0,2,10): dist[0]+10=10, not < dist[2](2) → no change

 dist = [0, 4, 2]

 Round 2:
 edge(0,1,4): 0+4=4, not < dist[1](4) → no change
 edge(1,2,-2): 4-2=2, not < dist[2](2) → no change
 edge(0,2,10): 10, not < 2 → no change

 dist unchanged = [0, 4, 2]
 ```

 **Step 2 — the extra check pass:**
 ```
 edge(0,1,4): 4, not < dist[1](4) → no improvement
 edge(1,2,-2): 2, not < dist[2](2) → no improvement
 edge(0,2,10): 10, not < 2 → no improvement

 Nothing improved → no negative cycle. Return dist = [0, 4, 2].
 ```

 **This is a graph with a negative EDGE (1→2 has weight -2), but NOT a negative CYCLE** — the values stabilize correctly, and the answer is legitimate.

 ## Case 2 — A genuine negative cycle

 ```
 V = 3
 edges = [[0,1,1], [1,2,-3], [2,1,1]]
 source = 0
 ```

 Notice: `1 → 2 → 1` forms a loop with total weight `-3 + 1 = -2` — going around it repeatedly makes "distance to node 1" keep shrinking forever.

 **Rounds (V-1 = 2):**

 ```
 dist = [0, MAX, MAX]

 Round 1:
 edge(0,1,1): dist[0]+1=1 < MAX → dist[1]=1
 edge(1,2,-3): dist[1]-3=1-3=-2 < MAX → dist[2]=-2
 edge(2,1,1): dist[2]+1=-2+1=-1 < dist[1](1) → dist[1]=-1

 dist = [0, -1, -2]

 Round 2:
 edge(0,1,1): 0+1=1, not < dist[1](-1) → no change
 edge(1,2,-3): -1-3=-4 < dist[2](-2) → dist[2]=-4
 edge(2,1,1): -4+1=-3 < dist[1](-1) → dist[1]=-3

 dist = [0, -3, -4]
 ```

 **Step 2 — the extra check pass:**
 ```
 edge(0,1,1): 0+1=1, not < dist[1](-3) → no improvement
 edge(1,2,-3): -3-3=-6 < dist[2](-4) → IMPROVEMENT DETECTED!
 ```

 **The moment this check finds an improvement, the function returns `null`** — signaling a negative cycle exists.

 ## Why this improvement STILL happening after V-1 rounds is exactly the signal

 In Case 1, everything stabilized by Round 2 (matching `V-1=2`) — no further improvement was possible, confirming the
 shortest paths were genuinely final. In Case 2, the distances **kept shrinking** even after the "should be enough"number of
 rounds — proving that the path being tracked isn't a simple, non-repeating shortest path anymore;
 it's implicitly looping through the negative cycle over and over, and could theoretically keep improving forever
 (there's no actual minimum — you could go around the `1→2→1` loop infinitely and get an arbitrarily negative number).

 ## The one-sentence summary

 If the graph has no negative cycle, all shortest paths are fully found within `V-1` rounds, and one more
 pass changes nothing. If a negative cycle exists, distances keep improving indefinitely no matter how many
 rounds you run — so checking for "did anything STILL improve after the V-1 rounds that should have been enough"
 is exactly how you catch it.

 */
