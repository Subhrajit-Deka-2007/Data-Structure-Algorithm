package Trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Leetcode_211
{

    class WordDictionary {

        TRIE_NODE root;
        public WordDictionary()
        {
            root = new TRIE_NODE();
        }

        public void addWord(String word)
        {
            TRIE_NODE current = root;
            for (char c : word.toCharArray())
            {
                current.children.putIfAbsent(c, new TRIE_NODE());
                current = current.children.get(c);
            }
            current.isEndOfWord = true;
        }

        public boolean search(String word)
        {

            Set<TRIE_NODE> alive = new HashSet<>();
            alive.add(root);

            for (char c : word.toCharArray()) {
                Set<TRIE_NODE> nextAlive = new HashSet<>();
                for (TRIE_NODE node : alive) {
                    if (c == '.') {
                        nextAlive.addAll(node.children.values());
                    } else {
                        if (node.children.containsKey(c)) {
                            nextAlive.add(node.children.get(c));
                        }
                    }
                }
                alive = nextAlive;
                if (alive.isEmpty()) return false;
            }

            for (TRIE_NODE node : alive) {
                if (node.isEndOfWord) return true;
            }
            return false;
        }
    }
    class TRIE_NODE
    {
        Map<Character, TRIE_NODE> children = new HashMap<>();
        boolean isEndOfWord = false;
    }
/**
 Summary

 Let m = length of the search word, k = number of unique characters possible per node (26 for lowercase letters).

 Time complexity
 Best case (no dots): O(m), same as a normal trie search, one step per character.
 Worst case (all dots): at each dot, the alive set can fan out by up to k. Total work across all levels is k + k^2 + k^3 + ... + k^m, which by the GP sum formula simplifies to O(k^m), since the last term dominates the whole sum.

 Space complexity
 Best case (no dots): O(m), just tracking one node at a time (or O(1) extra if you don't count the input itself).
 Worst case (all dots): the alive set can hold up to k^m nodes at its peak (the deepest level of fan-out). Since older levels get replaced (alive = nextAlive) and are eligible for garbage collection, you only ever need to hold the size of ONE level at a time, so space is O(k^m) directly, not a sum like time complexity.

 Bottom line: dots are the expensive part, each one can multiply your possibilities by up to k, so a word full of dots against a densely packed trie gives O(k^m) for both time and space in the worst case, while a word with no dots stays cheap at O(m).
 */
/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
}
