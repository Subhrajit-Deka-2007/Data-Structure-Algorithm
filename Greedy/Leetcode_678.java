package Greedy;

import java.util.Stack;

public class Leetcode_678
{
    class Solution
    {
        public boolean checkValidString(String s) {
            int minOpen = 0;
            int maxOpen = 0;

            for (char c : s.toCharArray()) {
                if (c == '(') {
                    minOpen++;
                    maxOpen++;
                } else if (c == ')') {
                    minOpen--;
                    maxOpen--;
                } else { // c == '*'
                    minOpen--; // Treat '*' as ')'
                    maxOpen++; // Treat '*' as '('
                }

                // If maxOpen drops below 0, there are too many ')'
                if (maxOpen < 0) return false;

                // minOpen cannot be negative (a '*' can just act as empty string)
                if (minOpen < 0) minOpen = 0;
            }

            // Valid if it's possible to have 0 open brackets left
            return minOpen == 0;
        }
/*
Core Intuition: Min-Max Range TrackingBecause '*' can act as '(', ')', or an empty string "", maintaining a single count of open parentheses isn't enough. Instead, track the range of possible open parentheses ([minOpen, maxOpen]):
minOpen: The minimum possible count of unmatched '(' (treating '*' as ')' whenever possible).
maxOpen: The maximum possible count of unmatched '(' (treating '*' as '(' whenever possible).

As you iterate through the string character by character:
1 '(': Increases both minOpen and maxOpen by 1.
2 ')': Decreases both minOpen and maxOpen by 1.
'*':
Could be ')' -> decreases minOpen by 1.
Could be '(' ->increases maxOpen by 1.
Could be "" -> keeps counts unchanged.
Key Conditions:

If maxOpen < 0: We have encountered more ')' than all possible '(' and '*' combined up to this point. Return false.

If minOpen < 0: Reset minOpen = 0. We can't have negative open brackets; it just means some '*' served as empty strings "" instead of ')'.

At the end: If minOpen == 0, it means we can successfully balance all open brackets. Return true.
*/


        public boolean checkValidString1(String s) {
            Stack<Integer> openStack = new Stack<>();
            Stack<Integer> starStack = new Stack<>();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == '(') {
                    openStack.push(i);
                } else if (c == '*') {
                    starStack.push(i);
                } else { // c == ')'
                    if (!openStack.isEmpty()) {
                        openStack.pop(); // Prefer matching actual '('
                    } else if (!starStack.isEmpty()) {
                        starStack.pop(); // Fallback to '*'
                    } else {
                        return false; // No '(' or '*' available to match ')'
                    }
                }
            }

            // Post-processing: Match remaining '(' with '*' coming AFTER them
            while (!openStack.isEmpty() && !starStack.isEmpty()) {
                if (openStack.peek() > starStack.peek()) {
                    return false; // '(' appears after '*', invalid match
                }
                openStack.pop();
                starStack.pop();
            }

            // Valid if all '(' are matched
            return openStack.isEmpty();
        }
/*
Position matters! An asterisk '*' can only close an open parenthesis '(' if the asterisk appears after the open parenthesis in the string (starIndex > openIndex).

Core Intuition & Algorithm
Maintain two stacks:

openStack: Stores the indices of '('.

starStack: Stores the indices of '*'.

Step 1: Process the String Left-to-Right
For each character at index i:

If c == '(': Push index i onto openStack.

If c == '*': Push index i onto starStack.

If c == ')':

Try to pop from openStack first (prefer matching with a real '(').

If openStack is empty, try to pop from starStack (use '*' as '(').

If both are empty, return false (unmatched ')').

Step 2: Match Remaining '(' with '*'
After the loop, if openStack is not empty, match the remaining '(' with available '*':

While both stacks are non-empty:

If openStack.peek() > starStack.peek(), return false. This means a '(' appears after a '*' (e.g., "*( " at indices 0 and 1), so that '*' cannot close it.

Otherwise, pop from both stacks.

Step 3: Final Check
If openStack is completely empty, all '(' were successfully matched. Return true.



Complexity AnalysisTime Complexity: $O(N)$ — Each index is pushed and popped at most once.Space Complexity: $O(N)$ — Requires up to $N$ space for the two stacks.
*/
    }
}
