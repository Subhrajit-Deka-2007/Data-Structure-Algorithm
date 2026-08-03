package Graphs;

import java.util.PriorityQueue;

public class Leetcode_778
{




        private int minTime = Integer.MAX_VALUE;

        public int swimInWater0(int[][] grid) {
            int n = grid.length;
            boolean[][] visited = new boolean[n][n];
            visited[0][0] = true;

            backtrack(grid, 0, 0, visited, grid[0][0]);

            return minTime;
        }

        private void backtrack(int[][] grid, int row, int col, boolean[][] visited, int currentMax) {
            int n = grid.length;

            // Base case: reached the destination
            if (row == n - 1 && col == n - 1) {
                minTime = Math.min(minTime, currentMax);
                return;
            }

            int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Check bounds and visited
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n
                        && !visited[newRow][newCol]) {

                    visited[newRow][newCol] = true;
                    int newMax = Math.max(currentMax, grid[newRow][newCol]);

                    backtrack(grid, newRow, newCol, visited, newMax);

                    visited[newRow][newCol] = false; // undo — THIS is the actual backtrack step
                }
            }
        }


        public int swimInWater(int[][] grid) {
            int n = grid.length;
            boolean[][] visited = new boolean[n][n];

            // Min-heap ordered by bottleneck (max elevation seen so far on path to this cell)
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            pq.offer(new int[]{grid[0][0], 0, 0}); // {bottleneck, row, col}

            int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};

            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int bottleneck = curr[0];
                int row = curr[1];
                int col = curr[2];

                if (visited[row][col]) continue; // stale entry, skip
                visited[row][col] = true;

                // Reached destination — this IS the answer, guaranteed minimal
                if (row == n - 1 && col == n - 1) {
                    return bottleneck;
                }

                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n
                            && !visited[newRow][newCol]) {

                        int newBottleneck = Math.max(bottleneck, grid[newRow][newCol]);
                        pq.offer(new int[]{newBottleneck, newRow, newCol});
                    }
                }
            }

            return -1; // unreachable, shouldn't happen per problem constraints
        }


/*
Backtracking (all paths):  O(4^(n²))       <- exponential, infeasible for n≥~5
DFS Appraoch : (Backtracking ): ---> O( (4^N^2)Space Complexity : O(N^2)
Dijkstra-style (heap):     O(n² log n)      <- polynomial, runs fine even for n=50
*/


        public int swimInWater1(int[][] grid) {
            int n = grid.length;
            int lo = grid[0][0], hi = n * n - 1;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (canReach(grid, mid)) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            return lo;
        }

        private boolean canReach(int[][] grid, int t) {
            int n = grid.length;
            if (grid[0][0] > t) return false;
            boolean[][] visited = new boolean[n][n];
            return dfs(grid, 0, 0, t, visited);
        }

        private boolean dfs(int[][] grid, int row, int col, int t, boolean[][] visited) {
            int n = grid.length;
            if (row < 0 || row >= n || col < 0 || col >= n) return false;
            if (visited[row][col] || grid[row][col] > t) return false;
            visited[row][col] = true;
            if (row == n - 1 && col == n - 1) return true;
            int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
            for (int[] dir : directions) {
                if (dfs(grid, row + dir[0], col + dir[1], t, visited)) return true;
            }
            return false;
        }

