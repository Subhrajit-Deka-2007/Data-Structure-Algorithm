package Graphs;
import java.util.Arrays;
public class Kruskal_Algorithm
{


    int[] parent, rank;

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    void union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry) return;
        if (rank[rx] < rank[ry]) parent[rx] = ry;
        else if (rank[rx] > rank[ry]) parent[ry] = rx;
        else { parent[ry] = rx; rank[rx]++; }
    }

    int kruskal(int V, int[][] edges) { // edges[i] = {u, v, weight}
        Arrays.sort(edges, (a, b) -> a[2] - b[2]); // sort by weight
        parent = new int[V]; rank = new int[V];
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
}
/**
 Complexity — Kruskal's

 Time:

  Sorting E edges: O(E log E)
 Going through sorted edges, each find/union call with path compression + union by rank is nearly O(1) —
 technically O(α(V)), the inverse Ackermann function, which is so slow-growing it's effectively constant
 (≤ 4 for any realistic V). Doing this for all E edges: O(E · α(V)) ≈ O(E)
 Total: O(E log E) dominates. Since E ≤ V², log E ≤ 2 log V, so this is sometimes written as O(E log V) —
 same thing, tighter constant.

 Space:

 parent[] and rank[] arrays: O(V)
 Storing the edge list: O(E)
 Total: O(V + E)
 */

/*
The big picture: what is MST?

Minimum Spanning Tree = given a connected, undirected, weighted graph, pick a subset of edges that:

Connects all V nodes (spanning)
Contains no cycles (tree — exactly V-1 edges)
Has the minimum total edge weight possible

Both Prim's and Kruskal's are greedy algorithms that build this tree, but they attack the problem from completely different
angles.

Kruskal's Algorithm
Core idea

"Sort all edges by weight. Go through them cheapest-first. Add an edge if it doesn't create a cycle. Stop once you have V-1 edges."

It thinks in terms of edges, globally, regardless of which node they touch.

Why "doesn't create a cycle" matters

If you already have a path connecting two nodes, and you add another edge between those same two nodes (directly or indirectly),
you've made a cycle — which means you have a redundant edge you didn't need. A tree with V nodes has exactly V-1 edges;
any more, and it's not a tree anymore.

How do we check "would this create a cycle?" — Union-Find

This is why Kruskal's pairs naturally with Union-Find (Disjoint Set). Each node starts in its own separate set.
 When you consider an edge (u, v):

find(u) == find(v)? → they're already in the same connected component → adding this edge would create a cycle → skip it.
find(u) != find(v)? → they're in different components → adding this edge safely connects two separate pieces → take it,
 then union(u, v).
Step-by-step trace

Graph, 5 nodes (0-4), edges:

(0,1) = 2
(0,3) = 6
(1,2) = 3
(1,3) = 8
(1,4) = 5
(2,4) = 7
(3,4) = 9

Step 1: Sort edges by weight:

(0,1)=2, (1,2)=3, (1,4)=5, (0,3)=6, (2,4)=7, (1,3)=8, (3,4)=9

Step 2: Go through, cheapest first. Initially every node is its own set: {0} {1} {2} {3} {4}

Edge	find(u) vs find(v)	Action	Sets after
(0,1)=2	different	take	{0,1} {2} {3} {4}
(1,2)=3	different	take	{0,1,2} {3} {4}
(1,4)=5	different	take	{0,1,2,4} {3}
(0,3)=6	different	take	{0,1,2,3,4} — all connected!
(2,4)=7	same (both in the one big set)	skip (would cycle)	—
(1,3)=8	same	skip	—
(3,4)=9	same	skip	—

We stop once we have V-1 = 4 edges taken: (0,1), (1,2), (1,4), (0,3). Total weight = 2+3+5+6 = 16.
 */
