package Graphs;

import java.util.*;

public class Leetcode_417
{
    class Solution {
        public List<List<Integer>> pacificAtlantic1(int[][] heights) {
            int rows = heights.length;
            int cols = heights[0].length;
            List<List<Integer>> result = new ArrayList<>();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    boolean[][] visited = new boolean[rows][cols];
                    dfs(r, c, heights, visited);

                    boolean reachesPacific = false;
                    boolean reachesAtlantic = false;

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            if (visited[i][j]) {
                                if (i == 0 || j == 0) reachesPacific = true;
                                if (i == rows - 1 || j == cols - 1) reachesAtlantic = true;
                            }
                        }
                    }

                    if (reachesPacific && reachesAtlantic) {
                        result.add(Arrays.asList(r, c));
                    }
                }
            }

            return result;
        }

        public void dfs(int r, int c, int[][] heights, boolean[][] visited) {
            visited[r][c] = true;

            int rows = heights.length;
            int cols = heights[0].length;
            int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

            for (int[] d : dirs) {
                int newR = r + d[0];
                int newC = c + d[1];

                if (newR >= 0 && newR < rows && newC >= 0 && newC < cols
                        && !visited[newR][newC]
                        && heights[newR][newC] <= heights[r][c]) {

                    dfs(newR, newC, heights, visited);
                }
            }
        }

/*
**Time Complexity: O((rows × cols)²)**

Let `m = rows`, `n = cols`, and `N = m × n` (total cells).

- Outer double loop: runs once per cell → `N` iterations.
- For each cell, you call `dfs`, which in the worst case visits every other cell → up to `N` work.
- Plus, scanning the `visited[][]` array afterward to check borders → another `O(N)` per cell.

So each of the `N` starting cells costs `O(N)` work → total = `O(N × N) = O(N²) = O((m×n)²)`.

**Space Complexity: O(rows × cols) = O(N)**

- The `visited[][]` array is size `m × n`, recreated fresh for each starting cell (but only one exists at a time, not accumulated).
- The recursion (call stack) for DFS can go as deep as `N` in the worst case (e.g., a long snake-like path touching every cell).
- So auxiliary space per call is `O(N)`, and since you don't keep multiple `visited` arrays alive simultaneously, total space stays `O(N)`, not `O(N²)`.

**Worst case scenario:**

The worst case happens when the grid is **completely flat** (all heights equal) or arranged so that one DFS call can wander through nearly every cell before terminating — since the flow condition is `≤` (equal heights allowed), a flat grid lets DFS visit *all* `N` cells from *any* starting cell.

Example: a `500 × 500` grid where every height is `5`.
- `N = 250,000`
- Every single-cell DFS visits all 250,000 cells.
- Total work ≈ `250,000 × 250,000 = 6.25 × 10^10` operations.

That's why this fails on LeetCode for larger grids — it's technically correct but explodes way past acceptable runtime (well beyond ~10^8 operations, which is the rough limit for a 1-2 second time limit).

Ready to move to the optimized reverse-BFS/DFS approach that brings this down to `O(N)`?
*/


        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            int rows = heights.length;
            int cols = heights[0].length;
            List<List<Integer>> result = new ArrayList<>();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    boolean[][] visited = new boolean[rows][cols];
                    bfs(r, c, heights, visited);

                    boolean reachesPacific = false;
                    boolean reachesAtlantic = false;

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            if (visited[i][j]) {
                                if (i == 0 || j == 0) reachesPacific = true;
                                if (i == rows - 1 || j == cols - 1) reachesAtlantic = true;
                            }
                        }
                    }

                    if (reachesPacific && reachesAtlantic) {
                        result.add(Arrays.asList(r, c));
                    }
                }
            }

            return result;
        }

        public void bfs(int startR, int startC, int[][] heights, boolean[][] visited) {
            int rows = heights.length;
            int cols = heights[0].length;
            int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startR, startC});
            visited[startR][startC] = true;

            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];

                for (int[] d : dirs) {
                    int newR = r + d[0];
                    int newC = c + d[1];

                    if (newR >= 0 && newR < rows && newC >= 0 && newC < cols
                            && !visited[newR][newC]
                            && heights[newR][newC] <= heights[r][c]) {

                        visited[newR][newC] = true;
                        queue.offer(new int[]{newR, newC});
                    }
                }
            }
        }
    }
/*
**Time Complexity: O((rows × cols)²)**

Same as DFS, since the fundamental work per cell doesn't change:

- Outer double loop over all cells → `N = rows × cols` starting points.
- For each starting cell, BFS explores up to `N` cells in the worst case (flat grid).
- Plus scanning `visited[][]` afterward to check borders → another `O(N)` per starting cell.

Total: `N` cells × `O(N)` work each = `O(N²) = O((rows × cols)²)`.

**Space Complexity: O(rows × cols) = O(N)**

- `visited[][]` array → `O(N)`.
- The BFS `Queue` can hold up to `O(N)` cells in the worst case (e.g., all cells at the same height get enqueued before being processed).
- No recursive call stack this time (that's the main structural difference from DFS) — but the queue plays the equivalent role, so space stays `O(N)`, same order as DFS.

**Worst case:** identical to DFS — a flat grid (or one where BFS from any single cell wanders through nearly all `N` cells) forces every one of the `N` starting points to do `O(N)` work, giving `O(N²)` total.

So DFS and BFS are equivalent here in both time and space — the only difference is *how* you traverl (stack vs queue), not the complexity class. The real fix is changing *where you start the traversal from*, which is what the optimized reverse multi-source approach does next.
*/
}
