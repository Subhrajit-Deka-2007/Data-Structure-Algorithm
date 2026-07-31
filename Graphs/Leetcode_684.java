package Graphs;

public class Leetcode_684
{


    class Solution {
        public int[] findRedundantConnection(int[][] edges) {
            /** Solve using DSU(Disjoint Set Union ) */
            int n = edges.length;
            int [] parent ,size;
            parent = new int[n+1];// as 1 based indexing
            size = new int [n+1];
            for(int i =1;i<n+1;i++){
                parent[i]=i;
                size[i]=1;
            }
            int [] ans = new int [2];
            for(int [] arr:edges){
                int u = arr[0],v = arr[1];
                if(leader(u,parent)==leader(v,parent)){
                    // if there is already same so
                    ans[0] =u;
                    ans[1] =v;
                    break;
                }else union(u,v,parent,size);
            }
            return ans;
        }
        /**======================================     DSU START ================================= */
        int leader(int u, int [] parent){
            if(parent[u]==u) return u;
            else return parent[u] = leader(parent[u],parent);
            /* we will also make the leader as parent on backtracking we call it path compression */
        }
        void union(int u, int v,int [] parent,int [] size){
            u = leader(u,parent);
            v = leader(v,parent);
            if(u!=v){
                /* We only do union when leader are differnet and also we do union on the basis of size */
                if(size[u]>size[v]){
                    parent[v]= u;
                    size[u]+=size[v];
                }else{
                    parent[u]=v;
                    size[v]+=size[u];
                }
            }
        }
    }
/*
Let's break down exactly why it's **O(V²)** (or equivalently O(E×V), or O(n²)) for both versions.

### The structure of the algorithm

```
for each edge (u, v) in edges:        <- runs E times
    check if u and v already connected <- this check costs up to O(V)
    if yes: return this edge
    else: add edge to graph
```

So total time = **(number of edges) × (cost of one connectivity check)**

---

### Cost of ONE connectivity check (DFS or BFS)

When you call `connected(u, v)` or `connectedBFS(u, v)`, in the worst case you have to visit **every node currently in the graph** before either finding `v` or exhausting all paths.

```
visited = [false, false, false, ..., false]   <- size V
queue/stack processes up to V nodes
each node's neighbor list is scanned
```

So **one check = O(V)** in the worst case (imagine a graph that's basically a long chain — you might have to walk through almost all nodes to check connectivity).

---

### Putting it together

```
Edge 1: check connectivity -> O(V) work
Edge 2: check connectivity -> O(V) work
Edge 3: check connectivity -> O(V) work
...
Edge E: check connectivity -> O(V) work

Total = E × O(V) = O(E × V)
```

Since in this specific problem **E = V = n** (n edges, n nodes):

```
O(E × V) = O(n × n) = O(n²)
```

---

### Concrete trace to make it click

Say n = 5 (5 nodes, 5 edges). Worst case, the graph grows into a near-complete chain before the cycle edge shows up:

```
Edge 1: graph has 0 nodes  -> DFS touches ~0 nodes
Edge 2: graph has 2 nodes  -> DFS touches ~2 nodes
Edge 3: graph has 3 nodes  -> DFS touches ~3 nodes
Edge 4: graph has 4 nodes  -> DFS touches ~4 nodes
Edge 5: graph has 5 nodes  -> DFS touches ~5 nodes (finds cycle)

Total work ≈ 0+2+3+4+5 = 14 ≈ O(n²) growth pattern
```

As n grows, this sum behaves like `1+2+3+...+n ≈ n²/2`, which is why it's O(n²).

---

### Space Complexity

```
graph adjacency list: O(V + E)   <- stores all nodes/edges added so far
visited array:         O(V)       <- one per connectivity check
recursion stack (DFS only): O(V) worst case (long chain)
queue (BFS only):           O(V) worst case
```

So overall space is **O(V)** (or O(n)).

---

### Why Union-Find beats this

Union-Find with path compression + union by rank turns that "check connectivity" step from **O(V)** down to nearly **O(1)** (technically O(α(n)), the inverse Ackermann function — grows so slowly it's a constant for all practical n). That's why the total becomes:

```
E × O(α(n)) ≈ O(n α(n)) ≈ O(n)
```

That's the entire reason Union-Find is the "expected" optimal solution for this problem in an interview — same core idea (checking connectivity before adding an edge), just a smarter data structure for the check itself.

Want me to walk through Union-Find next, or does this clear up the DFS/BFS complexity for now?
*/



}
