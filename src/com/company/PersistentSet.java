package com.company;

public interface PersistentSet {

    void insert(int val);

    void delete(int val);

    int versionsAmount();

    boolean contains(int version, int val);

    void print(int version);

    int size(int version);

}
