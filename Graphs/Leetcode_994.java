package Graphs;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode_994
{
    class Solution
    {
        public int orangesRotting(int[][] grid)
        {

            Queue<int[]> queue = new ArrayDeque<>();
            int fresh = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                    else if (grid[i][j] == 1) fresh++;
                }
            }

            if (fresh == 0) return 0;

            int minutes = 0;
            int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

            while (!queue.isEmpty()) {
                int size = queue.size(); // everything currently in queue = ONE minute's worth of rotten oranges
                boolean rottedAny = false;

                for (int k = 0; k < size; k++) {
                    int[] curr = queue.poll();
                    for (int[] d : dirs) {
                        int nr = curr[0] + d[0], nc = curr[1] + d[1];
                        if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == 1) {
                            grid[nr][nc] = 2;
                            fresh--;
                            queue.offer(new int[]{nr, nc});
                            rottedAny = true;
                        }
                    }
                }
                if (rottedAny) minutes++;
            }
/**
 * Here's the direct answer, isolated to just that one decision:
 *
 * ## The rule
 * **Use the `size = queue.size()` loop pattern only when the problem asks you to count something that increases by exactly 1 per "round" of BFS** — minutes, distance, number of steps, levels.
 *
 * **Skip it when the problem only asks "can this be reached / is this connected"** — a yes/no or true/false answer, with no number attached.
 *
 * ## How to tell which one your problem is, before writing any code
 * Ask yourself: **"Does my final answer need to be a number that represents *how far* or *how long*?"**
 *
 * - "How many **minutes** until all oranges rot?" → number → **use the loop**
 * - "What is the **distance** to the nearest gate?" → number → **use the loop**
 * - "**Can** water flow from this cell to the ocean?" → yes/no → **skip the loop**
 * - "**Is** this region surrounded?" → yes/no → **skip the loop**
 *
 * ## What changes in the code, concretely
 *
 * **With the loop (need a count):**
 * ```java
 * while (!queue.isEmpty()) {
 *     int size = queue.size();        // <-- this line only exists because you need to count rounds
 *     for (int i = 0; i < size; i++) {
 *         int[] curr = queue.poll();
 *         // expand neighbors...
 *     }
 *     counter++;                       // <-- one increment per full round
 * }
 * ```
 *
 * **Without the loop (just reachability):**
 * ```java
 * while (!queue.isEmpty()) {
 *     int[] curr = queue.poll();      // just poll one at a time, no batching
 *     // expand neighbors...
 * }
 * // no counter anywhere — you just check a boolean array at the end
 * ```
 *
 * ## Why it matters mechanically
 * The `size = queue.size()` line's **only job** is to let you know **"I've finished processing everyone at the current distance, time to move to the next distance and bump my counter."** If you don't need to know *which* distance/round something was reached
 * at — only *whether* it was reached at all — that bookkeeping serves no purpose, so it's dropped.
 */
            return fresh == 0 ? minutes : -1;
        }
    }
/*
**Time Complexity: O(rows × cols)**

- The initial nested loop to scan the grid: O(rows × cols)
- BFS: every cell enters the queue **at most once** (it only gets added when it flips from 1→2, and it can only flip once). Each dequeued cell does O(1) work (checks 4 neighbors).
- So total BFS work is O(rows × cols) too.

Combined: **O(rows × cols)**, i.e., O(n × m).

**Space Complexity: O(rows × cols)**

- Worst case, the queue can hold up to O(rows × cols) cells — e.g., if the entire grid is rotten oranges initially, all of them go into the queue at once.
- You're mutating `grid` in-place (no extra grid needed), so no additional O(n×m) structure beyond the queue.

So both time and space are **O(n × m)** where n = rows, m = cols.

One small note unrelated to complexity: your `minutes++` is guarded by `rottedAny`, which correctly avoids over-counting the last "empty" round where nothing new rots — nice catch, a lot of people get this off-by-one wrong.
*/
}
