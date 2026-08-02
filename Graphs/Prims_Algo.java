package Graphs;

import java.util.List;
import java.util.PriorityQueue;

public class Prims_Algo
{
    int prim(int V, List<int[]>[] adj) { // adj[u] = list of {v, weight}
        boolean[] visited = new boolean[V];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {weight, node}
        pq.add(new int[]{0, 0}); // start at node 0, cost 0 to "reach" it

        int totalWeight = 0, nodesUsed = 0;
        while (!pq.isEmpty() && nodesUsed < V) {
            int[] top = pq.poll();
            int weight = top[0], u = top[1];
            if (visited[u]) continue; // stale entry, skip
            visited[u] = true;
            totalWeight += weight;
            nodesUsed++;

            for (int[] edge : adj[u]) {
                int v = edge[0], w = edge[1];
                if (!visited[v]) pq.add(new int[]{w, v});
            }
        }
        return totalWeight;
    }
}
/**

 Prim's Algorithm
 Core idea

 "Start at any node. Repeatedly grow the tree by adding the cheapest edge that connects a node already in the tree to a node not yet in the tree."

 It thinks in terms of nodes — growing one connected blob, always reaching outward via the cheapest available edge.

 This should feel familiar — it's structurally the same shape as Dijkstra! The difference: Dijkstra picks the next node based on total distance from source; Prim's picks the next node based on the single cheapest edge from the current tree, ignoring how far that node is from the start overall.

 How do we track "cheapest edge that reaches outward"? — Min-Heap

 Same tool as Dijkstra: a priority queue (min-heap) of (weight, node), plus a visited[] array marking which nodes are already inside the tree.

 Step-by-step trace

 Same graph as before:

 (0,1) = 2
 (0,3) = 6
 (1,2) = 3
 (1,4) = 5
 (2,4) = 7
 (1,3) = 8
 (3,4) = 9

 Start at node 0. visited = {0}. Push all edges from 0 into the heap: (2,1), (6,3).

 Heap: [(2,1), (6,3)]

 Pop (2,1) — cheapest. Node 1 not visited → take edge (0,1), weight 2. Mark visited = {0,1}.
 Push all of node 1's edges: (3,2), (8,3), (5,4).

 Heap: [(3,2), (5,4), (6,3), (8,3)]

 Pop (3,2) — cheapest. Node 2 not visited → take edge (1,2), weight 3. Mark visited = {0,1,2}. Push node 2's edges: (7,4).

 Heap: [(5,4), (6,3), (7,4), (8,3)]

 Pop (5,4) — cheapest. Node 4 not visited → take edge (1,4), weight 5. Mark visited = {0,1,2,4}. Push node 4's edges: (9,3).

 Heap: [(6,3), (7,4), (8,3), (9,3)]

 Pop (6,3) — cheapest. Node 3 not visited → take edge (0,3), weight 6. Mark visited = {0,1,2,3,4} — all nodes visited, done!

 (The remaining heap entries (7,4), (8,3), (9,3) are stale/irrelevant — both endpoints already visited, so they'd just get
 popped and discarded if we kept going.)

 MST edges: (0,1)=2, (1,2)=3, (1,4)=5, (0,3)=6 — total weight 16. Same MST as Kruskal's found (MST is unique here since all
 weights are distinct — in general there can be multiple
 valid MSTs with equal total weight, but the total weight itself is always the same).





 Notice the exact same "stale entry" pattern as Dijkstra: if (visited[u]) continue; — we don't bother removing old heap entries when a better one shows up, we just let them sit and discard them lazily when popped.

 Complexity — Prim's (heap-based)

 Let's derive this mechanically, the way you like.

 Every node gets popped from the heap once when finalized: V pops, each O(log E) (heap operations cost log
 of heap size, and heap can hold up to E entries) → contributes O(V log E)
 Every edge gets pushed into the heap at most once, when its "outward" endpoint is discovered — that's E pushes,
 each O(log E) → contributes O(E log E)
 Since E dominates V here (assuming a connected graph, E ≥ V-1), the E log E term dominates.

 Total: O(E log E) — same asymptotic bound as Kruskal's, since log E ≤ 2 log V, also commonly written O(E log V).

 Space:

 Adjacency list: O(V + E)
 visited[] array: O(V)
 Heap can hold up to O(E) entries (stale ones included)
 Total: O(V + E)
 When to pick which — the practical rule of thumb
 Kruskal's	Prim's
 Best for	Sparse graphs (E close to V)	Dense graphs (E close to V²)
 Needs	Union-Find	Min-heap + adjacency list
 Natural fit when	Edges are already given as a flat list	You're already doing BFS/DFS-style traversal

 Both give the same total MST weight always — the choice is really about which data structure fits your input format and graph density better.

 Want to trace through a case where the graph has a tie in edge weights, so you can see how Kruskal's and Prim's might
 pick a different set of edges but still land on the same total cost?







 Good question to pin down precisely — let's check this carefully rather than assume.

 Short answer: Both algorithms can land on different trees when ties exist — it's not unique to Prim's.

 The reason multiple valid MSTs exist is a property of the graph itself (having tied edge weights), not a property of which
 algorithm you use. So whichever algorithm you run, if ties exist, there's a chance it picks a different — but equally valid —
 combination of edges than the other algorithm, or even a different run of the same algorithm with a different tie-breaking rule.

 Why Kruskal's can also vary

 Kruskal's sorts edges by weight and processes them in order. When two edges have the exact same weight, the sort doesn't
 inherently know which one should go "first" — that's decided by whatever tie-breaking the sort implementation happens to use
 (could be original input order, could be arbitrary). If you process a tied edge before another, you might accept it into the
 tree; a different tie-order might have accepted the other tied edge instead, ending up with a different tree shape — same
 total weight, though.

 Why Prim's can also vary

 Prim's uses a min-heap and always pops the cheapest available edge from the "frontier." When two edges in the heap
 are tied in weight, again the heap doesn't guarantee which one comes out first — depends on insertion order and how
 the heap breaks ties internally. Pop one vs. the other, and you might grow the tree in a different shape.

 Concrete check using our tie example
 0 → 1 : weight 2
 0 → 2 : weight 2
 1 → 2 : weight 2
 2 → 3 : weight 5
 Kruskal's, depending on how the sort orders the three weight-2 edges, might pick (0,1)+(0,2) first two,
 or (0,1)+(1,2), etc. — different runs/implementations could differ.
 Prim's, starting at node 0, would push (0,1)=2 and (0,2)=2 onto the heap — both tied. Which one pops first
 depends on heap tie-breaking. Pick (0,1) first → tree grows one way; pick (0,2) first → tree grows a different way.

 So both are susceptible to picking different (but equally valid, equally-weighted) trees in the presence of ties.
 It's not that one algorithm is "the one" that produces variation — any greedy algorithm facing a tie has some freedom
 in which tied option it picks, and that freedom is exactly where the variation comes from.

 The one constant regardless of algorithm or tie-breaking

 No matter which specific tree either algorithm lands on, the total weight will always be the same — because that
 minimum total is a fixed mathematical property of the graph, not something that depends on which valid path you took
 to construct it.
 */