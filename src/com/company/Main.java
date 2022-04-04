package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PersistentSet<RationalNumber> set = new PersistentSetImpl<>();
        fillSetFromFile(set);
        String cmd;
        Scanner scanner = new Scanner(System.in);
        System.out.println("I for insert, D for delete, P for print, E for exit");
        while (true) {
            cmd = scanner.nextLine();
            switch (cmd) {
                case "I" -> {
                    System.out.println("Insert rational number: ");
                    String strNum = scanner.nextLine();
                    set.insert(parseNum(strNum));
                    System.out.println("Inserted successfully:) Now latest version is " + set.versionsAmount());
                }
                case "D" -> {
                    System.out.println("Delete rational number: ");
                    String strNum = scanner.nextLine();
                    set.delete(parseNum(strNum));
                    System.out.println("Deleted successfully:) Now latest version is " + set.versionsAmount());
                }
                case "P" -> {
                    System.out.println("Print version (L for latest): ");
                    String version = scanner.nextLine();
                    if(version.equals("L")) {
                        set.printLatest();
                    } else {
                        int versionNum = Integer.parseInt(version);
                        if(versionNum > 0 && versionNum <= set.versionsAmount()) {
                            set.print(versionNum);
                        }
                    }
                }
                case "E" -> {return;}
            }
        }
    }

    private static void fillSetFromFile(PersistentSet<RationalNumber> set) {
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String str;
            while ((str = br.readLine()) != null) {
                String[] operation = str.split("\s+");
                RationalNumber rationalNumber = parseNum(operation[1]);
                if(operation[0].equals("I")) {
                    set.insert(rationalNumber);
                } else if(operation[0].equals("D")) {
                    set.delete(rationalNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RationalNumber parseNum(String str) {
        String[] elements = str.split("\s*/\s*");
        return new RationalNumber(
                Integer.parseInt(elements[0]),
                Integer.parseInt(elements[1])
        );
    }
}
