package Arrays;

public class Implementing_Max_Heap {
}
 class Heapify {
    // maintains max-heap property for subtree rooted at index i
    public static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            heapify(arr, n, largest); // recursively fix the affected subtree
        }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
            // heapify reduced heap // we are trying to sort these in ascending order
            // we are putting the large value from the root and swap with that last index and then again used
            // heap algorithm on that [0 , n-2] to get the new root and again repeat the process
            // and in the end we will have a sorted array so basically we are sorting the array
            // Time Complexity = log(n-1)+log(n-2)+----+1 = n log n



            /*
            Yes — exactly. What you coded (heapify + build-max-heap loop + the extraction loop) is the
            complete Heap Sort algorithm.
            Let's confirm the full picture end to end, tying together everything you've built.
             */
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        heapSort(arr);
        for (int x : arr) System.out.print(x + " ");
    }
}