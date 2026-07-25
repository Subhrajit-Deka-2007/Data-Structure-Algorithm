package Heap;

import java.util.Collections;
import java.util.PriorityQueue;

public class Leetcode_295
{
    class MedianFinder {
        /*
        I can solve it using ArrayList
        */
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer>minHeap = new PriorityQueue<>();
        public MedianFinder() {

        }

        public void addNum(int num) {
            if(maxHeap.size()==0)maxHeap.add(num);
            else {
                if(num<maxHeap.peek())maxHeap.add(num);
                else minHeap.add(num);
            }
            // balance the heap
            if(maxHeap.size()>minHeap.size()+1){// maximum differnce will be 2 only or we can write maxHeap.size()==minHeap.size()+2
                int top  = maxHeap.remove();
                minHeap.add(top);
            }
            if(minHeap.size()>maxHeap.size()+1){// maximum differnce will be 2 only or we can write maxHeap.size()==minHeap.size()+2
                int top  = minHeap.remove();
                maxHeap.add(top);
            }
        }

        public double findMedian() {
            if(maxHeap.size()==minHeap.size()) return( maxHeap.peek()+minHeap.peek())/2.0;
                // or we can also  write maxHeap.size()+minHeap.size() %2 ==0 then
            else if(maxHeap.size()>minHeap.size()) return maxHeap.peek();
            else return minHeap.peek();
        }
    }


/*
## Time Complexity

**`addNum(num)`:**
- Comparing against `maxHeap.peek()`: O(1)
- Adding to either heap: O(log k), where k = number of elements currently in that heap (roughly n/2 at any point, where n = total numbers inserted so far)
- Rebalancing (if triggered): one `remove()` + one `add()`, each O(log k)

Overall: **O(log n)** per call to `addNum`.

**`findMedian()`:**
- Just compares heap sizes and peeks at the top of one or both heaps — no insertion, no removal, no traversal.

Overall: **O(1)** per call to `findMedian`.

## Space Complexity

Both heaps together store every number that's ever been inserted — nothing gets discarded, just redistributed between the two heaps.

Overall: **O(n)**, where n = total numbers inserted so far.

## Summary table

| Operation | Time | Space |
|---|---|---|
| `addNum` | O(log n) | O(n) (cumulative, across all calls) |
| `findMedian` | O(1) | O(1) extra (no new storage) |

This is why the two-heap approach is the standard optimal solution for this problem — insertion is logarithmic (much better than re-sorting the whole array every time, which would be O(n log n) per insert), and fetching the median is instant.
*/

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}
