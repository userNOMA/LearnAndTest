package org.example.idea;

import java.util.ArrayList;
import java.util.Arrays;

public class TestQuike {
    public static void main(String[] args) {
        System.out.println("qqq");
        System.out.println("TestQuike.main");
        System.out.println("args = " + Arrays.toString(args));
        String s = "sfsfsaf";
        System.out.println("s = " + s);

        int a = 100;
        ArrayList<Object> asaa = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            System.out.println("i = " + i);
            asaa.add(i);
        }
    }

}
