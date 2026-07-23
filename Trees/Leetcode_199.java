package Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_199
{



      class TreeNode {
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
        public List<Integer> rightSideView(TreeNode root)
        {

            List<Integer> ans = new ArrayList<>();
            if( root == null ) return ans;
            ans.add(2);
            rightSide(root,ans,0);
            return ans;
        }
        public void rightSide( TreeNode root , List<Integer> ans, int idx )
        {
        /*
        Approach 1 : DFS Done BY me
        */
            if( root == null ) return;
            if(ans.size()>=idx+1) ans.set(idx,root.val);// Thought by me
            else ans.add(root.val);
            rightSide(root.left,ans,idx+1);
            rightSide(root.right,ans,idx+1);

        }


        /* Approach 2 : Using BFS
         Time Complexity : O (N)
        Space Complexity : O (K) where k is the size of queue
        */
        public List<Integer> rightSideView2(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            if (root == null) return ans;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                // Get the number of nodes at the CURRENT level
                int levelSize = queue.size();

                // Process all nodes at this level
                for (int i = 0; i < levelSize; i++) {
                    TreeNode currentNode = queue.poll();

                    // If this is the last node in the loop, it's the rightmost node!
                    if (i == levelSize - 1) {
                        ans.add(currentNode.val);
                    }

                    // Add the children for the NEXT level
                    if (currentNode.left != null) queue.add(currentNode.left);
                    if (currentNode.right != null) queue.add(currentNode.right);
                }
            }

            return ans;
        }
    }
}
