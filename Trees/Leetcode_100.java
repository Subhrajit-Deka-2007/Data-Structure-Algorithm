package Trees;

public class Leetcode_100
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
        public boolean isSameTree(TreeNode p, TreeNode q)
        {
        /*
        Find the inorder , preorder , post order traversal of both
        the trees and store the list in arraylist and then start traversing
        both the arraylist  and start comparing the elements of
        the array parallely
        */

            if( p == null && q == null ) return true;
            else if( (p != null && q == null) || (q!=null&&p==null) ) return false;
            else if( p.val!=q.val )return false;

            return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
         /*
         else  return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
         Both works as if any of the condition get's execute it will never reach these
         line as return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
         as after any if , else if case we are having return statement
         */

        }
    }
}

