package Greedy;

public class Leetcode_134
{

    class Solution {
        public int canCompleteCircuit0(int[] gas, int[] cost) {
            int n = gas.length;
            for (int start = 0; start < n; start++) {
                if (canComplete0(gas, cost, start)) return start;
            }
            return -1;
        }

        private boolean canComplete0(int[] gas, int[] cost, int start) {
            int tank = 0;
            for (int i = 0; i < gas.length; i++) {
                int station = (start + i) % gas.length;
                tank += gas[station] - cost[station];
                if (tank < 0) return false;
            }
            return true;
        }
    /*
    Complexity: outer loop tries n starting points, inner loop simulates n stations each time
     O(n²) time, O(1) space.
    */



        public int canCompleteCircuit(int[] gas, int[] cost) {
            int totalTank = 0, currTank = 0, start = 0;

            for (int i = 0; i < gas.length; i++) {
                int diff = gas[i] - cost[i];
                totalTank += diff;
                currTank += diff;

                if (currTank < 0) {          // failed starting from `start` (or anywhere since)
                    start = i + 1;            // jump past the failure point
                    currTank = 0;             // reset the local attempt
                }
            }

            return totalTank >= 0 ? start : -1;
        }
    /*
    Time Complexity = O(N)
    Space Complexity = O(1)
    */

    }


}
