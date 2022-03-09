package com.company;

public interface PersistentSet<T extends Comparable<T>> {

    void insert(T val);

    void delete(T val);

    int versionsAmount();

    boolean contains(int version, T val);

    void print(int version);

    int size(int version);

}
