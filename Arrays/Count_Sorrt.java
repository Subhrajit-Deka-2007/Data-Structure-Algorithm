package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class Count_Sorrt
{

        public int[] countingSort(int[] nums) {
            int n = nums.length;

            // Step A: find the max value, so we know how many
            // "buckets" (0 to max) we need in our count array.
            int max = nums[0];
            for (int num : nums) {
                if (num > max) {
                    max = num;
                }
            }

            // count[v] will eventually tell us how many numbers
            // have value v — one slot per possible value, 0 to max.
            int[] count = new int[max + 1];

            // ---- STEP 1: Tally how many numbers have each value ----
            for (int i = 0; i < n; i++) {
                count[nums[i]]++;
            }
            // After this loop: count[v] = "how many numbers equal v"

            // ---- STEP 2: Convert tallies into cumulative positions ----
            // Each slot absorbs the running total of everything before it.
            // This turns "how many numbers equal EXACTLY v"
            // into "how many numbers are ≤ v" — which tells us
            // the correct ending position for that value's group.
            for (int i = 1; i <= max; i++) {
                count[i] += count[i - 1];
            }
            // After this loop: count[v] = "how many numbers are ≤ v"

            // output will hold the final sorted result.
            int[] output = new int[n];

            // ---- STEP 3: Place each number into its correct position ----
            // Walk BACKWARD through nums so that numbers with the
            // same value keep their original relative order (stability).
            for (int i = n - 1; i >= 0; i--) {
                int value = nums[i];

                // count[value] tells us the 1-indexed position this
                // number belongs at; subtract 1 for the 0-indexed slot.
                output[count[value] - 1] = value;

                // Decrement so the NEXT number with the same value
                // (found earlier in this backward walk) takes the
                // slot just before this one.
                count[value]--;
            }

            // output is now fully sorted — return it.
            return output;
        }
    }
/*
Time: O(n + k)   where k = max value in the array
Space: O(n + k)   (count array is O(k), output array is O(n))
 */
