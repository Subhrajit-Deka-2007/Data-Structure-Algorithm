package Trees;

public class Leetcode_226
{

    public class TreeNode {
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
        public TreeNode invertTree(TreeNode root)
        {
        /*
         I will use recursion for each node I will do
         it's node.left = node.right and node.right node.left
         */
            return invert(root);
        }

        public TreeNode invert( TreeNode root )
        {
            if( root == null )return root;

            TreeNode left = invert(root.right);
            TreeNode right = invert(root.left);

            root.left = left;
            root.right = right;

            return root;
        }
    }
}
