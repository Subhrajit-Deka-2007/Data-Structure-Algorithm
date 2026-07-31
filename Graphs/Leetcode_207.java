package Graphs;

import java.util.*;

public class Leetcode_207 {
    class Solution {
        public boolean canFinish0(int numCourses, int[][] prerequisites) {

            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < numCourses; i++) adjList.add(new ArrayList<>());

            for (int[] pair : prerequisites) {
                adjList.get(pair[1]).add(pair[0]);
            }

            int[] state = new int[numCourses];

            for (int i = 0; i < numCourses; i++) {
                if (state[i] == 0) { // only start a fresh DFS if unvisited
                    if (hasCycle(i, adjList, state)) {
                        return false;
                    }
                }
            }

            return true;
        }


        private boolean hasCycle(int course, List<List<Integer>> adjList, int[] state) {
            if (state[course] == 1) return true;  // found a VISITING node again → cycle!
            if (state[course] == 2) return false; // already confirmed safe, no cycle here

            state[course] = 1; // mark as VISITING — we're now exploring this course

            for (int next : adjList.get(course)) {
                if (hasCycle(next, adjList, state)) {
                    return true; // cycle found somewhere deeper
                }
            }

            state[course] = 2; // mark as DONE — fully explored, confirmed safe
            return false;
        }
/*
Time: O(V + E) — every node and edge visited once
Space: O(V + E) — adjacency list, plus O(V) for the state array and recursion stack



The 3 states, explained with a story

Imagine you're DFS-ing through courses, trying to figure out if you can complete them all. For every course, it's in one of three states:

UNVISITED — you haven't even looked at this course yet
VISITING — you're currently "in the middle of" trying to complete this course (you started exploring its prerequisites, but haven't finished yet — it's on your current call stack)
DONE — you fully confirmed this course (and everything it depends on) is safe, no cycle involving i
*/


        public boolean canFinish1(int numCourses, int[][] prerequisites) {
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());

            for (int[] pre : prerequisites) {
                int a = pre[0], b = pre[1];
                adj.get(b).add(a);   // edge: b -> a
            }

            int[] color = new int[numCourses]; // 0=WHITE, 1=GRAY, 2=BLACK

            for (int i = 0; i < numCourses; i++) {
                if (color[i] == 0) {
                    if (hasCycle(i, color, adj)) return false; // cycle found
                }
            }

            return true; // no cycle anywhere = can finish all


        }

        private boolean hasCycle(int node, int[] color, List<List<Integer>> adj) {
            // grey , red , black method
            color[node] = 1; // GRAY - in current DFS path

            for (int neighbor : adj.get(node)) {
                if (color[neighbor] == 1) return true;              // back edge -> cycle
                if (color[neighbor] == 0 && hasCycle(neighbor, color, adj)) return true;
            }

            color[node] = 2; // BLACK - fully explored, safe
            return false;
        }

/*
Both are O(V + E) time and space. Pick whichever you're faster at coding under pressure — most people find Kahn's slightly more mechanical/easier to get right the first time.
*/
    }

    public List<Integer> topoSortDFS2(int n, List<List<Integer>> adj) {
        boolean[] visited = new boolean[n];
        boolean[] inStack = new boolean[n];  // tracks current recursion path (for cycle detection)
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (dfs(i, visited, inStack, adj, stack)) {
                    return new ArrayList<>(); // cycle found, no valid topo order
                }
            }
        }

        // stack is in reverse topo order — pop to get correct order
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private boolean dfs(int node, boolean[] visited, boolean[] inStack,
                        List<List<Integer>> adj, Deque<Integer> stack) {
        visited[node] = true;
        inStack[node] = true; // mark as part of current path

        for (int neighbor : adj.get(node)) {
            if (inStack[neighbor]) return true; // back edge -> cycle
            if (!visited[neighbor] && dfs(neighbor, visited, inStack, adj, stack)) {
                return true;
            }
        }

        inStack[node] = false; // done exploring this path, unmark
        stack.push(node); // push AFTER exploring all neighbors
        return false;
    }

    /**
     * Kahn's Algorithm
     */
    public List<Integer> topoSortBFS(int n, List<List<Integer>> adj) {
        int[] inDegree = new int[n];

        // Step 1: calculate in-degree for every node
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                inDegree[v]++;
            }
        }

        // Step 2: seed queue with all in-degree 0 nodes
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }

        List<Integer> result = new ArrayList<>();

        // Step 3: process queue
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int neighbor : adj.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // Step 4: cycle check
        if (result.size() != n) {
            return new ArrayList<>(); // cycle exists, no valid topo order
        }

        return result;
    }
}