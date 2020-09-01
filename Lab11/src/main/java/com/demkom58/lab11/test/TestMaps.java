package com.demkom58.lab11.test;

import java.util.*;
import java.util.function.Consumer;

public class TestMaps {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println("--------- Test 1 ---------");
        Map<String, String> map = new HashMap<>();

        map.put("file", "файл");
        map.put("block", "блок");
        map.put("dictionary", "словник");
        map.putIfAbsent("file", "фаайл");
        map.replace("block", "блок1", "блок2");

        System.out.println("Does it contain 'file' key? " + map.containsKey("file"));

        map.keySet().forEach(System.out::println);
        System.out.println();
        map.values().forEach(System.out::println);
        System.out.println();
        map.entrySet().forEach(System.out::println);
    }

    private static void test2() {
        System.out.println("--------- Test 2 ---------");
        final Consumer<Map<String, String>> tester = (map) -> {
            map.put("file", "файл");
            map.put("block", "блок");
            map.put("dictionary", "словник");

            System.out.println("> " + map.getClass().getSimpleName() + ":");
            map.keySet().forEach(System.out::println);
        };
        tester.accept(new HashMap<>());
        tester.accept(new LinkedHashMap<>());
        tester.accept(new TreeMap<>());
    }

    private static void test3() {
        System.out.println("--------- Test 3 ---------");
        Properties props = System.getProperties();
        props.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}
