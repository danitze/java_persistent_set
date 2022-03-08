package com.company;

public class Main {

    public static void main(String[] args) {
        PersistentSet set = new PersistentSetImpl();
        set.insert(5);
        set.insert(7);
        set.insert(6);
        set.print(3);
        set.delete(6);
        set.print(4);
    }
}
