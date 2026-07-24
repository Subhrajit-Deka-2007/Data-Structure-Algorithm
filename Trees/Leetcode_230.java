package Trees;

import java.util.ArrayList;

public class Leetcode_230
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
        public int kthSmallest(TreeNode root, int k) {
            //     ArrayList<Integer>ls = new ArrayList<>();
            //    inorder(root,ls);
            //     return ls.get(k-1);
            //     rev_inorder(root,ls);
            //     return ls.get(ls.size()-k);
            int[] x= new int[1];
            x [0]=k;
            TreeNode [] ans = new TreeNode[1];
            return INORDER(root,x,ans).val;

        }
        public void inorder(TreeNode root, ArrayList<Integer> ls){
            if( root == null) return ;
            if( root.left == null&& root.right == null) {
                ls.add(root.val);
                return ;
            }
            inorder(root.left,ls);
            ls.add(root.val);
            inorder(root.right,ls);
        }
        public void rev_inorder(TreeNode root, ArrayList<Integer>ls){
            if( root == null) return ;
            if( root.left == null&& root.right == null) {
                ls.add(root.val);
                return ;
            }
            rev_inorder(root.right,ls);
            ls.add(root.val);
            rev_inorder(root.left,ls);
        }
        // follow up can we do it without using an extra array
        public TreeNode INORDER(TreeNode root,int[] k,TreeNode [] x){
            if(root == null) return null;
            // we are travelling the bst okay inorder and inorder the traversal is in ascending order so when we get the root just do        k--
            // make k pass by reference
            INORDER(root.left,k,x);
            if(--k[0]==0) return x[0] = root; // BECASUE AT THE TIME WE HIT A root at the time only we decrease and check
        /*
        It is based on the concept for bst inorder traversal gives sorted lsit
        */
            INORDER(root.right,k,x);
            return x[0];
            /**
             T.C =O(N)worst case: FOR THE LAST SMALLEST ELEMENT THAT IS NTH SMALLEST ELEMENT
             S.C =O(H) where h = level of tree and it is equal to logn when the bst is balance and n when tree is degenrate or skewed
             and it is recursive space  */
        }




    }
}
