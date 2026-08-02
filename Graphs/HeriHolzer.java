package Graphs;

import java.util.*;

/**
 Hierholzer's Algorithm — From the Start
 What problem are we solving?

 We want to find a path that uses every edge in the graph exactly once. This is different from everything else
 you've learned so far:

 Kruskal's/Prim's care about visiting every node cheaply (MST).
 Dijkstra/Bellman-Ford/Floyd-Warshall care about the cheapest cost between nodes.
 Hierholzer's cares about using every single edge, exactly once — no repeats, no skips. Cost doesn't matter at all here.

 Two flavors:

 Eulerian Circuit: uses every edge once, and returns to the same node it started at.
 Eulerian Path: uses every edge once, but start and end can be different nodes.
 Step 1 — Check if it's even possible (before touching any algorithm)

 This is a prerequisite check, separate from the traversal itself.

 Undirected graph:

 Circuit exists if every node has an even degree (even number of edges touching it), and the graph is connected.
 Path (not circuit) exists if exactly 0 or 2 nodes have odd degree, graph connected. If 2 odd nodes exist, you must
 start at one of them and end at the other.

 Why even degree? Every time you pass through a node (not start/end), you use one edge to enter and a different edge to
 leave — edges get consumed in pairs. Odd degree means one edge is left unpaired, and you'll get stranded there eventually.

 Directed graph: replace "even/out" with in-degree vs out-degree:

 Circuit: every node needs in-degree == out-degree.
 Path: one node can have out = in + 1 (start), one can have in = out + 1 (end), everyone else needs in == out.

 Cyclic/acyclic: a Circuit always requires the graph to contain a cycle (by definition — you return to start, that's a loop). A Path can exist even on an acyclic graph (e.g., a straight chain 0-1-2-3).

 Weight: completely irrelevant. Positive, negative, or unweighted — doesn't matter, since we're never adding costs.

 Step 2 — The core idea, in plain English

 "Walk from your start node, using up edges as you go, until you get stuck (no unused edges left where you're standing).
 That traces one loop. Then check: does any node along that loop still have unused edges? If yes, start a fresh walk from
 there, and splice that detour into your path. Keep doing this until every edge is used."

 Step 3 — Full manual trace

 Graph (undirected), edges: 0-1, 1-2, 2-0, 1-3, 3-4, 4-1

 Degree check: node 0 → 2 (even). node 1 → 4 (even: edges to 0, 2, 3, 4).
 node 2 → 2 (even). node 3 → 2 (even). node 4 → 2 (even). All even → Eulerian Circuit exists.

 Start at node 0. Walk, marking edges used:

 0 → 1 (use 0-1)
 1 → 2 (use 1-2)
 2 → 0 (use 2-0)

 Back at 0. Node 0 has no unused edges left → stuck. But other edges remain unused (1-3, 3-4, 4-1) → not done yet.

 Path so far: 0 → 1 → 2 → 0

 Scan this path for a node with leftover unused edges. Node 1 qualifies (edges to 3, 4 still unused). Detour from node 1:

 1 → 3 (use 1-3)
 3 → 4 (use 3-4)
 4 → 1 (use 4-1)

 This detour is itself a loop back to 1.

 Splice it in, at the point where 1 appears in the original path:

 Original: 0 → 1 → 2 → 0
 Detour at 1: 1 → 3 → 4 → 1
 Spliced result: 0 → 1 → 3 → 4 → 1 → 2 → 0

 All 6 edges used exactly once. Done. Final Eulerian Circuit: 0 → 1 → 3 → 4 → 1 → 2 → 0

 Step 4 — How this is actually coded (stack-based, no real "splicing")

 Manually splicing strings mid-path is expensive and messy in code. Instead, we use a stack, and the splicing happens
 implicitly through the order things get popped.







 The logic, plainly:

 Look at the node on top of the stack.
 If it still has an unused edge, walk along it — push the new node on top.
 If it has no unused edges left, it's fully resolved — pop it off, and place it at the front of our answer list.
 Step 5 — Tracing the code, to see the stack replicate our manual splice
 Push 0. Peek 0 → has edges → go to 1. Push 1.
 Peek 1 → has edges → go to 2 (say we pick this one first). Push 2.
 Peek 2 → has edge to 0 → push 0.
 Peek 0 (2nd copy on stack) → no edges left → pop, front of list. circuit = [0]
 Peek 2 → no edges left → pop, front. circuit = [2, 0]
 Peek 1 → still has edges (to node 3)! → go to 3. Push 3.
 Peek 3 → edge to 4 → push 4.
 Peek 4 → edge back to 1 → push 1.
 Peek 1 (3rd copy) → no edges left → pop, front. circuit = [1, 2, 0]
 Peek 4 → no edges left → pop, front. circuit = [4, 1, 2, 0]
 Peek 3 → no edges left → pop, front. circuit = [3, 4, 1, 2, 0]
 Peek 1 (2nd copy) → no edges left → pop, front. circuit = [1, 3, 4, 1, 2, 0]
 Peek 0 (1st copy) → no edges left → pop, front. circuit = [0, 1, 3, 4, 1, 2, 0]

 Final: 0 → 1 → 3 → 4 → 1 → 2 → 0 — exact match with our manual splice trace.

 Why "insert at front" works

 A node only gets popped once it's fully resolved — every detour reachable from it has already been explored and folded in. Nodes that get stuck early (like 0, resolved quickly since it only had 2 edges) actually belong later in the final sequence, because everything discovered through node 1's extra edges needs to be woven in before we come back around to close the loop at 0. Inserting at the front naturally reverses the pop order into the correct final traversal — this is what implicitly performs the splice, without ever manipulating a path in the middle.

 Complexity
 Time: O(E) — every edge is pushed and consumed exactly once (removed from the adjacency list when used, both directions for undirected).
 Space: O(V + E) — adjacency list storage plus the stack/output list.

 Since you've built up Union-Find, topological sort, and now Hierholzer's — the natural next application is LeetCode 332 (Reconstruct Itinerary), which is Hierholzer's directly, with one twist: you must pick the lexicographically smallest valid itinerary when multiple Eulerian paths exist. Want to walk through that one next?



 Example A: Circuit EXISTS

 Graph: 0-1, 1-2, 2-0 (a triangle)

 Degree check first: node 0 → 2 edges (even). node 1 → 2 edges (even). node 2 → 2 edges (even). All even → Eulerian Circuit should exist.

 Adjacency list (mutable, we remove edges as used):

 0: [1, 2]
 1: [0, 2]
 2: [1, 0]

 Trace:

 Step	Stack (top→right)	Action
 1	[0]	Peek 0. adj[0] = [1,2], not empty. Take 2 (last element). Remove 2 from adj[0], remove 0 from adj[2]. Push 2.
 2	[0, 2]	Peek 2. adj[2] = [1] (0 was removed). Take 1. Remove from both sides. Push 1.
 3	[0, 2, 1]	Peek 1. adj[1] = [0] (2 was removed). Take 0. Remove from both sides. Push 0.
 4	[0, 2, 1, 0]	Peek 0 (2nd copy). adj[0] = [] now empty. Pop, insert at front. circuit = [0]
 5	[0, 2, 1]	Peek 1. adj[1] = [] empty. Pop, front. circuit = [1, 0]
 6	[0, 2]	Peek 2. adj[2] = [] empty. Pop, front. circuit = [2, 1, 0]
 7	[0]	Peek 0 (1st copy). adj[0] = [] empty. Pop, front. circuit = [0, 2, 1, 0]
 8	[]	Stack empty. Loop ends.

 Final circuit: 0 → 2 → 1 → 0

 Check: 3 edges in the graph, and our circuit has 4 nodes listed = 3 edges traced (0-2, 2-1, 1-0) → all edges used exactly once, and we're back at start. ✅ Valid Eulerian Circuit found.

 Example B: Circuit does NOT exist (disconnected edges)

 Graph: 0-1 and 2-3 — two completely separate edges, not connected to each other at all.

 Degree check: node 0 → 1 (odd). node 1 → 1 (odd). node 2 → 1 (odd). node 3 → 1 (odd). Four odd-degree nodes → doesn't even qualify for an Eulerian Path (need exactly 0 or 2 odd nodes), let alone a Circuit. This should fail.

 Adjacency list:

 0: [1]
 1: [0]
 2: [3]
 3: [2]

 Trace, starting arbitrarily at node 0:

 Step	Stack	Action
 1	[0]	Peek 0. adj[0] = [1], not empty. Take 1. Remove both sides. Push 1.
 2	[0, 1]	Peek 1. adj[1] = [] (0 already removed). Pop, front. circuit = [1]
 3	[0]	Peek 0. adj[0] = [] empty. Pop, front. circuit = [0, 1]
 4	[]	Stack empty. Loop ends.

 Final "circuit": 0 → 1

 What went wrong — and how do you even notice?

 The code didn't crash. It didn't throw an error. It just quietly returned [0, 1] and stopped — completely ignoring the edge 2-3, which never got touched at all! The stack simply ran empty once it exhausted whatever was reachable from 0, with no built-in awareness that other unused edges exist elsewhere in the graph.

 This is the critical danger of this code: it will always terminate and return something, whether or not a true Eulerian circuit actually exists. You must verify the result yourself by checking: does the returned circuit actually use every edge? A graph with E edges should produce a circuit list of length E + 1 (since a circuit with E edges touches E+1 node-visits, counting the repeated start/end).

 java
 List<Integer> circuit = hierholzer(0, adj);
 if (circuit.size() != totalEdges + 1) {
 // NOT a valid Eulerian circuit — some edges were unreachable/unused
 }

 In Example B: totalEdges = 2 (edges 0-1 and 2-3), so a valid circuit would need length 2+1 = 3. But we got length 2 ([0, 1]) → mismatch → confirms no valid Eulerian Circuit exists covering the whole graph.

 The core lesson

 The stack-based code assumes you already verified the graph qualifies (via the degree check) before calling it. It does not do that verification itself — it just blindly walks edges and pops what's exhausted. If you skip the degree/connectivity pre-check and the graph doesn't actually support an Eulerian circuit, the code will silently give you a partial, incorrect answer rather than telling you "impossible." Always do the even-degree (or in=out for directed) check f





 Let's trace it the same way — full adjacency list contents at every step, no shorthand — for the disconnected graph.

 Graph: two separate edges, not connected to each other
 0-1  and  2-3
 Starting adjacency lists
 adj[0] = [1]
 adj[1] = [0]
 adj[2] = [3]
 adj[3] = [2]

 Notice: node 0 only connects to node 1. Node 2 only connects to node 3. These are two completely separate little graphs sitting side by side — nothing links {0,1} to {2,3}.

 Degree check (before we even run the code)

 Node 0 → 1 edge (odd). Node 1 → 1 edge (odd). Node 2 → 1 edge (odd). Node 3 → 1 edge (odd). Four odd-degree nodes. The rule says: Eulerian Path needs exactly 0 or 2 odd-degree nodes. We have 4 — this already fails the check. But let's run the code anyway, to see how it behaves when nobody bothered to check first.

 Start the algorithm at node 0

 stack = [0], circuit = []

 Step 1: Peek 0

 adj[0] = [1] — take the last (only) element, 1. Uses edge 0-1.

 Remove 1 from adj[0]:

 adj[0] was [1] → remove 1 → adj[0] = []

 Remove 0 from adj[1]:

 adj[1] was [0] → remove 0 → adj[1] = []

 Push 1.

 State after step 1:

 adj[0] = []
 adj[1] = []
 adj[2] = [3]     ← completely untouched
 adj[3] = [2]     ← completely untouched
 stack = [0, 1]
 Step 2: Peek 1

 adj[1] = [] — already empty! No unused edges here. This node is "stuck" — pop it, insert at front of circuit.

 circuit = [1]
 stack = [0]
 Step 3: Peek 0

 adj[0] = [] — also empty. Pop, insert at front.

 circuit = [0, 1]
 stack = []
 Step 4: Stack is empty. Loop ends.

 Final returned circuit: [0, 1]

 What actually happened here

 The algorithm found and used the edge 0-1 just fine, then completely stopped — because from node 1, there was nowhere left to go (its only edge, back to 0, was already used), and the stack ran dry. Nodes 2 and 3, along with their edge 2-3, were never even looked at. They're sitting untouched, fully connected to each other, but totally unreachable from wherever we happened to start (0).

 The critical takeaway: the code did NOT detect failure

 Notice the code never threw an error, never printed a warning — it just quietly returned [0, 1] as if that were a complete, successful answer. It has no built-in check for "did I actually use every edge?" It just walks until the stack is empty, then hands back whatever it collected.

 How you'd actually catch this

 Compare the result size against what a true, complete Eulerian circuit should look like:

 Total edges in the graph = 2  (0-1 and 2-3)
 A valid circuit using all edges should have length = edges + 1 = 3

 But we got circuit.size() = 2 ([0, 1]), not 3. Mismatch → this confirms no valid Eulerian Circuit covers the whole graph — exactly matching what our degree check predicted upfront (4 odd-degree nodes means it was never going to work).

 Side-by-side contrast with Example A
 Example A (triangle)	Example B (two separate edges)
 Degree check	All even → passes	4 odd nodes → fails
 Total edges	3	2
 Expected circuit length	3+1 = 4	2+1 = 3
 Actual circuit returned	[0, 2, 1, 0], length 4 ✓	[0, 1], length 2 ✗
 Conclusion	Valid Eulerian Circuit found	Code silently gave an incomplete, invalid answer
 The lesson to carry forward

 Always run the degree/connectivity check before trusting this code's output. The stack-based Hierholzer implementation is only correct given that a valid Eulerian Circuit/Path already exists in the graph — it has no self-awareness to detect when that precondition wasn't met. If you skip the pre-check, you must at least verify circuit.size() == totalEdges + 1 afterward to catch silent failures like this one




 Recognized flawed example and pivoted toward clearer demonstration
 Recognized flawed example and pivoted toward clearer demonstration
 Good catch to double check — let's clarify exactly what that graph was (and wasn't) an example of.

 Was 0-1 and 2-3 an example of Eulerian Path?
 No. That graph doesn't have an Eulerian Path either — it fails for a much more basic reason before we even get to degree-counting.

 The reason: it's not even connected
 An Eulerian Path (or Circuit) requires you to walk through the graph using every edge in one continuous journey. But 0-1 and 2-3 are two separate, disconnected pieces — there's no edge linking node 1 (or 0) to node 2 (or 3) at all. You physically cannot walk from the {0,1} piece to the {2,3} piece, because no path connects them.

 Connectivity is actually the very first check you need — before even looking at degrees. If the graph (or at least the part with edges) isn't fully connected, neither an Eulerian Path nor Circuit can exist, no matter what the degrees look like.

 So that example was really just showing: "what happens when you run the code on a graph that was never a valid candidate to begin with" — it wasn't meant to be "a path graph," it was meant to be "a broken/disconnected graph" to show the code fails silently.

 Let's build an actual valid Eulerian Path example
 Graph: 0-1, 1-2, 2-3 (a simple connected chain, no loop)

 Degree check:

 node 0: connects to {1}         → degree 1 (odd)
 node 1: connects to {0, 2}      → degree 2 (even)
 node 2: connects to {1, 3}      → degree 2 (even)
 node 3: connects to {2}         → degree 1 (odd)
 Exactly 2 odd-degree nodes (0 and 3), rest even → Eulerian Path exists (not a Circuit, since it doesn't return to start). The path must start at one of the odd nodes and end at the other.

 Trace the code, starting at node 0 (a required odd-degree node)
 Initial adjacency lists:

 adj[0] = [1]
 adj[1] = [0, 2]
 adj[2] = [1, 3]
 adj[3] = [2]
 Step 1: Peek 0. adj[0] = [1] → take 1 (edge 0-1). Remove 1 from adj[0], remove 0 from adj[1].

 adj[0] = []
 adj[1] = [2]
 stack = [0, 1]
 Step 2: Peek 1. adj[1] = [2] → take 2 (edge 1-2). Remove 2 from adj[1], remove 1 from adj[2].

 adj[1] = []
 adj[2] = [3]
 stack = [0, 1, 2]
 Step 3: Peek 2. adj[2] = [3] → take 3 (edge 2-3). Remove 3 from adj[2], remove 2 from adj[3].

 adj[2] = []
 adj[3] = []
 stack = [0, 1, 2, 3]
 Step 4: Peek 3. adj[3] = [] → empty, stuck. Pop, front. circuit = [3]

 Step 5: Peek 2. adj[2] = [] → empty. Pop, front. circuit = [2, 3]

 Step 6: Peek 1. adj[1] = [] → empty. Pop, front. circuit = [1, 2, 3]

 Step 7: Peek 0. adj[0] = [] → empty. Pop, front. circuit = [0, 1, 2, 3]

 Final result: 0 → 1 → 2 → 3

 Verify it's correct
 Total edges = 3 (0-1, 1-2, 2-3). A valid path (not circuit, so no repeated start/end node) using all edges should have length = edges + 1 = 4. We got [0,1,2,3], length 4 ✓. Starts at 0 (odd-degree node), ends at 3 (the other odd-degree node) ✓. Every edge used exactly once ✓.

 Key distinction to hold onto
 Requirement
 Connected	Required for both Path and Circuit — non-negotiable, check this first
 Eulerian Circuit	All nodes even degree (connected graph)
 Eulerian Path	Exactly 0 or 2 odd-degree nodes (connected graph); if 2, must start/end at those specific nodes
 The earlier 0-1, 2-3 example failed at the very first hurdle (not connected) — it never even got to the degree-counting stage as a meaningful test.


 */
