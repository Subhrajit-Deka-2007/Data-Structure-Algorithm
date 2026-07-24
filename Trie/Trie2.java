package Trie;
import java.util.HashMap;
import java.util.Map;


public class Trie2
{
        TRIE_NODE root;
        public Trie2() {
            root = new TRIE_NODE();
        }
        public void insert(String word) {
            TRIE_NODE current = root;
            for (char c : word.toCharArray()) {
                current.children.putIfAbsent(c, new TRIE_NODE());
                current = current.children.get(c);
            }
            current.isEndOfWord = true;
        }
        public boolean search(String word) {
            TRIE_NODE current = root;
            for (char c : word.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    return false;
                }
                current = current.children.get(c);
            }
            return current.isEndOfWord;
        }
        public boolean startsWith(String prefix) {
            TRIE_NODE current = root;
            for (char c : prefix.toCharArray()) {
                if (!current.children.containsKey(c)) {
                    return false;
                }
                current = current.children.get(c);
            }
            return true;
        }
    }


    class TRIE_NODE
    {
        Map<Character, TRIE_NODE> children = new HashMap<>();
        boolean isEndOfWord = false;
    }
/*
Time Complexity :
insert: O(m)
search: O(m)
startsWith: O(m)


Space Complexity :
O(m) — lowercase o, and m stands for the length of the word (or prefix) you're inserting/searching.

one insert call → O(m) extra space
whole trie → O(N) total space, N = sum of lengths of all unique-path characters
 */