/**
 Here's the complete solution, followed by the full complexity derivation.

 ```java
 class Solution {
 public int swimInWater(int[][] grid) {
 int n = grid.length;
 int lo = grid[0][0], hi = n * n - 1;

 while (lo < hi) {
 int mid = lo + (hi - lo) / 2;
 if (canReach(grid, mid)) {
 hi = mid;
 } else {
 lo = mid + 1;
 }
 }

 return lo;
 }

 private boolean canReach(int[][] grid, int t) {
 int n = grid.length;
 if (grid[0][0] > t) return false;
 boolean[][] visited = new boolean[n][n];
 return dfs(grid, 0, 0, t, visited);
 }

 private boolean dfs(int[][] grid, int row, int col, int t, boolean[][] visited) {
 int n = grid.length;
 if (row < 0 || row >= n || col < 0 || col >= n) return false;
 if (visited[row][col] || grid[row][col] > t) return false;
 visited[row][col] = true;
 if (row == n - 1 && col == n - 1) return true;
 int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
 for (int[] dir : directions) {
 if (dfs(grid, row + dir[0], col + dir[1], t, visited)) return true;
 }
 return false;
 }
 }
 ```

 ---

 **Time Complexity — building it up piece by piece**

 **Piece 1: How many times does the binary search loop run?**

 ```java
 int lo = grid[0][0], hi = n * n - 1;
 while (lo < hi) {
 ```

 The search range is `[0, n² - 1]` — total range size `≈ n²`. Standard binary search halves the range each iteration, so the number of iterations needed to shrink a range of size `n²` down to a single value is:

 ```
 log₂(n²)
 ```

 Using the log rule `log(n²) = 2 log(n)`, drop the constant `2`:

 **→ O(log n)** iterations of the `while` loop.

 **Piece 2: How much work does EACH iteration do?**

 Each iteration calls `canReach(grid, mid)` exactly once:
 ```java
 if (canReach(grid, mid)) { ... }
 ```

 Inside `canReach`:
 ```java
 boolean[][] visited = new boolean[n][n];
 return dfs(grid, 0, 0, t, visited);
 ```

 - Allocating `visited` is `O(n²)` (has to zero-initialize `n × n` cells).
 - The `dfs` call: in the worst case, it visits every cell in the grid exactly once (standard grid-DFS bound — each cell is marked `visited` the first time it's reached, and never revisited, so the total work across the whole DFS call is bounded by the number of cells). Each cell does `O(1)` work (bounds check, elevation check, loop over 4 directions).

 **So one full `canReach` call costs O(n²)** — same bound as any single-pass grid traversal (Islands, Pacific Atlantic, etc.).

 **Piece 3: Combine**

 ```
 Total Time = (number of binary search iterations) × (cost per iteration)
 = O(log n) × O(n²)
 = O(n² log n)
 ```

 **This matches exactly what you said: `O(log n)` binary search iterations, each doing `O(n²)` work in the worst case** — multiplying those two gives the final bound.

 ---

 **Space Complexity**

 - `visited` array: `O(n²)` — but note, it's **recreated fresh on every `canReach` call**, not accumulated across calls. So at any given moment, only one `O(n²)` array exists — space doesn't multiply by `log n`, since old arrays get garbage collected once each `canReach` call returns.
 - Recursion stack for `dfs`: worst case, a single path could wind through nearly all `n²` cells before hitting the destination or dead-ending → `O(n²)` stack depth.

 **Total Space: O(n²)**

 ---

 **Final comparison across all three approaches you've now built for this problem:**

 ```
 Brute-force backtracking (all paths): O(4^(n²))        <- infeasible
 Dijkstra-style (priority queue):      O(n² log n)        <- single pass
 Binary search + DFS:                  O(n² log n)        <- log n separate passes
 ```

 Both the Dijkstra approach and the binary-search+DFS approach land at the **same Big-O class**, `O(n² log n)` — but they arrive there differently: Dijkstra's does it in **one** pass with a heap doing the `log n` work per operation, while binary search does it in **`log n` separate, independent** `O(n²)` passes. Same asymptotic order, but Dijkstra's typically has a smaller constant factor in practice (no repeated re-traversal of the same cells across iterations).





 ## Summary: Binary Search + DFS approach for LeetCode 332 (Swim in Rising Water)

 **Core idea:**
 Instead of directly computing the minimum bottleneck water level, reframe it as a yes/no question asked repeatedly: *"At water level `t`, can I reach `(n-1,n-1)` from `(0,0)`?"* Then binary search over `t` to find the smallest value where the answer flips from `false` to `true`.

 **Why this works — monotonicity:**
 If you can reach the destination at threshold `t`, you can also reach it at any `t' > t` (more cells become walkable, never fewer). This monotonic property is what makes binary search valid.

 **The two building blocks:**

 1. **`canReach(grid, t)`** — a plain grid-connectivity DFS (same shape as Number of Islands), except a cell is "walkable" only if `grid[row][col] <= t`. Returns `true`/`false`.

 2. **Binary search wrapper** — searches over `t` in range `[grid[0][0], n²-1]`:
 - If `canReach(mid)` is true → answer could be ≤ mid → shrink `hi = mid`
 - If false → answer must be > mid → shrink `lo = mid + 1`
 - Converges to the minimum feasible `t`

 **Code:**
 ```java
 class Solution {
 public int swimInWater(int[][] grid) {
 int n = grid.length;
 int lo = grid[0][0], hi = n * n - 1;
 while (lo < hi) {
 int mid = lo + (hi - lo) / 2;
 if (canReach(grid, mid)) hi = mid;
 else lo = mid + 1;
 }
 return lo;
 }

 private boolean canReach(int[][] grid, int t) {
 int n = grid.length;
 if (grid[0][0] > t) return false;
 boolean[][] visited = new boolean[n][n];
 return dfs(grid, 0, 0, t, visited);
 }

 private boolean dfs(int[][] grid, int row, int col, int t, boolean[][] visited) {
 int n = grid.length;
 if (row < 0 || row >= n || col < 0 || col >= n) return false;
 if (visited[row][col] || grid[row][col] > t) return false;
 visited[row][col] = true;
 if (row == n - 1 && col == n - 1) return true;
 int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
 for (int[] dir : directions) {
 if (dfs(grid, row + dir[0], col + dir[1], t, visited)) return true;
 }
 return false;
 }
 }
 ```

 **Complexity:**
 | | Bound | Reason |
 |---|---|---|
 | Time | `O(n² log n)` | `O(log n)` binary search iterations × `O(n²)` per DFS reachability check |
 | Space | `O(n²)` | `visited` array + recursion stack, recreated fresh each `canReach` call |

 **Key trade-off vs. Dijkstra's approach:** same Big-O class (`O(n² log n)`), but arrived at differently — this does `log n` *independent* full grid traversals, while Dijkstra's does it in a single pass. Conceptually simpler since it reuses the plain grid-DFS pattern you already know (Islands, Pacific Atlantic), at the cost of some repeated work across iterations.
 */
}
