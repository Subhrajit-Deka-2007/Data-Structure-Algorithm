package Greedy;

public class Leetcode_55
{

    class Solution
    {
        public boolean canJump0(int[] nums)
        {
            return Jump0(nums,0);
        }
        public boolean Jump0( int [] nums, int st  )
        {
            if( st>= nums.length-1 )return true;
            for ( int i = 1 ; i<= nums[st] ; i++  )if(Jump0(nums,st+i)) return true;
            return false;


        }
/*
When analyzing recursive algorithms, a **recurrence relation** expresses the total runtime of a function on an input of size $k$ in terms of the runtime on smaller inputs.

Here is the step-by-step breakdown of how the recurrence relation for your `Jump0` code is constructed and solved.

---

### Step 1: Formulating the Relation

Let $T(k)$ represent the total operations required to execute `Jump0` when you are **$k$ steps away from the end of the array**.

Look at what happens inside the recursive call for a given index:

```java
public boolean Jump0(int[] nums, int st) {
    if (st >= nums.length - 1) return true; // Base case: O(1)
    for (int i = 1; i <= nums[st]; i++) {   // Try every jump size i
        if (Jump0(nums, st + i)) return true;
    }
    return false;
}

```

In the worst-case scenario (e.g., `nums[st] = k - 1`, where you can jump to any remaining index, and the target is at the very end):

1. Doing basic checks and loop control takes **$1$ unit of work**.
2. Making a jump of size $1$ leaves $k - 1$ elements remaining $\rightarrow T(k - 1)$ work.
3. Making a jump of size $2$ leaves $k - 2$ elements remaining $\rightarrow T(k - 2)$ work.
4. ...
5. Making a jump of size $k - 1$ leaves $1$ element remaining $\rightarrow T(1)$ work.

Adding these up gives the recurrence relation:

$$T(k) = 1 + T(k-1) + T(k-2) + T(k-3) + \dots + T(1)$$

---

### Step 2: Solving the Recurrence Relation

To find the closed-form expression for $T(k)$, evaluate $T(k)$ side-by-side with $T(k-1)$:

**Equation (1):**


$$T(k) = 1 + T(k-1) + T(k-2) + T(k-3) + \dots + T(1)$$

**Equation (2)** (substitute $k - 1$ in place of $k$):


$$T(k-1) = 1 + T(k-2) + T(k-3) + \dots + T(1)$$

Now, subtract **Equation (2)** from **Equation (1)**:

$$T(k) - T(k-1) = [1 + T(k-1) + T(k-2) + \dots + T(1)] - [1 + T(k-2) + \dots + T(1)]$$

Notice that all terms $T(k-2), T(k-3), \dots, T(1)$ and the constant $1$ cancel out:

$$T(k) - T(k-1) = T(k-1)$$

Add $T(k-1)$ to both sides:

$$T(k) = 2 \cdot T(k-1)$$

---

### Step 3: Unrolling the Pattern

This simplified equation ($T(k) = 2 \cdot T(k-1)$) means that **every step away from the end doubles the amount of required work**:

* $T(1) = O(1)$ (Base case)
* $T(2) = 2 \cdot T(1) = 2^1$
* $T(3) = 2 \cdot T(2) = 2^2$
* $T(4) = 2 \cdot T(3) = 2^3$
* $\dots$
* $T(k) = 2^{k-1}$

For the full array length $N$ (where $k = N$ steps away from the end):

$$T(N) = 2^{N-1} = O(2^N)$$

---

### Key Takeaway

The work doubles with each added element because the code recreates the exact same decision branches repeatedly without caching past results (overlapping subproblems), creating a decision tree with $2^N$ total leaves in the worst case.



Space Complexity: O(N) The space complexity is O(N) due to the call stack of the recursion. In the worst case, the function jumps 1 step at a time (0 -> 1 -> 2 -> ... -> N-1), resulting in a maximum recursion depth of $N$.
*/

        //Solving using top - down dp => memoization



        public boolean canJump1(int[] nums) {
            Integer[] memo = new Integer[nums.length]; // null = unvisited, 0 = false, 1 = true--> Three state needed for these question
            return Jump1(nums, 0, memo);
        }

