package Trees;

public class Leetcode_543 {

     class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {

        public int diameterOfBinaryTree(TreeNode root) {
            int[] max = new int[1];
            longestDiameter(root, max);
            return max[0];
        }

        public int longestDiameter(TreeNode root, int[] max) {
            if (root == null) return 0;
            int left = longestDiameter(root.left, max);
            int right = longestDiameter(root.right, max);
            max[0] = Math.max(max[0], left + right);

            return 1 + Math.max(left, right);
        }
    }
}
    /*
    Time Complexity = O (N)
    Space Complexity = O (H)

     How I know it I know the execution sequence of recursion
     . Now let's come to point on each call for that particular function these , represents that
     node and for each node i take it's maximum length from the left and maximum length from the right
     and then we add it and compare with the value stored in the 0th index and if it ,the value is  bigger then
     we update it , and these node was called by the node above it so
     what we are going to do before returning to the above node, for the current node I will
    compare it's maximum length from the left and maximum length from the right then after finding the
    maximum , I will add 1 to it adding 1 means it represents the current node . And these process repeats
    */
