package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Leetcode_102
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
        public List<List<Integer>> levelOrder(TreeNode root)
        {
        /*
        My confidence:  I can solve it using Both dfs and bfs
        First let's solve it using DFS
        */
            List<List<Integer>> ans = new ArrayList<>();

            dfs(root,0,ans);
            return ans;
        }
        public void dfs( TreeNode root , int idx , List<List<Integer>> ans )
        {
            if( root == null )return;

            if(ans.size()==idx)
            {
                List<Integer> ls = new ArrayList<>();
                ans.add(ls);

            }
            ans.get(idx).add(root.val);

            dfs( root.left,idx+1,ans);
            dfs( root.right,idx+1,ans);

        }

        public List<List<Integer>> bfs( TreeNode root , List<List<Integer>> ans )
        {
            Queue<TreeNode> q = new ArrayDeque<>();
            if(root!=null)q.add(root);

            while( !q.isEmpty() )
            {
                List<Integer> ls = new ArrayList<>();
                TreeNode p = null;
                int curr = q.size();// Necessary I have thought it by myself

                for ( int i = 0 ; i<curr ; i++ )
                {
                    p = q.poll();
                    ls.add(p.val);

                    if(p.left!=null)q.add(p.left);
                    if(p.right!=null)q.add(p.right);
                }
                ans.add(ls);
            }
            return ans;
        }
    }
}
