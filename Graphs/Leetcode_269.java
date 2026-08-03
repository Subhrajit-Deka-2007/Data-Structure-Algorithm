package Graphs;

import java.util.*;

public class Leetcode_269
{
    class Solution {
        public String alienOrder(String[] words) {
            // Step 1: initialize graph with all distinct letters as nodes
            Map<Character, List<Character>> adj = new HashMap<>();
            Map<Character, Integer> inDegree = new HashMap<>();

            for (String word : words) {
                for (char c : word.toCharArray()) {
                    adj.putIfAbsent(c, new ArrayList<>());
                    inDegree.putIfAbsent(c, 0);
                }
            }

            // Step 2: compare adjacent words to extract edges
            for (int i = 0; i < words.length - 1; i++) {
                String w1 = words[i];
                String w2 = words[i + 1];

                int minLen = Math.min(w1.length(), w2.length());
                boolean foundDifference = false;

                for (int j = 0; j < minLen; j++) {
                    char c1 = w1.charAt(j);
                    char c2 = w2.charAt(j);

                    if (c1 != c2) {
                        adj.get(c1).add(c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                        foundDifference = true;
                        break;
                    }
                }

                // Invalid case: w1 is longer than w2 but w2 is a prefix of w1
                if (!foundDifference && w1.length() > w2.length()) {
                    return "";
                }
            }

            // Step 3: Kahn's BFS topo sort
            Queue<Character> queue = new LinkedList<>();
            for (char c : inDegree.keySet()) {
                if (inDegree.get(c) == 0) {
                    queue.offer(c);
                }
            }

            StringBuilder result = new StringBuilder();

            while (!queue.isEmpty()) {
                char u = queue.poll();
                result.append(u);

                for (char v : adj.get(u)) {
                    inDegree.put(v, inDegree.get(v) - 1);
                    if (inDegree.get(v) == 0) {
                        queue.offer(v);
                    }
                }
            }

            // Step 4: cycle check
            if (result.length() != inDegree.size()) {
                return "";
            }

            return result.toString();
        }
    }
    /*
    Let's derive this directly from the code, piece by piece.

**Variables:**
- `L` = number of words
- `C` = total characters across all words combined
- `U` = number of unique letters (≤ 26)

## Piece 1 — building nodes

```java
for (String word : words) {
    for (char c : word.toCharArray()) {
        adj.putIfAbsent(c, new ArrayList<>());
        inDegree.putIfAbsent(c, 0);
    }
}
```

The inner loop runs once per character, and summed across every word, that's exactly `C` total iterations, each doing O(1) work.

**Cost: O(C)**

## Piece 2 — comparing adjacent words

```java
for (int i = 0; i < words.length - 1; i++) {
    int minLen = Math.min(w1.length(), w2.length());
    for (int j = 0; j < minLen; j++) {
        if (c1 != c2) { ...; break; }
    }
}
```

Each word (except the first and last) participates in **two** comparisons — once as `w2` against its predecessor, once as `w1` against its successor. So across the whole loop, each word's length is examined at most twice, giving total work ≤ `2 × C`.

**Cost: O(C)**

## Piece 3 — Kahn's BFS

```java
for (char c : inDegree.keySet()) {
    if (inDegree.get(c) == 0) queue.offer(c);
}
```
Runs once per unique letter → **O(U)**.

```java
while (!queue.isEmpty()) {
    char u = queue.poll();
    for (char v : adj.get(u)) { ... }
}
```
Each letter is popped at most once (in-degree only decreases, never resets), so the outer loop runs at most `U` times. The inner loop, summed across every pop, visits each edge exactly once — and there's at most one edge per adjacent word pair, so at most `L-1` edges total.

**Cost: O(U + L)**

## Combining

```
O(C) + O(C) + O(U + L) = O(C + U + L)
```

**Simplifying:**
- Every word has at least 1 character, so `L ≤ C` — the `L` term is dominated by `C`, drop it.
- `U ≤ 26` — a constant, drop it.

## Final Answer: **O(C)**

where `C` is the total number of characters across all input words — the algorithm's cost scales with how much text there is to read, not with how many words or how many distinct letters exist.

**Space complexity**, for completeness: `O(U + E)` where `E ≤ L-1` — the graph structure (`adj`, `inDegree`) plus the queue and result string, all bounded by the number of unique letters and edges, so effectively **O(C)** as well
in the worst case (since edges and characters are both bounded by C).
     */
}
