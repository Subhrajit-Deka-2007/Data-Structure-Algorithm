package Arrays;

public class Leetcode_853
{
    class Solution {
        public int carFleet(int target, int[] position, int[] speed) {
            int n = position.length;

            // Step 1: pair position and speed together
            double[][] cars = new double[n][2];
            for (int i = 0; i < n; i++) {
                cars[i][0] = position[i];
                cars[i][1] = speed[i];
            }

            // Step 2: sort by position, DESCENDING
            Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));

            Deque<Double> stack = new ArrayDeque<>();

            // Step 3: process cars from closest-to-target backward
            for (double[] car : cars) {
                double pos = car[0];
                double spd = car[1];
                double time = (target - pos) / spd;

                // if this car's time is greater than the fleet ahead's time,
                // it forms its OWN new fleet — push it
                if (stack.isEmpty() || time > stack.peek()) {
                    stack.push(time);
                }
                // otherwise, it catches up and merges — do nothing, don't push
            }

            return stack.size();
        }
/**
 Time: O(n log n) — dominated by the sort
 Space: O(n) — worst case, the stack could hold up to n entries (if every car is its own fleet)



 Let's build this from scratch — physical intuition first, then translate directly to code, no gaps.

 ---

 ## THE APPROACH

 ## Setup — combine position and speed, and sort

 ```
 target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 ```

 **Pair each car with its speed, then sort by position — but DESCENDING (closest to target first).** Why descending? Because a car can only be affected by (catch up to) a car **ahead of it** — so if we process from closest-to-target backward, by the time we look at any car, we already know everything about the cars ahead of it.

 ```
 Sorted by position, descending:
 Position 10, speed 2
 Position 8,  speed 4
 Position 5,  speed 1
 Position 3,  speed 3
 Position 0,  speed 1
 ```

 ## For each car, calculate: "how long would it take to reach the target, if driving completely alone?"

 ```
 time = (target - position) / speed
 ```

 ```
 Position 10, speed 2: (12-10)/2 = 1.0
 Position 8,  speed 4: (12-8)/4 = 1.0
 Position 5,  speed 1: (12-5)/1 = 7.0
 Position 3,  speed 3: (12-3)/3 = 3.0
 Position 0,  speed 1: (12-0)/1 = 12.0
 ```

 ## Now walk through the cars in this sorted order, using a stack

 **The stack holds the time-to-target of the current "fleet leaders"** — cars that are confirmed to be their own separate fleet (nothing behind them has caught up... yet).

 **Car 1 (position 10, time 1.0):** stack is empty, nothing to compare against. This car is automatically its own fleet leader. **Push 1.0.**
 ```
 Stack: [1.0]
 ```

 **Car 2 (position 8, time 1.0):** compare against the top of the stack (1.0, from the car ahead). **Is THIS car's time (1.0) <= the fleet ahead's time (1.0)?** YES — this means car 2 reaches the target in the SAME time or FASTER than the car ahead, so it catches up and merges into that fleet. **Do NOT push anything new — this car joins the existing fleet.**
 ```
 Stack: [1.0]  (unchanged)
 ```

 **Car 3 (position 5, time 7.0):** compare against top of stack (1.0). **Is 7.0 <= 1.0?** NO — this car is SLOWER to reach the target than the fleet ahead of it, meaning it will NEVER catch up. **This car forms its own NEW, separate fleet. Push 7.0.**
 ```
 Stack: [1.0, 7.0]
 ```

 **Car 4 (position 3, time 3.0):** compare against top of stack (7.0, the fleet from car 3). **Is 3.0 <= 7.0?** YES — car 4 reaches the target faster than car 3's fleet ahead of it, so it catches up and merges with car 3's fleet.
 ```
 Stack: [1.0, 7.0]  (unchanged)
 ```

 **Car 5 (position 0, time 12.0):** compare against top of stack (7.0). **Is 12.0 <= 7.0?** NO — this car is slower, forms its own new fleet. **Push 12.0.**
 ```
 Stack: [1.0, 7.0, 12.0]
 ```

 ## Final answer

 **The stack has 3 entries — meaning 3 separate fleets.** Matches the expected output!

 ---

 ## THE CODE

 ```java
 class Solution {
 public int carFleet(int target, int[] position, int[] speed) {
 int n = position.length;

 // Step 1: pair position and speed together
 double[][] cars = new double[n][2];
 for (int i = 0; i < n; i++) {
 cars[i][0] = position[i];
 cars[i][1] = speed[i];
 }

 // Step 2: sort by position, DESCENDING
 Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));

 Deque<Double> stack = new ArrayDeque<>();

 // Step 3: process cars from closest-to-target backward
 for (double[] car : cars) {
 double pos = car[0];
 double spd = car[1];
 double time = (target - pos) / spd;

 // if this car's time is greater than the fleet ahead's time,
 // it forms its OWN new fleet — push it
 if (stack.isEmpty() || time > stack.peek()) {
 stack.push(time);
 }
 // otherwise, it catches up and merges — do nothing, don't push
 }

 return stack.size();
 }
 }
 ```

 ## Connecting each code piece back to the trace

 **`Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]))`** — this is the "sort by position, descending" step.

 **`double time = (target - pos) / spd;`** — this is the "time to reach target alone" calculation we did for each car by hand.

 **`if (stack.isEmpty() || time > stack.peek())`** — this checks: "is my time GREATER than the fleet ahead's time?" (meaning I'm slower, so I DON'T merge, I form a new fleet) — matches exactly what we traced for car 3 (7.0 > nothing yet since stack was [1.0]... wait let's double check this matches our trace direction).

 **Double-checking the condition direction against our trace:** for car 3 (time 7.0), stack top was 1.0. We said "is 7.0 <= 1.0? NO, so form a new fleet, push." **In code terms: `time(7.0) > stack.peek()(1.0)` → TRUE → push.** ✓ Matches.

 For car 2 (time 1.0), stack top was 1.0. We said "is 1.0<=1.0? YES, so merge, don't push." **In code: `time(1.0) > stack.peek()(1.0)` → FALSE → don't push (goes to the implicit "merge" case).** ✓ Matches.

 ## Complexity

 ```
 Time: O(n log n) — dominated by the sort
 Space: O(n) — worst case, the stack could hold up to n entries (if every car is its own fleet)
 ```

 Try tracing through this code yourself with the exact example, confirming each step matches what we did by hand — then try running it on LeetCode directly.


 */
    }

}
