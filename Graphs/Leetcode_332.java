package Graphs;

import java.util.*;

public class Leetcode_332
{
    class Solution {
        public List<String> findItinerary(List<List<String>> tickets) {
            Map<String, PriorityQueue<String>> graph = new HashMap<>();
            for (List<String> ticket : tickets) {
                graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>())
                        .add(ticket.get(1));
            }

            LinkedList<String> route = new LinkedList<>();
            dfs("JFK", graph, route);
            return route;
        }

        private void dfs(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {
            PriorityQueue<String> destinations = graph.get(airport);
            while (destinations != null && !destinations.isEmpty()) {
                String next = destinations.poll();  // smallest unused destination
                dfs(next, graph, route);
            }
            route.addFirst(airport);  // post-order: add only when fully "drained"
        }
    }
/*
Complexity:

Building the graph: O(E log E) — each of E tickets does one heap insert, O(log E) each (heap sizes summed across all nodes cap at E).
DFS: visits each edge exactly once, each poll() is O(log E) → O(E log E) total.
Total: O(E log E), Space: O(E) for the graph + recursion stack up to O(E) in the worst case (a long chain).




**Time Complexity**

Let `E = tickets.size()` (number of tickets/edges), and let `V` = number of distinct airports.

**Step 1 — Building the graph:**
```java
for (List<String> ticket : tickets) {
    graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>())
         .add(ticket.get(1));
}
```
- Loop runs `E` times (once per ticket).
- Each `.add()` into a `PriorityQueue` costs `O(log k)`, where `k` = current size of that particular queue. In the worst case, `k` can be up to `E` (e.g., if one airport has almost all outgoing tickets).
- Worst-case bound per insert: `O(log E)`.
- **Total for building graph: `O(E log E)`**

**Step 2 — DFS traversal:**
```java
while (destinations != null && !destinations.isEmpty()) {
    String next = destinations.poll();
    dfs(next, graph, route);
}
```
- Across the *entire* recursion (all calls combined), the total number of `poll()` operations equals the total number of tickets, `E` — since every ticket gets polled exactly once, and `dfs` is called exactly once per ticket used (plus one initial call for the start airport).
- Each `poll()` from a `PriorityQueue` of size `k` costs `O(log k)`, worst case `O(log E)`.
- **Total for DFS: `E` polls × `O(log E)` each = `O(E log E)`**

- `route.addFirst(airport)` runs once per airport-visit (once per `dfs` call, so `V + something` times overall — actually once per node-visit-instance, which is bounded by `E + 1` total dfs calls). `addFirst` on a `LinkedList` is `O(1)`, so this contributes `O(E)` total — dominated by the polling cost anyway.

**Combine:**
```
Building graph: O(E log E)
DFS traversal:  O(E log E)
```
**Total Time: O(E log E)**

This matches the same pattern you saw in Kruskal's — the `log` factor comes from a heap/sort operation on the edges, and here it's the `PriorityQueue` insert+poll playing that role instead of a sort.

---

**Space Complexity**

1. **`graph` map**: stores every ticket exactly once across all the `PriorityQueue`s (each ticket is one entry in exactly one queue). → `O(E)`

2. **Recursion call stack**: in the worst case (e.g., all tickets chain into one long path with no branching, like a straight line), the recursion depth equals the total number of `dfs` calls, which is `O(E)`. → `O(E)`

3. **`route` list**: holds one entry per airport-visit across the whole trip. A valid Eulerian path using `E` edges visits `E + 1` airports (nodes) total, counting revisits. → `O(E)`

**Total Space: O(E)**

---

**Summary:**
```
Time:  O(E log E)
Space: O(E)
```

where `E` = number of tickets. Same shape as Kruskal's and the array-based Prim's discussion — the dominant cost here comes from keeping destinations sorted via the heap, not from the DFS traversal itself (which is linear in the number of edges).
*/
}
