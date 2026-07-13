package DATA_STRUCTURE_AND_ALGORITHM.Arrays;

public class Quick_Sort
{

        public void quickSort(int[] arr, int low, int high) {
            // Base case: if low >= high, this sub-array has 0 or 1
            // elements, which is already trivially sorted — stop recursing.
            if (low < high) {

                // Partition the array around a pivot.
                // Everything smaller than the pivot ends up to its left,
                // everything bigger ends up to its right.
                // pivotIndex tells us EXACTLY where the pivot landed —
                // its final, correct sorted position.
                int pivotIndex = partition(arr, low, high);

                // Recursively sort the left side (everything before the pivot)
                quickSort(arr, low, pivotIndex - 1);

                // Recursively sort the right side (everything after the pivot)
                quickSort(arr, pivotIndex + 1, high);
            }
        }

        private int partition(int[] arr, int low, int high) {
            // Choose the LAST element in this range as the pivot.
            int pivot = arr[high];

            // i tracks the boundary of "confirmed smaller than pivot" region.
            // Starts at low-1 because we haven't confirmed anything yet.
            int i = low - 1;

            // j scans through every element in this range (except the pivot itself).
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    // This element belongs on the "smaller" side.
                    // Expand the smaller-region boundary by one...
                    i++;
                    // ...and swap this element into that newly claimed spot.
                    swap(arr, i, j);
                }
            }

            // After the loop, everything from low to i is smaller than pivot.
            // Place the pivot right after that region — its final position.
            swap(arr, i + 1, high);

            // Return where the pivot ended up, so quickSort() knows
            // where to split for the left and right recursive calls.
            return i + 1;
        }

        private void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }
/**

    int[] arr = {8, 3, 5, 4, 7, 6, 1, 2};
    quickSort(arr, 0, arr.length - 1);




        ## Complexity — best/average case


    Time: O(n log n)
    Space: O(log n)  — recursion stack depth
```

 **Why:** when the pivot lands roughly in the **middle** of each range, the array splits into two
 * **roughly equal halves** each time. Just like we derived earlier: cost per level is O(n)
 * (partition touches every element in that range), and the number of levels is O(log n)
 * (since the range keeps halving) → **O(n) × O(log n) = O(n log n)**.



        ## Complexity — worst case


    Time: O(n²)
    Space: O(n)  — recursion stack depth now equals n


        ## Why worst case happens — tied directly to the code

    Look at this line:

    int pivot = arr[high]; // ALWAYS picks the LAST element

 If the input array is already sorted (ascending)**, and you always pick the last element as pivot,
 that pivot is **always the maximum** of whatever range you're currently partitioning.

 Trace it to see why this is catastrophic

        arr = {1, 2, 3, 4, 5}  // already sorted

 First call: `partition(arr, 0, 4)`, pivot = `arr[4] = 5

    Every other element (1,2,3,4) is smaller than 5.
    Loop: j=0,1,2,3 → all satisfy arr[j] < pivot → i increments every time, swaps happen but array stays effectively the same (swapping elements with themselves in this sorted case)
    Final swap places pivot (5) at index 4 — it was ALREADY there.
    Returns pivotIndex = 4.

        **Split:** `quickSort(arr, 0, 3)` — left side has **4 elements**. `quickSort(arr, 5, 4)` — right side is **empty** (low > high, does nothing).

        **This is the worst possible split** — instead of dividing into two halves (say, 2 and 2), it divided into "everything" (4 elements) and "nothing" (0 elements).

        ## Why this repeats at every level

    Call 1: range size 5 → splits into 4 and 0
    Call 2: range size 4 → splits into 3 and 0
    Call 3: range size 3 → splits into 2 and 0
    Call 4: range size 2 → splits into 1 and 0
    Call 5: range size 1 → base case, stop
 Instead of the array shrinking by HALF each level (which would take log n levels),
 it only shrinks by ONE element each level** — meaning you need **n levels** total, not log n.

## The total cost — summing it up

    Level 1 (partition on 5 elements): cost = 5
    Level 2 (partition on 4 elements): cost = 4
    Level 3 (partition on 3 elements): cost = 3
    Level 4 (partition on 2 elements): cost = 2
    Level 5 (partition on 1 element):  cost = 1

    Total = 5+4+3+2+1 = 15


    For a general array of size `n`, this sum is `n + (n-1) + (n-2) + ... + 1`, which is the classic arithmetic series that evaluates to:
        ```
    n(n+1)/2 = O(n²)


        ## Why space also becomes O(n) in the worst case

    Since each recursive call only ever shrinks the range by 1 (not by half), the recursion goes **n levels deep** before hitting the base case — meaning the call stack holds **n** stacked frames at its deepest point, instead of just `log n`. That's why worst-case space is O(n), not O(log n).

        ## The actual fix used in production Quick Sort implementations

`
    // Instead of always picking arr[high], pick a RANDOM index and swap it to the end first:
    int randomIndex = low + (int)(Math.random() * (high - low + 1));
    swap(arr, randomIndex, high);
    int pivot = arr[high]; // now proceed as normal
```

    By randomizing the pivot choice, it becomes statistically extremely unlikely that an adversarial or already-sorted input will consistently trigger the worst-case pattern at every single level — this is what makes Quick Sort reliably fast in practice, despite having a theoretical O(n²) worst case.

        ## Summary table

| | Best/Average | Worst |
        |---|---|---|
        | Time | O(n log n) | O(n²) |
        | Space | O(log n) | O(n) |
        | Trigger for worst case | — | Already-sorted (or reverse-sorted) input + always picking first/last element as pivot |
        | Fix | — | Random or median-of-three pivot selection |

    Want to trace through the **best-case** scenario the same way (a fresh unsorted array, showing the roughly-equal splits happening at each level), to see the O(n log n) pattern emerge as clearly as we just saw the O(n²) one?


}
 **/
