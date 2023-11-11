package org.example.stream;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 关于流的学习
 * @date 2023/11/10 21:34
 */
public class Main {
    public static void main(String[] args) {
        String[] testString = {"Java", "C++", "Golang"};
        List<String> testStringList = Stream.of(testString)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println(testStringList);
        testStringList.forEach(System.out::println);

        System.out.println();

        Map<String, Integer> friends = new LinkedHashMap<>();
        friends.put("Alice", 30);
        friends.put("Bob", 28);
        friends.put("Cavin", 33);

        System.out.println();

        friends.entrySet().forEach(System.out::println);

        System.out.println();

        friends.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);

        System.out.println();

        friends.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(System.out::println);

        System.out.println();

        LinkedHashMap<String, Integer> friends2 = new LinkedHashMap<>();
        friends.put("Alice", 30);
        friends.put("Bob", 28);
        friends.put("Cavin", 33);

        friends2.entrySet().forEach(System.out::println);

    }
}
