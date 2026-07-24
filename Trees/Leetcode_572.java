package Trees;

public class Leetcode_572
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
        // public boolean isSubtree(TreeNode root, TreeNode subRoot)
        // {
        // In my approach first I find the value whose value is same as the
        //  subtree node but in muy given tree if there are duplicate elements it fails for that case
        // If there is no duplicat emy appraoch is valid
        //     TreeNode req = find( root, subRoot );

        //     // After gettign the required Node .Now I will check it is similar to given subtree or not

        //     return check ( req, subRoot );

        // }

        // public TreeNode find( TreeNode root , TreeNode req )
        // {
        //     if ( root == null ) return null;


        //     TreeNode left = find( root.left , req);
        //     if( left!=null ) return left;

        //     TreeNode right = find( root.right ,req);
        //     if(right!=null ) return right;

        //    if( root.val == req.val )return root;


        //      return null;
        // }

        // public boolean check( TreeNode p, TreeNode q )
        // {
        //     if( p == null && q == null ) return true;
        //     else if( p!=null && q == null||p==null&&q!=null ) return false;
        //     else if ( p.val != q.val )return false;
        //     else return check(p.left,q.left)&&check(p.right,q.right);

        // }


        public boolean isSubtree1(TreeNode root, TreeNode subRoot) {
            // correct Appraoch : for each node i am checking it is subtree or not
            if (root == null) return false;
            if (check(root, subRoot)) return true;
            return isSubtree1(root.left, subRoot) || isSubtree1(root.right, subRoot);
    /*
    There are M nodes in the subtree and there are N nodes in the subtree
    for each node i am traversing M times SO
    Time Complexity = O (N*M)
    Space Complexity = O ( log (M))
    */
        }

        public boolean check(TreeNode p, TreeNode q)
        {
            if (p == null && q == null) return true;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            return check(p.left, q.left) && check(p.right, q.right);
        }




        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            String text = serialize(root);
            String pattern = serialize(subRoot);
            return kmpSearch(text, pattern);
        }

        private String serialize(TreeNode node) {
            if (node == null) return "#,";
            return "," + node.val + "," + serialize(node.left) + serialize(node.right);
        }

        // Build the LPS array for the pattern
        private int[] buildLPS(String pattern) {
            int m = pattern.length();
            int[] lps = new int[m];
            int len = 0; // length of the previous longest prefix-suffix match
            int i = 1;

            while (i < m) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else {
                    if (len != 0) {
                        len = lps[len - 1]; // fall back using the LPS array itself
                    } else {
                        lps[i] = 0;
                        i++;
                    }
                }
            }
            return lps;
        }

        // KMP search: does pattern appear anywhere inside text?
        private boolean kmpSearch(String text, String pattern) {
            int n = text.length();
            int m = pattern.length();
            if (m == 0) return true;

            int[] lps = buildLPS(pattern);
            int i = 0; // pointer into text
            int j = 0; // pointer into pattern

            while (i < n) {
                if (text.charAt(i) == pattern.charAt(j)) {
                    i++;
                    j++;
                    if (j == m) {
                        return true; // full match found
                    }
                } else {
                    if (j != 0) {
                        j = lps[j - 1]; // use the LPS array to skip, WITHOUT moving i backward
                    } else {
                        i++; // nothing to reuse, just advance text pointer
                    }
                }
            }
            return false; // exhausted text, no match found
        }
    }
}
