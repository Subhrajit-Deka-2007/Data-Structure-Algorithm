package Trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
public class Leetcode_297
{
     class TreeNode {
      int val;
      TreeNode left;
     TreeNode right;
      TreeNode(int x) { val = x; }
 }

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root)
        {
            if (root == null) return "#,";
            return root.val + "," + serialize(root.left) + serialize(root.right);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
            return buildTree(queue);
        }

        private TreeNode buildTree(Queue<String> queue) {
            String val = queue.poll();
            if (val.equals("#")) return null;

            TreeNode node = new TreeNode(Integer.parseInt(val));
            node.left = buildTree(queue);
            node.right = buildTree(queue);
            return node;
        }


    }
/*
Serialize: O(n) time, O(n) space (the output string)
Deserialize: O(n) time, O(n) space (the queue + recursion stack)
*/





// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
}
