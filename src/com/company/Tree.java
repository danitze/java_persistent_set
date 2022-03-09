package com.company;

public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root = null;
    private int size = 0;

    public void insert(T val) {
        ++size;
        TreeNode<T> node = new TreeNode<>(val);
        if (root == null) {
            root = node;
        } else {
            insertRecursive(root, node);
        }
        fixInsert(node);
    }

    public void delete(T val) {
        if (root == null) {
            return;
        }
        TreeNode<T> node = findNodeWithVal(root, val);
        if (node == null) {
            return;
        }
        --size;
        if (node.getLeft() != null && node.getRight() != null) {
            TreeNode<T> swappingNode = findLargest(node.getLeft());
            node.swapValues(swappingNode);
            replaceForDelete(node.getLeft(), val);
            node = findLargest(node.getLeft());
        }
        fixDelete(node);
        delete(node);
    }

    public void print() {
        printRecursive(root);
    }

    public int size() {
        return size;
    }

    public boolean contains(T val) {
        return findElementRecursive(root, val);
    }

    public Tree<T> copyForInsert(T insertionVal) {
        Tree<T> newTree = new Tree<>();
        newTree.root = this.root;
        newTree.size = this.size;
        newTree.replaceForInsert(newTree.root, insertionVal);
        return newTree;
    }

    public Tree<T> copyForDelete(T deleteVal) {
        Tree<T> newTree = new Tree<>();
        newTree.root = this.root;
        newTree.size = this.size;
        newTree.replaceForDelete(newTree.root, deleteVal);
        return newTree;
    }

    private void insertRecursive(TreeNode<T> root, TreeNode<T> newNode) {
        if (newNode.getVal().equals(root.getVal())) {
            return;
        }
        if (newNode.getVal().compareTo(root.getVal()) < 0)  {
            TreeNode<T> left = root.getLeft();
            if (left == null) {
                root.setLeft(newNode);
                newNode.setParent(root);
            } else {
                insertRecursive(left, newNode);
            }
        } else {
            TreeNode<T> right = root.getRight();
            if (right == null) {
                root.setRight(newNode);
                newNode.setParent(root);
            } else {
                insertRecursive(right, newNode);
            }
        }
    }

    private void rotateRight(TreeNode<T> root) {
        TreeNode<T> newRoot = root.getLeft();
        root.setLeft(newRoot.getRight());
        if(newRoot.getRight() != null) {
            newRoot.getRight().setParent(root);
        }
        newRoot.setRight(root);
        changeDescendant(root, newRoot);
        root.setParent(newRoot);
    }

    private void rotateLeft(TreeNode<T> root) {
        TreeNode<T> newRoot = root.getRight();
        root.setRight(newRoot.getLeft());
        if(newRoot.getLeft() != null) {
            newRoot.getLeft().setParent(root);
        }
        newRoot.setLeft(root);
        changeDescendant(root, newRoot);
        root.setParent(newRoot);
    }

    private void recolor(TreeNode<T> root) {
        TreeNode<T> left = root.getLeft();
        TreeNode<T> right = root.getRight();
        if (left != null && right != null) {
            left.setColor(Color.BLACK);
            right.setColor(Color.BLACK);
            root.setColor(Color.RED);
        }
    }

    private void changeDescendant(TreeNode<T> oldDescendant, TreeNode<T> newDescendant) {
        TreeNode<T> parent = oldDescendant.getParent();
        newDescendant.setParent(parent);
        if (parent == null) {
            root = newDescendant;
            return;
        }
        if (parent.getLeft() == oldDescendant) {
            parent.setLeft(newDescendant);
        } else {
            parent.setRight(newDescendant);
        }
    }

    private void fixInsert(TreeNode<T> newNode) {
        if (newNode == root) {
            newNode.setColor(Color.BLACK);
            return;
        }
        TreeNode<T> parent = newNode.getParent();
        TreeNode<T> grandParent = parent.getParent();
        if (grandParent == null) {
            return;
        }
        TreeNode<T> uncle = (grandParent.getLeft() == parent) ? grandParent.getRight() : grandParent.getLeft();
        if (uncle != null && parent.getColor() == Color.RED && uncle.getColor() == Color.RED) {
            recolor(grandParent);
            fixInsert(grandParent);
        } else if (parent.getColor() == Color.RED && (uncle == null || uncle.getColor() == Color.BLACK)) {
            if (parent == grandParent.getRight() && newNode == parent.getLeft()) {
                rotateRight(parent);
                fixInsert(newNode);
            } else if (parent == grandParent.getLeft() && newNode == parent.getRight()) {
                rotateLeft(parent);
                fixInsert(newNode);
            } else if (parent == grandParent.getRight() && newNode == parent.getRight()) {
                rotateLeft(grandParent);
                parent.setColor(Color.BLACK);
                grandParent.setColor(Color.RED);
            } else {
                rotateRight(grandParent);
                parent.setColor(Color.BLACK);
                grandParent.setColor(Color.RED);
            }
        }
    }

    private void printRecursive(TreeNode<T> root) {
        if (root == null) {
            return;
        }
        printRecursive(root.getLeft());
        System.out.println(root.getVal());
        printRecursive(root.getRight());
    }

    private TreeNode<T> findNodeWithVal(TreeNode<T> root, T val) {
        if (root == null) {
            return null;
        }
        if (root.getVal().equals(val)) {
            return root;
        }
        return (val.compareTo(root.getVal()) < 0)
                ? findNodeWithVal(root.getLeft(), val)
                : findNodeWithVal(root.getRight(), val);
    }

    private TreeNode<T> findLargest(TreeNode<T> root) {
        while (root.getRight() != null) {
            root = root.getRight();
        }
        return root;
    }

    private void delete(TreeNode<T> node) {
        if (node == root) {
            if (node.getLeft() != null) {
                root = node.getLeft();
                node.setLeft(null);
            } else if (node.getRight() != null) {
                root = node.getRight();
                node.setRight(null);
            } else {
                root = null;
            }
            return;
        }
        TreeNode<T> parent = node.getParent();
        TreeNode<T> descendant = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        node.setParent(null);
        node.setLeft(null);
        node.setRight(null);
        if(node == parent.getLeft()) {
            parent.setLeft(descendant);
        } else {
            parent.setRight(descendant);
        }
        if(descendant != null) {
            descendant.setParent(parent);
        }
    }

    private void fixDelete(TreeNode<T> node) {
        if (node.getColor() == Color.RED) {
            return;
        }
        if (node == root) {
            TreeNode<T> left = node.getLeft();
            TreeNode<T> right = node.getRight();
            if (left != null) {
                left.setColor(Color.BLACK);
            } else if (right != null) {
                right.setColor(Color.BLACK);
            }
            return;
        }
        TreeNode<T> parent = node.getParent();
        TreeNode<T> sibling = (node == parent.getLeft()) ? parent.getRight() : parent.getLeft();
        TreeNode<T> outerNephew;
        if (sibling == null) {
            outerNephew = null;
        } else {
            outerNephew = (node == parent.getLeft()) ? sibling.getRight() : sibling.getLeft();
        }
        if (sibling != null && sibling.getColor() == Color.RED) {
            parent.setColor(Color.RED);
            sibling.setColor(Color.BLACK);
            if (node == parent.getLeft()) {
                rotateLeft(parent);
            } else {
                rotateRight(parent);
            }
            fixDelete(node);
        } else if (
                sibling != null && sibling.getColor() == Color.BLACK
                        && (sibling.getLeft() == null || sibling.getLeft().getColor() == Color.BLACK)
                        && (sibling.getRight() == null || sibling.getRight().getColor() == Color.BLACK)
        ) {
            if (parent.getColor() == Color.RED) {
                parent.setColor(Color.BLACK);
                sibling.setColor(Color.RED);
            } else {
                sibling.setColor(Color.RED);
                fixDelete(parent);
            }
        } else if (
                sibling != null && sibling.getColor() == Color.BLACK
                        && (
                        (sibling.getLeft() != null && sibling.getLeft().getColor() == Color.RED)
                                || (sibling.getRight() != null && sibling.getRight().getColor() == Color.RED)
                )
        ) {
            TreeNode<T> innerNephew = (outerNephew == sibling.getLeft()) ? sibling.getRight() : sibling.getLeft();
            if (outerNephew == null || outerNephew.getColor() == Color.BLACK) {
                innerNephew.setColor(Color.BLACK);
                sibling.setColor(Color.RED);
                if (node == parent.getLeft()) {
                    rotateRight(sibling);
                } else {
                    rotateLeft(sibling);
                }
                innerNephew.setColor(parent.getColor());
                parent.setColor(Color.BLACK);
                sibling.setColor(Color.BLACK);
            } else {
                sibling.setColor(parent.getColor());
                parent.setColor(Color.BLACK);
                outerNephew.setColor(Color.BLACK);
            }
            if(node == parent.getLeft()) {
                rotateLeft(parent);
            } else {
                rotateRight(parent);
            }
        }
    }

    private void replaceForInsert(TreeNode<T> node, T insertionVal) {
        if(node == null) {
            return;
        }
        copyNode(node);
        if(insertionVal.compareTo(node.getVal()) < 0) {
            replaceForInsert(node.getLeft(), insertionVal);
        } else {
            replaceForInsert(node.getRight(), insertionVal);
        }
    }

    public void replaceForDelete(TreeNode<T> node, T deleteVal) {
        copyNode(node);
        if(node.getVal().equals(deleteVal)) {
            return;
        }
        if(deleteVal.compareTo(node.getVal()) < 0) {
            replaceForDelete(node.getLeft(), deleteVal);
        } else {
            replaceForDelete(node.getRight(), deleteVal);
        }
    }

    private void copyNode(TreeNode<T> node) {
        TreeNode<T> newNode = new TreeNode<>(node.getVal());
        if(node == root) {
            root = newNode;
        }
        TreeNode<T> parent = node.getParent();
        TreeNode<T> left = node.getLeft();
        TreeNode<T> right = node.getRight();
        newNode.setParent(parent);
        if(parent != null) {
            if(node == parent.getLeft()) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
        }
        newNode.setLeft(left);
        newNode.setRight(right);
        if(left != null) {
            left.setParent(newNode);
        }
        if(right != null) {
            right.setParent(newNode);
        }
    }

    private boolean findElementRecursive(TreeNode<T> node, T val) {
        if(node == null) {
            return false;
        }
        if(node.getVal().equals(val)) {
            return true;
        }
        return (val.compareTo(node.getVal()) < 0)
                ? findElementRecursive(node.getLeft(), val)
                : findElementRecursive(node.getRight(), val);
    }

}
