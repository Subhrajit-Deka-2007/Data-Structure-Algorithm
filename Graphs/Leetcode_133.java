package Graphs;

import java.util.*;

public class Leetcode_133
{
    class Node {
        public int val;
        public List<Node> neighbors;
        Node( int val )
        {
            this.val = val;
        }
    }
    class Solution {
        public Node cloneGraph(Node node)
        {
            /* return bfs(node); */

            Map<Node, Node> visited = new HashMap<>();
            return dfs(visited ,node);

        }

        /*
        Time Complexity: O(V + E)
        Each node is enqueued exactly once (guarded by visited.containsKey), so the while loop runs V times.
        Inside the loop, you iterate curr.neighbors — across all nodes, this totals E edge-visits (each edge examined from one direction, since neighbor lists are typically symmetric for undirected graphs, so it's really 2×edges if you count strictly, but asymptotically it's O(E)).

        So total work = O(V) node dequeues + O(E) neighbor visits = O(V + E)

        Space Complexity: O(V)
        */
        public Node bfs( Node node )
        {
            if (node == null) return null;

            Map<Node, Node> visited = new HashMap<>();
            Queue<Node> q = new ArrayDeque<>();

            Node clone = new Node(node.val);
            visited.put(node, clone);
            q.add(node);

            while (!q.isEmpty()) {
                Node curr = q.poll();

                for (Node neighbor : curr.neighbors) {
                    if (!visited.containsKey(neighbor)) {
                        visited.put(neighbor, new Node(neighbor.val));
                        q.add(neighbor);
                    }
                    visited.get(curr).neighbors.add(visited.get(neighbor));
                }
            }

            return clone;
        }

        public Node dfs( Map<Node,Node>visited, Node node )
        {
            if( node == null ) return null;

            Node clone = new Node(node.val);
            visited.put(node, clone);

            for (Node neighbor : node.neighbors)
            {
                if (!visited.containsKey(neighbor))dfs(visited,neighbor);
                clone.neighbors.add(visited.get(neighbor));

            }
            return clone;
        }
/*
Time Complexity: O(V + E)

Same reasoning as BFS:

Each node gets cloned exactly once (guarded by visited.containsKey), so dfs recurses into a "new" node V times total.
For each node visited, you loop through its neighbors list once. Summed across all nodes, this is O(E) total edge-visits.

So: O(V) node-clones + O(E) neighbor-scans = O(V + E)

Space Complexity: O(V)


Structure	Space
visited HashMap	O(V)
Recursion call stack	O(V) worst case (e.g., a long chain/path graph → depth V)
*/
    }

}
