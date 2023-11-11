package org.example.lambda;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/10 21:43
 */
public class Main {
    public static void main(String[] args) {
        Function<Integer, String> f = x -> x+"AAA";
        System.out.println(f.apply(45));
    }
}
