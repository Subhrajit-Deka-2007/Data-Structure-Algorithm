package Trees;

import java.util.HashMap;
import java.util.Map;

public class Leetcode_105
{

    static class TreeNode {
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



    /*
    Pre-order + In-order  → uniquely determines the tree ✓
   Post-order + In-order → uniquely determines the tree ✓
   Pre-order + Post-order (NO in-order) → does NOT uniquely determine the tree ✗
   */
    class Solution {
        public TreeNode buildTree1(int[] preorder, int[] inorder) {
            return  helper_2(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
        }


        private static TreeNode helper_2(int[] preorder,int[]inorder,int pl,int ph,int il,int ih){
            if(pl>ph||il>ih) return null;
            TreeNode root = new TreeNode(preorder[pl]);
            int r =0;
            for(int i =0;i<inorder.length;i++){
                if(inorder[i]==preorder[pl]) {
                    r = i;
                    break;
                }
            }
            int ls = r-il;// Finding how many elemenets are on it's left
            root.left = helper_2(preorder,inorder,pl+1,pl+ls,il,r-1);
            root.right = helper_2(preorder,inorder,pl+ls+1,ph,r+1,ih);
            return root;

        }


/*
Original:  Time O(n²), Space O(n) [recursion stack]
Optimized: Time O(n), Space O(n) [recursion stack + HashMap]
*/


        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> inorderIndexMap = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderIndexMap.put(inorder[i], i);
            }
            return helper(preorder, inorderIndexMap, 0, preorder.length-1, 0, inorder.length-1);
        }

