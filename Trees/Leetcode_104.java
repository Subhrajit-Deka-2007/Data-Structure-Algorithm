package Trees;

public class Leetcode_104
{
     class TreeNode
    {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

    class Solution {
        public int maxDepth(TreeNode root)
        {
            if ( root == null ) return 0;
            int max = 1+  Math.max( maxDepth(root.left),maxDepth(root.right));
            return max;

        /*
        Time Complexity = O (N)
        Space Complexity = O(h)
        Where h is the height of the tree for a blanced tree
        it is log N
        And Here we need to remember one thing that we are not
        traversign a  particular nodes more then once
        So that is why the time complexity is O (n)
        */
        }
    }
}
