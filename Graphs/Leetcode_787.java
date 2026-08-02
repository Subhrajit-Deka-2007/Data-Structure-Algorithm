package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Arrays;
public class Leetcode_787
{


        /** Solve using Dijkstra  */
        public int findCheapestPrice1(int n, int[][] flights, int src, int dst, int k)
        {
            List<List<int[]>> adjList = new ArrayList<>();
            for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());
            for (int[] f : flights) adjList.get(f[0]).add(new int[]{f[1], f[2]});

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            pq.offer(new int[]{0, src, 0}); // {cost, node, stopsUsed}

            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int cost = curr[0], node = curr[1], stops = curr[2];

                if (node == dst) return cost;      // heap always pops cheapest first -> safe to return immediately
                if (stops > k) continue;           // exceeded stop budget, prune

                for (int[] neighbor : adjList.get(node)) {
                    pq.offer(new int[]{cost + neighbor[1], neighbor[0], stops + 1});
                }
            }
            return -1;
        }






        /**
         ## Time & Space Complexity — Constrained Dijkstra (K-Stops Flights)

         ### Time Complexity: **O(E · k · log(E · k))**

         **Where this comes from, step by step:**

         1. **Why a node isn't just popped once:** unlike plain Dijkstra, there's no `visited[]` lock. A single node can be popped again for every distinct `stopsUsed` value it might carry — from `0` up to `k`, i.e., **k+1 possible values** (simplified to `k` in Big-O, since constants drop out).

         2. **Pushes per edge:** every time a node is popped, we loop over its outgoing edges and push once per edge. Since a node can be popped up to `k` times, each of its edges can be pushed up to `k` times too.

         3. **Total pushes across the whole graph:**
         ```
         total pushes = k × (sum of out-degree over all nodes) = k × E = E × k
         ```
         (using the fact that summing out-degree over every node always equals total edge count `E`)

         4. **Cost per heap operation:** the heap can hold up to `O(E·k)` entries at once, so each push/pop costs `O(log(E·k))`.

         5. **Multiply:** `O(E·k)` pushes × `O(log(E·k))` per push = **O(E·k·log(E·k))**.

         ---

         ### Space Complexity: **O(E · k)**

         **Where this comes from:**

         - **Adjacency list:** stores all edges once → O(E).
         - **Priority queue (the dominant term):** since the same node can be pushed up to `k` times (once per stop-count), the heap can hold up to `O(E·k)` entries simultaneously in the worst case — this is what actually drives the space bound, not the adjacency list.

         So overall space is **O(E·k)**, dominated by the heap potentially holding many duplicate `(cost, node, stopsUsed)` entries for the same node.

         ---

         ### Quick contrast — why Bellman-Ford wins here

         | | Constrained Dijkstra | Bellman-Ford (k+1 rounds) |
         |---|---|---|
         | Time | O(E·k·log(E·k)) | O(k·E) |
         | Space | O(E·k) (heap duplicates) | O(V) (just the dist array) |

         Bellman-Ford avoids the heap entirely — no `log` factor, no duplicate-entry bloat — because it never relied on "finalize once and skip revisits" in the first place. That's exactly the assumption this k-stops constraint breaks for Dijkstra.
         */




        /* Solve Using Bellman Ford */
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;

            for (int i = 0; i <= k; i++) {
                int[] temp = dist.clone(); // IMPORTANT: use a snapshot, don't update in place mid-round

                for (int[] flight : flights) {
                    int u = flight[0], v = flight[1], price = flight[2];
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + price < temp[v]) {
                        temp[v] = dist[u] + price;
                    }
                }

                dist = temp;
            }

            return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];



        }
/*
Time Complexity = O(k·E)
Space Complexity = O(v)





What does i represent in this loop?

Let's trace what happens on each value of i, tying back to what we know about Bellman-Ford rounds = "edges allowed."

i = 0: this is the first round. Round "0" here effectively means: "allow paths using 1 edge" — because this is the very first pass where any edge relaxation can happen at all. Before any round has run, dist[] only has the source at 0 and everything else at INF — no edges have been considered yet.

So after i=0 finishes, we've allowed 1 edge — i.e., direct flights only.

i = 1: second round → now paths using up to 2 edges are considered.

i = k: the (k+1)-th round → paths using up to k+1 edges are considered.

So why does the loop run i <= k (i.e., i from 0 to k, which is k+1 total iterations)?

Because we need exactly k+1 rounds to allow paths with up to k+1 edges — which is precisely what "at most K stops" requires (K stops = K+1 edges).
*/




/**
 ## Summary — Why `dist.clone()` Is Needed (Bellman-Ford, K-Stops Variant)

 **The algorithm's goal:** each round `i` (from `0` to `k`) should allow paths using **exactly one more edge** than the previous round — enforcing a hard cap of `k+1` edges total by the time the loop ends.

 **The rule each round must follow:**
 - **Read** distances only from `dist[]` — the frozen snapshot from **before** this round started.
 - **Write** new/updated distances only into `temp[]` — this round's results.
 - Only after the entire round finishes does `dist = temp`, letting the next round see this round's updates.

 **Why `.clone()` enforces this:**
 ```java
 int[] temp = dist.clone();   // snapshot: freeze what we knew BEFORE this round
 // ... relax edges, reading dist[u] (old), writing temp[v] (new) ...
 dist = temp;                 // only now does the round's progress become visible
 ```
 By reading from the untouched `dist` and writing into a separate `temp`, no edge relaxed later in the loop can see a value that was updated by an earlier edge *in that same round*.

 **What breaks without it (updating `dist` in place):**
 An edge processed early in the round can update `dist[v]`. A *later* edge in that same pass can then read that just-updated value and chain onto it — using **two edges in what was meant to be a single-edge round**. This lets one round secretly do the work of two (or more), silently exceeding the `k+1`-edge cap the whole algorithm is built to enforce.

 **Concrete proof (traced example):**
 - `k=0` should mean "only direct flights allowed" (max 1 edge).
 - Without clone: `0->1` updates `dist[1]`, then `1->2` immediately reads that fresh value and updates `dist[2]` — all in round 0. Result: a 2-edge path sneaks through as if it were 1 edge. Wrong answer (a cost is returned when it should have been `-1`).
 - With clone: `1->2` reads the *old* `dist[1]` (still infinity, since `dist` hasn't been touched yet this round), correctly refuses to chain, and returns `-1` as expected.

 **One-line rule to remember:**
 > Snapshot at the start of the round, read only from the snapshot, write only to the new copy, swap at the end. This guarantees each round adds precisely one edge's worth of reach — never a sneaky shortcut using information that shouldn't exist yet.
 */}

