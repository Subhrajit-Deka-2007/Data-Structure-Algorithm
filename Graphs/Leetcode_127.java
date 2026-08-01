package Graphs;

import java.util.*;

public class Leetcode_127
{

    class Solution
    {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> wordSet = new HashSet<>(wordList);

            if (!wordSet.contains(endWord)) return 0; // target isn't even reachable

            Queue<String> queue = new ArrayDeque<>();
            queue.offer(beginWord);
            wordSet.remove(beginWord); // treat beginWord as "visited"

            int steps = 1;

            while (!queue.isEmpty()) {
                int size = queue.size(); // everything in the queue right now = current layer

                for (int k = 0; k < size; k++) {
                    String word = queue.poll();

                    if (word.equals(endWord)) {
                        return steps;
                    }

                    char[] chars = word.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char original = chars[i];

                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == original) continue;
                            chars[i] = c;
                            String newWord = new String(chars);

                            if (wordSet.contains(newWord)) {
                                wordSet.remove(newWord); // mark visited by removing from the set
                                queue.offer(newWord);
                            }
                        }
                        chars[i] = original; // restore before trying the next position
                    }
                }

                steps++;
            }

            return 0; // queue emptied, never reached endWord
        }
    }
/**
 Time: O(N × L × 26) — N = number of words, L = word length, 26 = alphabet size for generating variants
 Space: O(N × L) — the wordSet and queue
 */
}
