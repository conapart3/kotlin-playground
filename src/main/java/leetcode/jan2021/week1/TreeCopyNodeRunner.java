package leetcode.jan2021.week1;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class TreeCopyNodeRunner {
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        int val = target.val;
        return searchNodeForTarget(cloned, val);
    }

    private TreeNode searchNodeForTarget(TreeNode cloned, int val) {
        if (cloned.val == val) {
            return cloned;
        }
        if (cloned.left != null) {
            TreeNode treeNode = searchNodeForTarget(cloned.left, val);
            if(treeNode != null) return treeNode;
        }
        if (cloned.right != null) {
            TreeNode treeNode = searchNodeForTarget(cloned.right, val);
            if(treeNode != null) return treeNode;
        }
        return null;
    }

}
