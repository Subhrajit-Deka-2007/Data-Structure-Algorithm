package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class UnionFind
{

        int[] parent;
        int[] size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // everyone is their own leader initially
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return; // already same group

            // union by size: attach smaller group under bigger group
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }
/**
 * Two core operations:

 1. find(x) — returns the leader (root) of x's group. Used to check "which group does x belong to."
 2. union(x, y) — merges x's group and y's group into one. Used when you learn x and y are connected.

 Everything else (path compression, union by size/rank) are just optimizations on top of these two, not separate operations.
 From these two, you can also derive a third useful check:

 3. connected(x, y) — just checks if find(x) == find(y), tells you whether x and y are in the same group.

  That's really it. Every union-find problem boils down to calling union() to build connections, and find() (directly or via connected()) to query them.

 Without optimizations: find and union can be O(n) worst case each (long chain).

 With path compression + union by size/rank (both together): each operation is O(α(n)), which is practically O(1). For n total operations (unions + finds),
 total time is O(n · α(n)), which you just call O(n) in practice.

 Space complexity: O(n) — for the parent array and size array, each of length n.

 That's the standard answer to give in an interview: "O(α(n)) per operation, practically O(1), O(n) space."
 
 */

/**
 Approach : 3

 int[] parent, size;

 // STEP A: find the leader of x, with path compression
 int find(int x) {
 if (parent[x] != x) {
 parent[x] = find(parent[x]); // flatten path
 }
 return parent[x];
 }

 // STEP B: merge groups of x and y
 void union(int x, int y) {
 int rootX = find(x);
 int rootY = find(y);
 if (rootX == rootY) return;          // already same group
 if (size[rootX] < size[rootY]) {
 parent[rootX] = rootY;           // attach smaller to bigger
 size[rootY] += size[rootX];
 } else {
 parent[rootY] = rootX;
 size[rootX] += size[rootY];
 }
 }

 public int longestConsecutive(int[] nums) {
 if (nums.length == 0) return 0;      // edge case: empty array

 int n = nums.length;
 Map<Integer, Integer> indexOf = new HashMap<>(); // value -> index

 parent = new int[n];
 size = new int[n];

 // STEP 1: map each unique value to an index, init union-find
 for (int i = 0; i < n; i++) {
 if (!indexOf.containsKey(nums[i])) {  // skip duplicates
 indexOf.put(nums[i], i);
 parent[i] = i;                    // self leader
 size[i] = 1;                      // group size 1
 }
 }

 // STEP 2: union current number with (number+1) if it exists
 for (int i = 0; i < n; i++) {
 if (indexOf.get(nums[i]) != i) continue; // skip duplicate copies
 if (indexOf.containsKey(nums[i] + 1)) {
 union(i, indexOf.get(nums[i] + 1));
 }
 }

 // STEP 3: find the biggest group size
 int max = 0;
 for (int i = 0; i < n; i++) {
 if (indexOf.get(nums[i]) == i) {          // only check real entries
 max = Math.max(max, size[find(i)]);
 }
 }
 return max;
 }
 /**
 Building the map: O(n)
 Union operations (with path compression + union by size): O(n · α(n)) ≈ O(n)
 Final max-size scan: O(n)
 Total: O(n) overall, O(n) space (map + parent + size arrays).
 That's better than the O(n log n) sorting approach — no log factor, since union-find avoids sorting entirely.
 */

