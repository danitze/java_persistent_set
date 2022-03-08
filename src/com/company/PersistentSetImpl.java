package com.company;

import java.util.ArrayList;
import java.util.List;

public class PersistentSetImpl implements PersistentSet {

    private final List<Tree> trees = new ArrayList<>();

    @Override
    public void insert(int val) {
        Tree lastTree = (trees.size() == 0) ? null : trees.get(trees.size() - 1);
        if (lastTree != null && lastTree.contains(val)) {
            return;
        }
        Tree newTree = (lastTree == null) ? new Tree() : lastTree.copyForInsert(val);
        newTree.insert(val);
        trees.add(newTree);
    }

    @Override
    public void delete(int val) {
        Tree lastTree = (trees.size() == 0) ? null : trees.get(trees.size() - 1);
        if (lastTree == null || !lastTree.contains(val)) {
            return;
        }
        Tree newTree = lastTree.copyForDelete(val);
        newTree.delete(val);
        trees.add(newTree);
    }

    @Override
    public int versionsAmount() {
        return trees.size();
    }

    @Override
    public boolean contains(int version, int val) {
        return trees.get(version - 1).contains(val);
    }

    @Override
    public void print(int version) {
        System.out.println("Set version " + version + ":");
        trees.get(version - 1).print();
        System.out.println("----------------------------");
    }

    @Override
    public int size(int version) {
        return trees.get(version - 1).size();
    }

}