        private TreeNode helper(int[] preorder, Map<Integer,Integer> inorderIndexMap, int pl, int ph, int il, int ih) {
            if (pl > ph || il > ih) return null;

            TreeNode root = new TreeNode(preorder[pl]);
            int r = inorderIndexMap.get(preorder[pl]);
            int ls = r - il;

            root.left = helper(preorder, inorderIndexMap, pl+1, pl+ls, il, r-1);
            root.right = helper(preorder, inorderIndexMap, pl+ls+1, ph, r+1, ih);

            return root;
        }
    }
     /*
     ## Construct Binary Tree from Preorder and Inorder — Summary

## The core idea

**Pre-order's first element is ALWAYS the root of whatever subtree you're currently building. In-order tells you the LEFT/RIGHT split — everything before the root's position belongs to the left subtree, everything after belongs to the right subtree.**

## The algorithm, step by step

1. **Take `preorder[pl]`** (the first element of the current pre-order range) — this is the ROOT.
2. **Find that value's position (`r`) inside the current in-order range** — this tells you where the left/right split happens.
3. **Calculate `ls = r - il`** — the size of the left subtree.
4. **Recursively build the LEFT subtree:**
   - Pre-order range: `pl+1` to `pl+ls` (right after the root, exactly `ls` elements)
   - In-order range: `il` to `r-1` (everything before the root's position)
5. **Recursively build the RIGHT subtree:**
   - Pre-order range: `pl+ls+1` to `ph` (everything after the root and the left subtree)
   - In-order range: `r+1` to `ih` (everything after the root's position)
6. **Base case:** when `pl > ph` (or `il > ih`), the range is empty — return `null`.

## Why this correctly reconstructs the tree

**Pre-order tells you WHO the root is at each level. In-order tells you HOW to split the remaining elements into left and right subtrees. Combining both, at every recursive step, uniquely determines the exact structure — no ambiguity.**

## Complexity

```
As written (linear search for root's position in inorder each time): O(n²) time, O(n) space (recursion stack)
Optimized (using a HashMap to look up positions in O(1)): O(n) time, O(n) space
```

## The optimization worth knowing

**Precompute a `HashMap<Integer, Integer>` mapping each value to its index in `inorder`, BEFORE starting recursion — this replaces the inner linear search with an O(1) lookup, bringing the whole algorithm down to O(n).**

That leaves just **1 problem left in Trees: Serialize and Deserialize Binary Tree (297)** — the last Hard problem in this section. Want a hint to close it out?
















Let's trace this fully with a real example, connecting every line to the pre-order/in-order relationship you just confirmed.

## The example

```
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]
```

**This represents:**
```
        3
       / \
      9   20
         /  \
        15   7
```

## Initial call

```java
helper_2(preorder, inorder, pl=0, ph=4, il=0, ih=4)
```

**`pl,ph`** = the range WITHIN `preorder` we're currently working with. **`il,ih`** = the range WITHIN `inorder` we're currently working with.

## Line by line

```java
if(pl>ph||il>ih) return null;
```

**Base case: if the range is invalid (empty — meaning `pl` has gone PAST `ph`), there's nothing here — return null.** This happens when a subtree genuinely has NO nodes (like when a node has no left child).

```java
TreeNode root = new TreeNode(preorder[pl]);
```

**Recall: in pre-order, the FIRST element is ALWAYS the root.** So `preorder[pl]` (the first element of our CURRENT range) IS the root of whatever subtree we're building right now.

**Trace: `preorder[0] = 3`. Create `root = TreeNode(3)`.**

```java
int r = 0;
for (int i = 0; i < inorder.length; i++) {
    if (inorder[i] == preorder[pl]) {
        r = i;
        break;
    }
}
```

**We need to find WHERE this root's value sits WITHIN the in-order array — because that position tells us the LEFT/RIGHT split.** This loop SEARCHES `inorder` for the value `3`, and records its INDEX.

**Trace: search `inorder=[9,3,15,20,7]` for value `3`. Found at index 1. `r = 1`.**

## Why this index matters — the LEFT/RIGHT split

**Everything in `inorder` BEFORE index `r` belongs to the LEFT subtree. Everything AFTER index `r` belongs to the RIGHT subtree.**

```java
int ls = r - il;
```

**`ls` = "left subtree size" = how many elements are in the left subtree.** **Trace: `ls = r(1) - il(0) = 1`** — meaning the left subtree has exactly 1 node.

**Let's verify: `inorder[il..r-1] = inorder[0..0] = [9]`** — yes, just ONE element (`9`), matching `ls=1`.

```java
root.left = helper_2(preorder, inorder, pl+1, pl+ls, il, r-1);
```

**Recursively build the LEFT subtree.**

**For `preorder`:** the left subtree's elements come RIGHT AFTER the root, so the range is `pl+1` to `pl+ls` (exactly `ls` elements, starting right after the root).

**For `inorder`:** the left subtree's elements are everything BEFORE index `r`, so the range is `il` to `r-1`.

**Trace: `helper_2(preorder, inorder, pl+1=1, pl+ls=0+1=1, il=0, r-1=0)`**

```java
root.right = helper_2(preorder, inorder, pl+ls+1, ph, r+1, ih);
```

**Recursively build the RIGHT subtree.**

**For `preorder`:** the right subtree's elements come AFTER the root AND after the entire left subtree, so the range starts at `pl+ls+1` and goes to `ph`.

**For `inorder`:** the right subtree's elements are everything AFTER index `r`, so the range is `r+1` to `ih`.

**Trace: `helper_2(preorder, inorder, pl+ls+1=0+1+1=2, ph=4, r+1=2, ih=4)`**

## Let's trace the LEFT recursive call fully

```
helper_2(preorder, inorder, pl=1, ph=1, il=0, ih=0)

pl(1) > ph(1)? NO, valid range (single element).
root = TreeNode(preorder[1]) = TreeNode(9)

Find 9 in inorder: inorder[0]=9. r=0.
ls = r(0) - il(0) = 0

root.left = helper_2(pl+1=2, ph=pl+ls=1, il=0, r-1=-1)
   → pl(2) > ph(1)? YES → return null (no left child for node 9)

root.right = helper_2(pl+ls+1=2, ph=1, il=r+1=1, ih=0)
   → pl(2) > ph(1)? YES → return null (no right child for node 9)

Returns: TreeNode(9), no children
```

**Correctly built: node 9 is a leaf, matching our tree diagram.**

## Let's trace the RIGHT recursive call

```
helper_2(preorder, inorder, pl=2, ph=4, il=2, ih=4)

Valid range.
root = TreeNode(preorder[2]) = TreeNode(20)

Find 20 in inorder: inorder[2]=15,inorder[3]=20. Found at index 3. r=3.
ls = r(3) - il(2) = 1

root.left = helper_2(pl+1=3, ph=pl+ls=2+1=3, il=2, r-1=2)
   → builds subtree for node 15 (single element range)

root.right = helper_2(pl+ls+1=4, ph=4, il=r+1=4, ih=4)
   → builds subtree for node 7 (single element range)
```

**Correctly identifies 20 as the root of this subtree, with 15 as left child and 7 as right child — matching the diagram exactly.**

## The complete picture, in one sentence

**At every recursive call, you take the FIRST element of the current pre-order range as the root, FIND that value's position in the in-order range to determine the left/right split size, then recursively build the left subtree (using the corresponding shifted ranges in both arrays) and the right subtree (using the remaining ranges), until the ranges become empty (base case).**

## One efficiency note worth flagging

**The inner `for` loop (searching for the root's value in `inorder` every single time) makes this solution `O(n²)` in the worst case** — since for EVERY node, you're potentially scanning through the WHOLE `inorder` array again. **A common optimization: precompute a HashMap mapping value → index in `inorder`, ONCE, before starting the recursion — this would let you look up `r` in O(1) instead of O(n), bringing the overall complexity down to O(n).**

Does tracing through the left AND right recursive calls, seeing exactly how `pl/ph/il/ih` shift at each level, make the range-splitting logic click?
*/


}