public class HeriHolzer
{
    List<Integer> hierholzer(int start, Map<Integer, List<Integer>> adj) {
        Deque<Integer> stack = new ArrayDeque<>();
        List<Integer> circuit = new LinkedList<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.peek();
            if (!adj.get(node).isEmpty()) {
                int next = adj.get(node).remove(adj.get(node).size() - 1); // take an unused edge
                adj.get(next).remove(Integer.valueOf(node)); // remove reverse edge too (undirected)
                stack.push(next);
            } else {
                // no unused edges left here — this node is "resolved", pop it into the answer
                circuit.add(0, stack.pop()); // insert at FRONT
            }
        }
        return circuit;
    }
}
/**
 * # Time & Space Complexity — Hierholzer's Algorithm
 *
 * ## Time Complexity: **O(E)**
 *
 * Let's derive this mechanically rather than just quoting it.
 *
 * **What does the algorithm actually do, operation by operation?**
 *
 * - Every edge gets **pushed onto the stack exactly once** — when we walk across it, the destination node gets pushed.
 * Since there are `E` edges total, that's at most `E`
 * pushes (plus 1 initial push for the start node).
 * - Every edge gets **removed from the adjacency list exactly once from each side** — when you use edge `(u,v)`,
 * you remove `v` from `adj[u]` and `u` from `adj[v]`. That's 2 removals per edge, but each removal is O(1) if you're
 * using a structure like a `Deque`/`ArrayList` and always removing from the **end** (like we did — "take the last element").
 * So this is O(1) work, `E` times → **O(E)** total.
 * - Every node gets **popped from the stack exactly once** — when it becomes "stuck" (no unused edges left). Each pop is
 * O(1). Since the stack holds at most `E+1` entries total across the whole run (one per edge traversal, plus the start),
 * that's **O(E)** pops.
 *
 * **Adding it all up:** pushes O(E) + removals O(E) + pops O(E) = **O(E)** overall.
 *
 * (If you also count the upfront degree-check pass to verify the graph qualifies, that's O(V + E) — checking every node's
 * degree and every edge once. But the traversal itself, once you know it qualifies, is O(E).)
 *
 * ## Space Complexity: **O(V + E)**
 *
 * Breaking down what's actually stored in memory:
 *
 * - **Adjacency list**: stores all `E` edges (each edge appears in 2 lists for undirected graphs, but that's just a
 * constant factor — still O(E)).
 * - **The stack**: in the worst case, can hold up to `O(E)` node-references (since we push once per edge traversed,
 * before any popping happens — imagine a graph where you walk deep before getting stuck).
 * - **The output `circuit` list**: holds `O(E)` entries in the end (an Eulerian circuit visits `E+1` nodes total,
 * since each edge represents one more node in the sequence).
 * - **Node bookkeeping** (degree array, if you're using one for the upfront check): O(V).
 *
 * **Total: O(V + E)** — dominated by E in practice, since a connected graph needs at least V-1 edges, so E is usually the
 * larger term.
 *
 * ## Quick comparison against your other graph algorithms, to keep the pattern in view
 *
 * | Algorithm | Time | Space | What it's counting |
 * |---|---|---|---|
 * | Union-Find (per op) | O(α(V)) ≈ O(1) | O(V) | operations on V nodes |
 * | Dijkstra | O(E log V) | O(V + E) | edges relaxed via heap |
 * | Bellman-Ford | O(V·E) | O(V) | E edges × V-1 rounds |
 * | Floyd-Warshall | O(V³) | O(V²) | all pairs, all intermediates |
 * | Kruskal's / Prim's | O(E log E) | O(V + E) | sorting/heap over edges |
 * | **Hierholzer's** | **O(E)** | **O(V + E)** | each edge walked & removed once |
 *
 * Hierholzer's is actually one of the **cheapest** algorithms in this whole set, time-wise — no sorting, no heap,
 * no repeated rounds. Just a single linear pass that consumes each edge exactly once.
 */