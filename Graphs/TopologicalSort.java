package Graphs;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

 class TopoSortDFS1 {
// Handling the topo sort when there is cycle
    public static void main(String[] args) {
        // Making a Graph
        /*
        [0]--->[1]--->[2]--->[4]
                |->[3]<-|
         */
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) list.add(new ArrayList<>());
        list.get(0).add(1);
        list.get(1).add(2);
        list.get(1).add(3);
        list.get(2).add(3);
        list.get(2).add(4);
        System.out.println(list);

        // 0 = unvisited, 1 = visiting (on current DFS path), 2 = fully done
        int[] state = new int[5];
        ArrayList<Integer> ans = new ArrayList<>();
        boolean valid = true;

        for (int i = 0; i < 5 && valid; i++) {
            if (state[i] == 0) {
                valid = dfs(i, state, ans, list);
            }
        }

        if (!valid) {
            System.out.println("Cycle detected — no topological order exists");
        } else {
            Collections.reverse(ans);
            System.out.println("Topological Sort: " + ans);
        }
    }

    public static boolean dfs(int ele, int[] state, ArrayList<Integer> ans, List<List<Integer>> adj) {
        state[ele] = 1; // mark as "visiting" (currently on the recursion stack)

        for (int neighbor : adj.get(ele)) {
            if (state[neighbor] == 1) {
                return false; // back edge to an ancestor -> cycle found
            }
            if (state[neighbor] == 0) {
                if (!dfs(neighbor, state, ans, adj)) {
                    return false; // propagate cycle failure upward
                }
            }
            // if state[neighbor] == 2, it's already finished -> skip, no work needed
        }

        state[ele] = 2;  // mark fully done
        ans.add(ele);    // post-order add
        return true;
    }
}



