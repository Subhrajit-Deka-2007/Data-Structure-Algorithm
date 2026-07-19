package Binary_Search;

public class Leetcode_981
{

    class TimeMap {
        Map<String, List<String[]>> store;

        public TimeMap() {
            store = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            store.putIfAbsent(key, new ArrayList<>());
            store.get(key).add(new String[]{String.valueOf(timestamp), value});
        }

        public String get(String key, int timestamp) {
            if (!store.containsKey(key)) return "";

            List<String[]> entries = store.get(key);
            int left = 0, right = entries.size() - 1;
            String result = "";

            while (left <= right) {
                int mid = left + (right - left) / 2;
                int midTimestamp = Integer.parseInt(entries.get(mid)[0]);

                if (midTimestamp <= timestamp) {
                    result = entries.get(mid)[1];
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return result;
        }
    }
/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */


    /**
     ## Time Based Key-Value Store (981) — Approach Summary

     ## The core structure

     **A `HashMap<String, List<String[]>>`** — maps each key to a list of `[timestamp, value]` pairs, in the order they were set.

     ```java
     Map<String, List<String[]>> store;
     ```

     ## Why this structure — two levels of lookup

     **Level 1 (HashMap):** handles multiple independent keys — "foo," "love," etc. — each with its own separate history.

     **Level 2 (List, per key):** holds every `(timestamp, value)` pair ever set for THAT specific key, in the order they arrived.

     ## Why the list is automatically sorted — no manual sorting needed

     **The problem guarantees timestamps for the SAME key are strictly increasing across `set()` calls.** Since we simply `.add()` new entries to the end of the list, this guarantee means the list is sorted by construction — never needs re-sorting.

     ## `set(key, value, timestamp)`

     ```
     1. If this key has never been seen before, create a new empty list for it.
     2. Append (timestamp, value) to that key's list.
     ```
     **Cost: O(1)** — just an append.

     ## `get(key, timestamp)`

     ```
     1. If the key has never been set at all, return "" immediately.
     2. Binary search the key's sorted list, looking for the LARGEST timestamp that is <= the query timestamp.
     3. Return the value associated with that timestamp (or "" if none qualifies).
     ```

     **The binary search variant used:** instead of stopping at an exact match, keep a "running best" result whenever `midTimestamp <= target`, and keep searching RIGHT for an even closer (larger, but still valid) timestamp — only stop once the search range is exhausted.

     **Cost: O(log n)**, where n = number of entries for that specific key.

     ## The mental model

     **Think of it as a filing cabinet: each key has its own folder, and inside each folder, sticky notes are added in time order. `set()` just appends a new sticky note. `get()` binary searches within one folder to find the most recent applicable note at or before the queried time.**

     ## Complexity

     ```
     set(): O(1)
     get(): O(log n) — n = entries for that specific key
     Space: O(total number of set() calls across all keys)
     ```

     That's Binary Search down to just **1 problem left: Median of Two Sorted Arrays (4)** — the Hard-rated one. Want a hint for it, or take a short break given this was a fairly involved problem to fully unpack?
     */
}
