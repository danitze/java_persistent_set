package com.company;

public class TreeNode<T extends Comparable<T>> {
    private TreeNode<T> parent = null;
    private TreeNode<T> left = null;
    private TreeNode<T> right = null;

    private Color color = Color.RED;

    private T val;

    public TreeNode(T val) {
        this.val = val;
    }

    public void swapValues(TreeNode<T> node) {
        T temp = val;
        val = node.val;
        node.val = temp;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
