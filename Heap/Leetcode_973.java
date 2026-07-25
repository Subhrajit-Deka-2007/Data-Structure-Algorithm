package Heap;

import java.util.Collections;
import java.util.PriorityQueue;

public class Leetcode_973
{
}
class Triplet implements Comparable<Triplet> {
    int d;
    int x;
    int y;

    Triplet(int d, int x, int y) {
        this.d = d;
        this.y = y;
        this.x = x;
    }
    public int compareTo(Triplet t) {
        return this.d - t.d;// after this inside heap the sorting will be done on the basis of distance
    }
}

class Solution {
    public int[][] kClosest(int[][] points, int k) {

        PriorityQueue<Triplet> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0], y = points[i][1];
            int dis = (x * x - 0) + (y * y - 0);
            pq.add(new Triplet(dis, x, y));
            if (pq.size() > k) pq.remove();
        }
        int[][] ans = new int[k][2];
        for(int i =0; i<k;i++){
            Triplet t = pq.remove();
            ans[i][0] =t.x;
            ans[i][1] =t.y;
        }
        return ans;
        // Still there is some issue in the code
    }
    /**
     T.C =O(n log k (for insertion) + n log k(for deletion))

     S.C =O(K)
     */
}
