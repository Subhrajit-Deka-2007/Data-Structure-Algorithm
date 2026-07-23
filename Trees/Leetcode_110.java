package Trees;

public class Leetcode_110 {
    public class TreeNode {
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
        public boolean isBalanced(TreeNode root) {
        /*
         Balanced Binary Tree
        For EVERY node in the tree, the HEIGHT of its left subtree and the
        HEIGHT of its right subtree differ by AT MOST 1.

        Appraoch 1
        */
            boolean[] flag = new boolean[1];
            flag[0] = true;
            isBalance(root, flag);
            return flag[0];


        /*
        Approach 2 :

        return isBalance2(root)!=-1;
        */
        }

        public int isBalance(TreeNode root, boolean[] flag) {
            if (root == null) return 0;

            int left = isBalance(root.left, flag);
            if (flag[0] == false) return 0;
            int right = isBalance(root.right, flag);
            if (flag[0] == false) return 0;

            // In previous code i was just doing flag[0] == false
            // but it was not optimize as if the left is not valid still  we were finding the
            // height of the right side node we were doing uncessary work so we just return
            // so if anyone is giving us false we will just return we will not
            // find the height for that particular node
            flag[0] = (Math.abs(left - right) == 0 || Math.abs(left - right) == 1) ? true : false;

            return 1 + Math.max(left, right);
        }

        /*
        Another Approach Without using flag array
        */
        public int isBalance2(TreeNode root) {
            if (root == null) return 0;

            int left = isBalance2(root.left);
            if (left == -1) return -1;
            int right = isBalance2(root.right);
            if (right == -1) return -1;

            return Math.abs(left - right) > 1 ? -1 : 1 + Math.max(left, right);
        }
    }
}