public class TopologicalSort {
     // These Approach cannot handle the topo Sort
    public static void main(String[] args) {
        // Making a Graph
        /*
        [0]--->[1]--->[2]--->[4]
                |->[3]<-|
         */
        List<List<Integer>> list = new ArrayList<>();
        for(int i =0;i<5;i++)list.add(new ArrayList<>());
        list.get(0).add(1);
        list.get(1).add(2);
        list.get(1).add(3);
        list.get(2).add(3);
        list.get(2).add(4);
        System.out.println(list );
        boolean [] vis = new boolean[5];
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i =0;i<5;i++) if(!vis[i])dfs(i,vis,ans,list);
        System.out.println(" Topological Sort "+ans.reversed());
    }
    public static void dfs(int ele, boolean [] vis, ArrayList<Integer>ans,List<List<Integer>>adj ){
        // Topological Sort using DFS
        vis[ele] = true;
        for(int i : adj.get(ele)) if(!vis[i]) dfs(i,vis,ans,adj);
        ans.add(ele);
    }
    /*                         TOPOLOGICAL SORT USING BFS WHICH IS ALSO CALLED (KAHN'S ALGORITHM )                                              */




}
/**
No problem — let's rebuild both from scratch, generic (not tied to Alien Dictionary yet), since that context will help before we adapt it.

        ## Version 1: Kahn's Algorithm (BFS-based)

        **Core idea:** repeatedly pick nodes that have **zero incoming edges** (in-degree 0) — these are safe to place next in the ordering, since nothing depends on them being processed first. Removing a node "frees up" its neighbors, potentially dropping their in-degree to 0 too.

```java
public List<Integer> topoSortBFS(int n, List<List<Integer>> adj) {
    int[] inDegree = new int[n];

    // Step 1: compute in-degree for every node
    for (int u = 0; u < n; u++) {
        for (int v : adj.get(u)) {
            inDegree[v]++;
        }
    }

    // Step 2: seed queue with all in-degree-0 nodes
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }

    List<Integer> result = new ArrayList<>();

    // Step 3: process queue
    while (!queue.isEmpty()) {
        int u = queue.poll();
        result.add(u);

        for (int v : adj.get(u)) {
            inDegree[v]--;
            if (inDegree[v] == 0) {
                queue.offer(v);
            }
        }
    }

    // Step 4: cycle check
    if (result.size() != n) {
        return new ArrayList<>(); // cycle detected, no valid ordering
    }

    return result;
}
```

        **Line by line:**

        ```java
int[] inDegree = new int[n];
for (int u = 0; u < n; u++) {
        for (int v : adj.get(u)) {
inDegree[v]++;
        }
        }
        ```
For every edge `u -> v` in the adjacency list, increment `v`'s in-degree by 1 — since `v` now has one more prerequisite (`u`) that must come before it.

        ```java
Queue<Integer> queue = new LinkedList<>();
for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
}
        ```
Any node with in-degree `0` has **no prerequisites** — it's safe to place first in the ordering right away. Seed the BFS queue with all such nodes.

        ```java
while (!queue.isEmpty()) {
int u = queue.poll();
    result.add(u);
```
Pop a "ready" node, add it to the final ordering.

```java
    for (int v : adj.get(u)) {
inDegree[v]--;
        if (inDegree[v] == 0) {
        queue.offer(v);
        }
                }
                ```
For every neighbor `v` of `u` (meaning `u` was one of `v`'s prerequisites), decrement `v`'s in-degree — `u` is now "satisfied." If `v`'s in-degree drops to `0`, it means **all** of `v`'s prerequisites are now satisfied, so `v` becomes ready — push it onto the queue.

```java
if (result.size() != n) {
        return new ArrayList<>();
        }
        ```
        **Cycle detection**: if there's a cycle, some nodes will *never* reach in-degree 0 (they're stuck waiting on each other forever), so they never get added to `result`. If `result.size() < n`, some nodes were left out — meaning a cycle exists, and no valid topological order is possible.

        ---

        ## Version 2: DFS-based (using `visited` / `inStack`)

**Core idea:** do a DFS, and when a node is **fully finished** (all its descendants processed), push it onto a stack. Since a node's dependencies get finished *before* the node itself (post-order), popping the stack at the end gives correct order. `inStack` (or "currently in recursion path") is used to detect cycles — if you revisit a node that's still on the current DFS path, that's a cycle.

        ```java
public List<Integer> topoSortDFS(int n, List<List<Integer>> adj) {
    boolean[] visited = new boolean[n];
    boolean[] inStack = new boolean[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            if (hasCycle(i, adj, visited, inStack, stack)) {
                return new ArrayList<>(); // cycle detected
            }
        }
    }

    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
        result.add(stack.pop());
    }

    return result;
}

private boolean hasCycle(int u, List<List<Integer>> adj, boolean[] visited,
                         boolean[] inStack, Deque<Integer> stack) {
    visited[u] = true;
    inStack[u] = true;

    for (int v : adj.get(u)) {
        if (inStack[v]) {
            return true; // back edge -> cycle
        }
        if (!visited[v]) {
            if (hasCycle(v, adj, visited, inStack, stack)) {
                return true;
            }
        }
    }

    inStack[u] = false; // done exploring u's branch, remove from current path
    stack.push(u);       // post-order: push once fully done
    return false;
}
```

        **Line by line:**

        ```java
boolean[] visited = new boolean[n];   // has this node EVER been fully processed
boolean[] inStack = new boolean[n];   // is this node on the CURRENT recursion path
Deque<Integer> stack = new ArrayDeque<>();  // will hold post-order result
```

        ```java
visited[u] = true;
inStack[u] = true;
        ```
Mark `u` as visited (so we don't reprocess it from a different starting point later), and mark it as "currently on the path" (needed for cycle detection).

        ```java
        for (int v : adj.get(u)) {
        if (inStack[v]) {
        return true; // back edge -> cycle
        }
        ```
        **This is the cycle check.** If `v` is currently `inStack` — meaning it's an *ancestor* of `u` in the current DFS path — then edge `u -> v` creates a cycle (you'd be going back to something that depends on `u` completing first, but `u` depends on `v`... circular).

        ```java
    if (!visited[v]) {
        if (hasCycle(v, adj, visited, inStack, stack)) {
        return true;
        }
        }
        }
        ```
If `v` hasn't been visited yet, recurse into it. If that recursive call finds a cycle deeper in, propagate `true` up immediately.

        ```java
inStack[u] = false;
        stack.push(u);
return false;
        ```
Once we've explored **all** of `u`'s neighbors with no cycle found, `u` is done — remove it from the current path (`inStack[u] = false`, since we're backtracking past it), and push it onto the result stack. **This push happens in post-order** — `u` goes on the stack only after everything reachable from `u` has already been fully processed and pushed.

        ```java
        while (!stack.isEmpty()) {
        result.add(stack.pop());
        }
        ```
Since post-order pushes "dependents" before "dependencies" onto the stack... actually, more precisely: a node's *prerequisites get finished and pushed before the node itself* is pushed... let's just verify with a tiny trace.

---

        **Quick trace to confirm ordering, using edges `0 -> 1 -> 2`:**

        ```
hasCycle(0): visited[0]=true, inStack[0]=true
neighbor 1: not visited, recurse
hasCycle(1): visited[1]=true, inStack[1]=true
neighbor 2: not visited, recurse
hasCycle(2): visited[2]=true, inStack[2]=true
no neighbors
inStack[2]=false, stack.push(2)   stack=[2]
inStack[1]=false, stack.push(1)     stack=[2,1]
inStack[0]=false, stack.push(0)       stack=[2,1,0]

Pop stack: 0, 1, 2   -> result = [0, 1, 2]  ✓ correct order
```

The node with no dependents (`2`) gets pushed first (bottom of stack), and the node with the most things depending on it (`0`) gets pushed last (top of stack) — so popping gives you `0, 1, 2`, the correct dependency order.

        ---

Both versions return the same valid topological ordering (there may be multiple valid orderings if the graph allows it — these two methods might return *different* valid orderings from each other, both are acceptable).

Want to now build the Alien Dictionary edge-extraction step, and plug either of these topo-sort versions in?






 **Kahn's Algorithm (BFS-based) — Time & Space Complexity**

 Let `V` = number of nodes, `E` = number of edges.

 **Time:**

 ```java
 for (int u = 0; u < n; u++) {
 for (int v : adj.get(u)) {
 inDegree[v]++;
 }
 }
 ```
 This visits every node once (`O(V)`) and every edge exactly once across all adjacency lists combined (`O(E)`) → **O(V + E)**

 ```java
 for (int i = 0; i < n; i++) {
 if (inDegree[i] == 0) queue.offer(i);
 }
 ```
 One pass over all nodes → **O(V)**

 ```java
 while (!queue.isEmpty()) {
 int u = queue.poll();
 result.add(u);
 for (int v : adj.get(u)) {
 inDegree[v]--;
 if (inDegree[v] == 0) queue.offer(v);
 }
 }
 ```
 Each node is polled from the queue **at most once** (once it's added, in-degree only ever decreases, never resets) → `O(V)` total pops. Each node's adjacency list is iterated over exactly once, across the whole run → `O(E)` total edge relaxations.

 **Total for the while loop: O(V + E)**

 **Combine all parts: O(V + E) + O(V) + O(V + E) = O(V + E)**

 **Space:**
 - `inDegree[]` array: `O(V)`
 - `queue`: holds at most `V` nodes at any time → `O(V)`
 - `result` list: `O(V)`
 - adjacency list itself (input, but often counted): `O(V + E)`

 **Total Space: O(V + E)**

 ---

 **DFS-based (with `visited`/`inStack`) — Time & Space Complexity**

 **Time:**

 ```java
 for (int i = 0; i < n; i++) {
 if (!visited[i]) {
 hasCycle(i, ...);
 }
 }
 ```
 The outer loop runs `O(V)` times, but `hasCycle` only actually recurses into a node if it's **not yet visited** — so across the *entire* program, each node's DFS body (`hasCycle`) executes exactly **once**, regardless of how many times the outer loop or a neighbor tries to call it.

 Inside `hasCycle`, each edge `u -> v` is examined exactly once (once per adjacency list traversal, and each edge appears in exactly one node's list) → `O(E)` total across all calls combined.

 **Total Time: O(V + E)** — same order as Kahn's, standard for any DFS-based graph traversal.

 **Space:**
 - `visited[]`, `inStack[]` arrays: `O(V)` each → `O(V)`
 - `stack` (holding post-order result): up to `O(V)`
 - **Recursion call stack**: worst case (e.g., a long chain graph with no branching) depth = `O(V)`
 - adjacency list: `O(V + E)`

 **Total Space: O(V + E)**

 ---

 **Summary — both approaches are asymptotically identical:**

 ```
 Time         Space
 Kahn's (BFS):   O(V + E)     O(V + E)
 DFS-based:      O(V + E)     O(V + E)
 ```

 Same complexity class as your other topo-sort work (Course Schedule 207/210) — makes sense, since the underlying operation (visit every node once, every edge once) doesn't change regardless of which traversal strategy drives it. The practical difference is usually about ease of cycle detection (Kahn's: compare `result.size()` to `n`; DFS: explicit `inStack` check) and whether recursion depth is a concern for very large/deep graphs (DFS risks stack overflow on pathological inputs;
 Kahn's, being iterative, doesn't).
 */