package com.company;

public class Tree {
    private TreeNode root = null;
    private int size = 0;

    public void insert(int val) {
        ++size;
        TreeNode node = new TreeNode(val);
        if (root == null) {
            root = node;
        } else {
            insertRecursive(root, node);
        }
        fixInsert(node);
    }

    public void delete(int val) {
        if (root == null) {
            return;
        }
        TreeNode node = findNodeWithVal(root, val);
        if (node == null) {
            return;
        }
        --size;
        if (node.getLeft() != null && node.getRight() != null) {
            TreeNode swappingNode = findLargest(node.getLeft());
            node.swapValues(swappingNode);
            replaceForDelete(node.getLeft(), val);
            node = swappingNode;
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

    private void insertRecursive(TreeNode root, TreeNode newNode) {
        if (newNode.getVal() == root.getVal()) {
            return;
        }
        if (newNode.getVal() < root.getVal()) {
            TreeNode left = root.getLeft();
            if (left == null) {
                root.setLeft(newNode);
                newNode.setParent(root);
            } else {
                insertRecursive(left, newNode);
            }
        } else {
            TreeNode right = root.getRight();
            if (right == null) {
                root.setRight(newNode);
                newNode.setParent(root);
            } else {
                insertRecursive(right, newNode);
            }
        }
    }

    private void rotateRight(TreeNode root) {
        TreeNode newRoot = root.getLeft();
        root.setLeft(newRoot.getRight());
        if(newRoot.getRight() != null) {
            newRoot.getRight().setParent(root);
        }
        newRoot.setRight(root);
        changeDescendant(root, newRoot);
        root.setParent(newRoot);
    }

    private void rotateLeft(TreeNode root) {
        TreeNode newRoot = root.getRight();
        root.setRight(newRoot.getLeft());
        if(newRoot.getLeft() != null) {
            newRoot.getLeft().setParent(root);
        }
        newRoot.setLeft(root);
        changeDescendant(root, newRoot);
        root.setParent(newRoot);
    }

    private void recolor(TreeNode root) {
        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();
        if (left != null && right != null) {
            left.setColor(Color.BLACK);
            right.setColor(Color.BLACK);
            root.setColor(Color.RED);
        }
    }

    private void changeDescendant(TreeNode oldDescendant, TreeNode newDescendant) {
        TreeNode parent = oldDescendant.getParent();
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

    private void fixInsert(TreeNode newNode) {
        if (newNode == root) {
            newNode.setColor(Color.BLACK);
            return;
        }
        TreeNode parent = newNode.getParent();
        TreeNode grandParent = parent.getParent();
        if (grandParent == null) {
            return;
        }
        TreeNode uncle = (grandParent.getLeft() == parent) ? grandParent.getRight() : grandParent.getLeft();
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

    private void printRecursive(TreeNode root) {
        if (root == null) {
            return;
        }
        printRecursive(root.getLeft());
        System.out.println(root.getVal());
        printRecursive(root.getRight());
    }

    private TreeNode findNodeWithVal(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.getVal() == val) {
            return root;
        }
        return (val < root.getVal())
                ? findNodeWithVal(root.getLeft(), val)
                : findNodeWithVal(root.getRight(), val);
    }

    private TreeNode findLargest(TreeNode root) {
        while (root.getRight() != null) {
            root = root.getRight();
        }
        return root;
    }

    private void delete(TreeNode node) {
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
        TreeNode parent = node.getParent();
        TreeNode descendant = (node.getLeft() != null) ? node.getLeft() : node.getRight();
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

    private void fixDelete(TreeNode node) {
        if (node.getColor() == Color.RED) {
            return;
        }
        if (node == root) {
            TreeNode left = node.getLeft();
            TreeNode right = node.getRight();
            if (left != null) {
                left.setColor(Color.BLACK);
            } else if (right != null) {
                right.setColor(Color.BLACK);
            }
            return;
        }
        TreeNode parent = node.getParent();
        TreeNode sibling = (node == parent.getLeft()) ? parent.getRight() : parent.getLeft();
        TreeNode outerNephew;
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
            TreeNode innerNephew = (outerNephew == sibling.getLeft()) ? sibling.getRight() : sibling.getLeft();
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

    public Tree copyForInsert(int insertionVal) {
        Tree newTree = new Tree();
        newTree.root = this.root;
        newTree.size = this.size;
        newTree.replaceForInsert(newTree.root, insertionVal);
        return newTree;
    }

    private void replaceForInsert(TreeNode node, int insertionVal) {
        if(node == null) {
            return;
        }
        copyNode(node);
        if(insertionVal < node.getVal()) {
            replaceForInsert(node.getLeft(), insertionVal);
        } else {
            replaceForInsert(node.getRight(), insertionVal);
        }
    }

    public Tree copyForDelete(int deleteVal) {
        Tree newTree = new Tree();
        newTree.root = this.root;
        newTree.size = this.size;
        newTree.replaceForDelete(newTree.root, deleteVal);
        return newTree;
    }

    public void replaceForDelete(TreeNode node, int deleteVal) {
        copyNode(node);
        if(node.getVal() == deleteVal) {
            return;
        }
        if(deleteVal < node.getVal()) {
            replaceForDelete(node.getLeft(), deleteVal);
        } else {
            replaceForDelete(node.getRight(), deleteVal);
        }
    }

    private void copyNode(TreeNode node) {
        TreeNode newNode = new TreeNode(node.getVal());
        if(node == root) {
            root = newNode;
        }
        TreeNode parent = node.getParent();
        TreeNode left = node.getLeft();
        TreeNode right = node.getRight();
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

    public boolean contains(int val) {
        return findElementRecursive(root, val);
    }

    private boolean findElementRecursive(TreeNode node, int val) {
        if(node == null) {
            return false;
        }
        if(node.getVal() == val) {
            return true;
        }
        return (val < node.getVal())
                ? findElementRecursive(node.getLeft(), val)
                : findElementRecursive(node.getRight(), val);
    }

}
