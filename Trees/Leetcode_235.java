package Trees;

public class Leetcode_235
{
    public class TreeNode
    {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
    class Solution{

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
        {
            if(root.val<p.val && root.val<q.val) return lowestCommonAncestor(root.right,p,q);// go right
            else if(root.val>p.val && root.val>q.val) return lowestCommonAncestor(root.left,p,q); // go left
            else return root;// handles case when p is in right and q is at left and vice versa'
        }
    }
}
