package Trees;

public class Leetcode_124
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
        public int maxPathSum(TreeNode root)
        {
            int [] max = new int[1];
            max[0] = root.val;
            maxPath( root, max );
            return max[0];
        }

        public int maxPath( TreeNode root ,  int [] max )
        {
            if( root == null ) return 0;

            int left = maxPath( root.left, max);
            if( left < 0 ) left = 0;
            // I have reset the value when any of the side is returning minus as it will automatically decrease the whole value
            // so we are setting it to zero . Similarly we have done it for the right side
        /*
        How I have think about it
        Think about these case : These was the case which  was giving wrong answer .
        So , I decide to not include the result return by it

                1
               /  \
            -2      3
         */

            int right = maxPath( root.right, max );
            if( right < 0 ) right = 0;

            max [0] = Math.max( (root.val+left+right ), max[0]);
            return root.val + Math.max( left, right );
        }
    /*
    Time Complexity = O ( N )
    Where N is the total number of nodes in the tree
    Space Complexity : SPACE IS THE RECUSRIVE CALL STACK => O(H)
    WHERE H = O (n ) IF IT IS A SKEWED TREE
    AND H = O ( LOGN ) IF IT IS A BLANACED BINARY TREE


    The algorithm I have use is Depth First Search
    */
    }
}
