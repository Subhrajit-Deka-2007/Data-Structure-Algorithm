package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Arrays;
public class Dijkstra
{
    /** Dijkstra Algo can be applied for both directed and undirected graph but weight should not be negative . Also, it can
     * handle cycles

     Let's break this down piece by piece with a concrete trace, since this line is the single most important safety check in the whole algorithm.

     ### What the line does

     ```java
     if (d > dist[node]) continue;
     ```

     This says: **"If the distance value I just popped (`d`) is worse (bigger) than the current best-known distance for this node (`dist[node]`), then this popped entry is stale garbage — skip it, don't process its neighbors."**

     ---

     ### Why would `d` ever NOT match `dist[node]`?

     Remember from our earlier trace: a node can be **pushed to the priority queue multiple times**, once for every time a cheaper path to it is discovered. But we never **remove** the old, worse entries from the queue when we find something better — that would be expensive. Instead, we just let the stale entries sit in the queue, and check them at pop time.

     ```
     Example: node 3 gets pushed TWICE over the course of the algorithm

     First push:  dist[3] = 20  ->  PQ now contains (20, node 3)
     Later on, we find a cheaper path:
     Second push: dist[3] = 13  ->  PQ now contains (13, node 3) AND the old (20, node 3)

     At this point, dist[3] = 13 (the array holds the latest/best value)
     But the PQ still has BOTH (13, node3) and (20, node3) sitting in it
     ```

     ---

     ### What happens when we pop each one

     The priority queue always pops the **smallest** value first, so:

     ```
     Pop (13, node 3) FIRST:
     d = 13, dist[node] = dist[3] = 13
     Check: is d (13) > dist[node] (13)?  -> NO, they're equal
     -> Don't skip. Process this node's neighbors normally. This is the "real," final answer.

     ... later ...

     Pop (20, node 3) SECOND (eventually, since it's a bigger number, it sits deeper in the queue):
     d = 20, dist[node] = dist[3] = 13   (dist[3] was already updated to 13 earlier!)
     Check: is d (20) > dist[node] (13)?  -> YES
     -> continue (skip this iteration entirely). This is the "stale," outdated answer.
     ```

     ---

     ### Why skipping stale entries matters (what breaks without this check)

     If you **removed** this check entirely:

     ```java
     // WITHOUT the staleness check (buggy version)
     while (!pq.isEmpty()) {
     int[] curr = pq.poll();
     int d = curr[0], node = curr[1];
     // no check here!

     for (int[] neighbor : graph.get(node)) {
     // ... relax neighbors using 'd' as if it were dist[node] ...
     }
     }
     ```

     You would end up **re-processing node 3's neighbors a second time**, using the outdated distance `20` instead of the correct `13`. This could:
     1. **Waste time** — redundant work re-exploring neighbors you already explored correctly
     2. **Potentially cause wrong updates** — if `d=20` gets used to "relax" some neighbor, you might push an incorrect, worse distance into the queue for that neighbor, based on stale data

     The staleness check is what keeps the algorithm both **correct** and **efficient** — it's a cheap O(1) check that says "I've already found something better for this node, so this old queue entry is garbage, throw it away."

     ---

     ### Visual summary

     ```
     dist[] array:  always holds the CURRENT BEST known distance for each node
     PQ entries:    a HISTORY of every distance ever proposed for each node (including outdated ones)

     When you pop an entry (d, node):
     if d == dist[node]  -> this IS the current best, it's legit, process it
     if d >  dist[node]  -> this is OLD news, dist[node] has since improved, IGNORE it
     (d < dist[node] can never happen, since dist[] only ever decreases)
     ```

     That's the entire mechanism — it's essentially **lazy deletion**: instead of actively removing stale entries from the middle of the heap (which is expensive),
     you just let them sit there and cheaply discard them the moment they surface at the top.



     */
    public int[] dijkstra(int n, int[][] edges, int src)
    {

        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) graph.get(e[0]).add(new int[]{e[1], e[2]});

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], node = curr[1];
            if (d > dist[node]) continue;

            for (int[] neighbor : graph.get(node)) {
                int next = neighbor[0], weight = neighbor[1];
                if (dist[node] + weight < dist[next]) {
                    dist[next] = dist[node] + weight;
                    pq.offer(new int[]{dist[next], next});
                }
            }
        }
        return dist;
    }
}
/**
 * Let's do this fully step by step, using the 5-node complete graph as our concrete anchor.
 *
 * ## Setup
 *
 * ```
 * V = 5 nodes
 * E = 10 edges (complete graph: V(V-1)/2 = 5×4/2 = 10)
 * ```
 *
 * ---
 *
 * ## Step 1: Building the adjacency list
 *
 * ```java
 * List<List<int[]>> graph = new ArrayList<>();
 * for (int i = 0; i < n; i++) graph.add(new ArrayList<>());   // O(V)
 * for (int[] e : edges) graph.get(e[0]).add(new int[]{e[1], e[2]});  // undirected -> 2 inserts/edge
 * ```
 *
 * ```
 * Initialize V empty lists:        O(V) = O(5)
 * Insert each edge (both directions): O(2E) = O(20)
 *
 * Step 1 total: O(V + 2E) = O(V + E) = O(5 + 10) = O(15)
 * ```
 *
 * ---
 *
 * ## Step 2: Priority queue pushes — count them explicitly
 *
 * Every time `dist[node] + weight < dist[neighbor]` is true, we push. In the **worst case**, every single edge triggers exactly one improving push (this is the assumption that defines "worst case" — not every graph does this, but a dense graph with the right weight ordering can).
 *
 * ```
 * Number of edges = 10
 * Worst case: every edge causes 1 push -> up to 10 pushes total
 *
 * Step 2 total pushes: O(E) = O(10)
 * ```
 *
 * ---
 *
 * ## Step 3: Priority queue pops
 *
 * Every push eventually gets popped (even stale ones get popped and discarded).
 *
 * ```
 * Total pops ≈ total pushes = O(E) = O(10)
 * ```
 *
 * ---
 *
 * ## Step 4: Cost per push/pop operation
 *
 * The PQ can grow up to size E (10 in our case) before shrinking. Each `offer()`/`poll()` on a heap of size k costs O(log k).
 *
 * ```
 * Max PQ size ≈ E = 10
 * Cost per operation: O(log E) = O(log 10) ≈ O(3.3)
 * ```
 *
 * ---
 *
 * ## Step 5: Total cost of all PQ operations
 *
 * ```
 * (pushes + pops) x (cost per operation)
 * = O(E) x O(log E)
 * = O(10) x O(log 10)
 * = O(E log E)
 * ```
 *
 * ---
 *
 * ## Step 6: Neighbor-list traversal cost (the "for neighbor" loop)
 *
 * Across the whole algorithm, every entry in the adjacency list gets read at most once per time its owning node is popped as non-stale. In the worst case this totals the full adjacency list size:
 *
 * ```
 * O(2E) = O(20)  ->  simplifies to O(E)
 * ```
 *
 * ---
 *
 * ## Step 7: Add it all up
 *
 * ```
 * Build graph:            O(V + E)   = O(15)
 * PQ pushes+pops+cost:     O(E log E) = O(10 x 3.3) ≈ O(33)
 * Neighbor traversal:      O(E)       = O(10)
 *
 * Total = O(V + E) + O(E log E) + O(E)
 *       = O(E log E)          <- dominates, since log E > 1 and E ≥ V here
 * ```
 *
 * ## Final Time Complexity
 *
 * ```
 * Time Complexity: O(E log E)
 *                  equivalently O((V + E) log V)   [since log E ≤ 2 log V]
 * ```
 *
 * ---
 *
 * ## Space Complexity — step by step
 *
 * ```
 * Adjacency list storage:   O(V + 2E) = O(V + E) = O(15)   <- stores all edges both directions
 * dist[] array:              O(V) = O(5)
 * Priority queue (worst case size): O(E) = O(10)   <- can hold up to one entry per edge
 *
 * Total Space = O(V + E) + O(V) + O(E)
 *             = O(V + E)
 * ```
 *
 * ## Final Space Complexity
 *
 * ```
 * Space Complexity: O(V + E)
 * ```
 *
 * ---
 *
 * ## Summary table (using our concrete numbers)
 *
 * | Component | Cost | With V=5, E=10 |
 * |---|---|---|
 * | Build adjacency list | O(V + E) | 15 |
 * | PQ pushes | O(E) | 10 |
 * | PQ pops | O(E) | 10 |
 * | Cost per PQ op | O(log E) | ~3.3 |
 * | Total PQ cost | O(E log E) | ~33 |
 * | Neighbor traversal | O(E) | 10 |
 * | **Total Time** | **O(E log E)** | dominated by ~33 |
 * | **Total Space** | **O(V + E)** | 15 |
 *
 * The key mechanical fact driving all of this: **the priority queue can hold up to E entries (not V)**,
 * because a single node can be pushed multiple times as cheaper paths are discovered — that's what pulls
 * the complexity to `E log E` instead of the `V log V` you'd get if
 * each node were guaranteed to enter the queue only once (like in plain BFS).
 *
 *
 * Good catch — let me connect this directly back to the math you already worked through earlier, so you can see exactly where the swap comes from.
 *
 * ### Recall what we proved earlier
 *
 * ```
 * E ≤ V²          (max edges in a simple graph)
 * log(E) ≤ log(V²) = 2 log(V)
 * So: O(log E) = O(log V)      <- same complexity class, differ by constant factor ≤2
 * ```
 *
 * ### Now substitute that directly into our formula
 *
 * We calculated:
 * ```
 * Total Time = O(E log E)
 * ```
 *
 * Since `log E` and `log V` are the same order (just proven), we can **replace** `log E` with `log V` anywhere it appears, without changing the complexity class:
 *
 * ```
 * O(E log E)  =  O(E log V)      <- swap log E -> log V using the bound above
 * ```
 *
 * ### Why does the extra "+V" appear too?
 *
 * Look back at Step 1 and Step 6 of our breakdown:
 *
 * ```
 * Step 1 (build graph):        O(V + E)
 * Step 6 (neighbor traversal):  O(E)
 * ```
 *
 * These two terms are **separate from** the PQ cost — they don't have a log factor at all.
 * When we write the grand total, it actually looks like:
 *
 * ```
 * Total = O(V + E)  +  O(E log E)  +  O(E)
 * ```
 *
 * The `O(V+E)` and `O(E)` terms are both **smaller** than `O(E log E)` (since `log E ≥ 1` for any E ≥ 2),
 * so technically they get absorbed/dominated. But textbooks often keep the `V` term visible for clarity —
 * especially because in some graphs (sparse ones, like trees) it's worth showing that just *initializing* the
 * V nodes still costs something, even before any edges are processed. So the "safe, inclusive" way to write it is:
 *
 * ```
 * O((V + E) log V)
 * ```
 *
 * This form is **slightly more conservative/generous** than strictly necessary
 * (it multiplies even the `O(V)` initialization cost by `log V`, which isn't strictly required),
 * but it's mathematically still a **valid upper bound**, and it's the form most textbooks/interviewers
 * expect because it clearly shows both variables (V and E) contributing.
 *
 * ### Side-by-side comparison
 *
 * ```
 * Our derived formula:         O(E log E)
 * Textbook-standard formula:    O((V + E) log V)
 *
 * Are they equivalent?
 *   log E ≈ log V    (proven: differ by at most factor of 2)
 *   So: E log E  ≈  E log V  ≤  (V+E) log V
 *
 * Yes — same complexity class, just written with V made explicit instead of hidden inside E.
 * ```
 *
 * ### One-line summary
 *
 * **We write `log V` instead of `log E` because we already proved `E ≤ V²` makes them the same order —
 * and we write `(V+E)` instead of just `E` to explicitly show both the node-initialization cost and the
 * edge-processing cost in one combined bound, even though `E` alone would already dominate in most graphs.**
 */
