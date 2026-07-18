package Arrays;

import java.util.ArrayDeque;
import java.util.Deque;

public class Leetocde_84
{
    class Solution {
        public int largestRectangleArea(int[] heights) {
            Deque<Integer> stack = new ArrayDeque<>();
            int maxArea = 0;
            int n = heights.length;

            for (int i = 0; i < n; i++) {
                while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                    int height = heights[stack.pop()];
                    int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                    int width = i - leftBoundary - 1;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(i);
            }

            // pop whatever remains — these extend all the way to the end
            while (!stack.isEmpty()) {
                int height = heights[stack.pop()];
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int width = n - leftBoundary - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            return maxArea;
        }
    }
/**
 Let's build this with left-to-right, the same style we've used throughout — tracing a real example first, then connecting to code.

 ## Setup

 ```
 heights = [2,1,5,6,2,3]
 0 1 2 3 4 5
 ```

 ## The plan

 **Maintain a stack of INDICES, kept so that the heights they point to are in INCREASING order** (bottom to top of stack). When a new bar is SHORTER than what's on top, that's the signal to start popping and calculating areas.

 ## Trace, index by index

 **i=0 (height=2):** stack is empty, nothing to compare. Push 0.
 ```
 Stack: [0]  (heights: [2])
 ```

 **i=1 (height=1):** compare to stack top — `heights[0]=2`. Is `1 < 2`? YES — the bar at index 0 has found its "shorter bar to the right" (index 1). **Pop it and calculate its rectangle.**

 **When we pop index 0 (height 2):**
 - **Right boundary:** the current index (1) — this is where a shorter bar showed up.
 - **Left boundary:** whatever's now on top of the stack AFTER popping. Since the stack is now EMPTY after popping index 0, there's nothing shorter to the left — meaning this bar could extend all the way to the beginning (index -1, conceptually).
 - **Width = right boundary - left boundary - 1 = 1 - (-1) - 1 = 1**
 - **Area = height × width = 2 × 1 = 2**

 ```
 Max area so far: 2
 Stack after pop: []
 Now push index 1. Stack: [1]  (heights: [1])
 ```

 **i=2 (height=5):** compare to stack top — `heights[1]=1`. Is `5 < 1`? NO. Don't pop. Just push.
 ```
 Stack: [1, 2]  (heights: [1, 5])
 ```

 **i=3 (height=6):** compare to stack top — `heights[2]=5`. Is `6 < 5`? NO. Push.
 ```
 Stack: [1, 2, 3]  (heights: [1, 5, 6])
 ```

 **i=4 (height=2):** compare to stack top — `heights[3]=6`. Is `2 < 6`? YES — pop index 3.

 **Popping index 3 (height 6):**
 - **Right boundary:** current index (4).
 - **Left boundary:** stack top AFTER popping — that's index 2 (height 5).
 - **Width = 4 - 2 - 1 = 1**
 - **Area = 6 × 1 = 6**

 ```
 Max area so far: 6
 Stack: [1, 2]  (heights: [1,5])
 ```

 **Continue checking — is `heights[4]=2 < heights[2]=5`?** YES — pop index 2 too.

 **Popping index 2 (height 5):**
 - **Right boundary:** current index (4).
 - **Left boundary:** stack top after popping — that's index 1 (height 1).
 - **Width = 4 - 1 - 1 = 2**
 - **Area = 5 × 2 = 10**

 ```
 Max area so far: 10  ← this is the expected final answer!
 Stack: [1]  (heights: [1])
 ```

 **Continue checking — is `heights[4]=2 < heights[1]=1`?** NO. Stop popping. Push index 4.
 ```
 Stack: [1, 4]  (heights: [1, 2])
 ```

 **i=5 (height=3):** compare to stack top — `heights[4]=2`. Is `3<2`? NO. Push.
 ```
 Stack: [1, 4, 5]  (heights: [1,2,3])
 ```

 ## End of array — now pop everything remaining, since they extend all the way to the end

 ```
 Pop index 5 (height 3): right boundary = n(6, "past the end"). left boundary = stack top after pop = index 4.
 Width = 6 - 4 - 1 = 1. Area = 3×1=3. Max area stays 10.

 Pop index 4 (height 2): right boundary=6. left boundary = stack top after pop = index 1.
 Width = 6-1-1=4. Area=2×4=8. Max area stays 10.

 Pop index 1 (height 1): right boundary=6. left boundary = stack is now EMPTY after pop, so treat as -1.
 Width = 6-(-1)-1=6. Area=1×6=6. Max area stays 10.
 ```

 ## Final answer: 10 ✓

 ## The code

 ```java
 class Solution {
 public int largestRectangleArea(int[] heights) {
 Deque<Integer> stack = new ArrayDeque<>();
 int maxArea = 0;
 int n = heights.length;

 for (int i = 0; i < n; i++) {
 while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
 int height = heights[stack.pop()];
 int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
 int width = i - leftBoundary - 1;
 maxArea = Math.max(maxArea, height * width);
 }
 stack.push(i);
 }

 // pop whatever remains — these extend all the way to the end
 while (!stack.isEmpty()) {
 int height = heights[stack.pop()];
 int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
 int width = n - leftBoundary - 1;
 maxArea = Math.max(maxArea, height * width);
 }

 return maxArea;
 }
 }
 ```

 ## Complexity

 ```
 Time: O(n) — each index pushed once, popped at most once
 Space: O(n) — worst case, stack holds all indices (strictly increasing heights)
 ```

 Try tracing this code against the exact numbers we walked through by hand — does every pop and width calculation match? Want to try running it on LeetCode directly, or trace through one more example yourself first to confirm you've internalized the boundary logic?
 */

}
