package com.company;

public class TreeNode {
    private TreeNode parent = null;
    private TreeNode left = null;
    private TreeNode right = null;

    private Color color = Color.RED;

    private int val;

    public TreeNode(int val) {
        this.val = val;
    }

    public void swapValues(TreeNode node) {
        int temp = val;
        val = node.val;
        node.val = temp;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
