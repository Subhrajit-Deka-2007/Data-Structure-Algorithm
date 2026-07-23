package Trees;

public class Leetcode_1448
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
        public int goodNodes(TreeNode root)
        {
            return count ( root, Long.MIN_VALUE ,0);
        }

        public int count( TreeNode root , long X, int y )
        {
            if ( root == null ) return 0;

            int val = 0;
            int passValue = root.val;
            if( root.val >= X ) val = 1;
            else
            {
                val = 0;
                passValue = (int)X;
            /*
             when we get an invalid in between we will not pass the root value we will
             we will pass the value of the parent node
             */

            }


            return val + count(root.left,passValue,y)+count(root.right,passValue,y);

        }
    /*
    Time Complexity = O (N)
    Space Complexity = O (H)
    */
    }
/*

public void solve(TreeNode root, int currMax) {
        if(root==null) {
            return;
        }
        if(root.val>=currMax) {
            count++;
        }
        solve(root.left, Math.max(root.val, currMax));
        solve(root.right, Math.max(root.val, currMax));
    }
    public int goodNodes(TreeNode root) {
        if(root==null) {
            return count;
        }
        solve(root, root.val);
        return count;
    }

    */
}