        private boolean Jump1(int[] nums, int st, Integer[] memo) {
            if (st >= nums.length - 1) return true;
            if (memo[st] != null) return memo[st] == 1;

            for (int i = 1; i <= nums[st]; i++) {
                if (Jump1(nums, st + i, memo)) {
                    memo[st] = 1;
                    return true;
                }
            }
            memo[st] = 0;
            return false;

        }
    /*
    Let's strip away the code and look at this as a simple counting problem.

### 1. What Memoization Actually Does

Memoization gives your code a **notebook**:

* **Without memoization:** You re-calculate the same index over and over again, leading to exponential work $O(2^N)$.
* **With memoization:** You process each index **exactly once**. Once an index is calculated, the answer is saved in `memo[]`. Any future recursive call to that index checks `memo[]` and returns instantly in $O(1)$ time.

Since an array of size $N$ has $N$ indices, your code will only execute the main body of the function **$N$ times total**.

---

### 2. How Much Work Happens Inside Each Index?

When you stand at an index, you run a `for` loop to check your jump choices:

```java
for (int i = 1; i <= nums[st]; i++)

```

In the worst case (where every number in the array is large), that loop tries to check every remaining index in front of it:

* When standing at **Index 0**, the loop checks all remaining spots ahead of it ($N - 1$ steps).
* When standing at **Index 1**, the loop checks all remaining spots ahead of it ($N - 2$ steps).
* When standing at **Index 2**, the loop checks all remaining spots ahead of it ($N - 3$ steps).
* ...and so on, down to the last index.

---

### 3. Adding Up the Total Work

Suppose your array has **$N = 5$** elements:

* **Index 0:** loop runs **4 times**
* **Index 1:** loop runs **3 times**
* **Index 2:** loop runs **2 times**
* **Index 3:** loop runs **1 time**
* **Index 4:** loop runs **0 times**

**Total loop iterations = $4 + 3 + 2 + 1 + 0 = 10$ steps.**

---

### 4. Why 10 steps equals O(N^2)

Look at how the number of steps grows as $N$ gets bigger:


In Big-O notation, constant factors like  are dropped because we only care about the growth rate. Therefore,N^2 simplifies directly to O(N^2).
Space COmplexity = O(N+ N ( RECURSIVE SPACE ))

*/



// Now Let's do it using tabulation


        public boolean canJump4(int[] nums) {
            int n = nums.length;
            boolean[] dp = new boolean[n];

            // Base Case: The last index can always reach itself
            dp[n - 1] = true;

            // Work backwards from second-to-last element down to index 0
            for (int i = n - 2; i >= 0; i--) {
                int maxJump = Math.min(nums[i], n - 1 - i); // Avoid jumping out of bounds => n-1-i checks how much you are far aways from the ending point from the particular index

                for (int j = 1; j <= maxJump; j++) {
                    if (dp[i + j]) {
                        dp[i] = true;
                        break; // Stop checking once a valid jump path is found
                    }
                }
            }

            return dp[0];
        }
        /*
        Greedy Appraoch => Time Complexity = O(n) , Space Complexity = O(1), And here we are moving from left to right */
        public boolean canJump5(int[] nums) {
            int maxReach = 0;

            for (int i = 0; i < nums.length; i++) {
                // If the current index is beyond the maximum reach, we can't move forward
                if (i > maxReach) {
                    return false;
                }

                // Update maxReach to the furthest index reachable so far
                maxReach = Math.max(maxReach, i + nums[i]);

                // Optimization: Early exit if maxReach can already reach or pass the end
                if (maxReach >= nums.length - 1) {
                    return true;
                }
            }

            return maxReach >= nums.length - 1;
        }


        public boolean canJump6(int[] nums)
        {
            int lastPos = nums.length - 1;

            for (int i = nums.length - 2; i >= 0; i--) {
                // If we can reach lastPos from index i, shift lastPos to i
                if (i + nums[i] >= lastPos) {// that means we can reach the last index from that point or cross so definately valid so we changing our postion to i
                    lastPos = i;
                }
            }

            return lastPos == 0;
            //Time Complexity = O(n) , Space Complexity = O(1)
        }

    }



}
