package com.demkom58.lab10.test;

import java.util.*;

public class TestCollections {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    private static void test1() {
        System.out.println("------- Test 1 -------");
        final Random random = new Random();

        Collection<Integer> vec = new Vector<>();
        for (int i = 0; i < 15; i++)
            vec.add(random.nextInt(10));
        print(vec);

        Collection<Integer> stack = new Stack<>();
        stack.addAll(vec);
        print(stack);

        print(new ArrayList<>(vec));
        print(new LinkedList<>(vec));
        print(new ArrayDeque<>(vec));
        print(new PriorityQueue<>(vec));
        print(new HashSet<>(vec));
        print(new LinkedHashSet<>(vec));
        print(new TreeSet<>(vec));
    }

    private static void test2() {
        System.out.println("------- Test 2 -------");
        final Random random = new Random();

        final ArrayList<Integer> c1 = new ArrayList<>();
        final Collection<Integer> c2 = new LinkedHashSet<>();
        final Collection<Integer> c3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            c1.add(random.nextInt(10));
            c2.add(random.nextInt(10));
        }

        c3.addAll(c1);
        c3.removeAll(c2);
        System.out.println(c1 + "removeAll" + c2 + " = " + c3);

        c3.clear();
        c3.addAll(c2);
        c3.removeAll(c1);
        System.out.println(c2 + "removeAll" + c1 + " = " + c3);

        c3.clear();
        c3.addAll(c1);
        c3.removeAll(c2);
        System.out.println(c1 + "removeAll" + c2 + " = " + c3);

        c3.clear();
        c3.addAll(c1);
        c3.removeAll(c1);
        System.out.println(c2 + "removeAll" + c1 + " = " + c3);
    }

    private static void test3() {
        System.out.println("------- Test 3 -------");
        final Random rnd = new Random();

        final Collection<Integer> c1 = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            c1.add(rnd.nextInt(10));

        Collection<Integer> c2 = new LinkedHashSet<>(c1);
        System.out.println(c1 + "containsAll" + c2 + " = " + c1.containsAll(c2));

        Collection<Integer> c3 = new TreeSet<>(c1);
        System.out.println(c1 + "containsAll" + c3 + " = " + c1.containsAll(c3));
    }

    private static void test4() {
        System.out.println("------- Test 4 -------");

        Random rnd = new Random();
        List<Integer> c1 = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            c1.add(rnd.nextInt(10));
        c1.sort(Comparator.comparingInt(a -> a));

        System.out.println(c1);
    }

    private static void test5() {
        System.out.println("------- Test 5 -------");
        System.out.println("Universal");

        Collection<Integer> coll = new ArrayDeque<>();
        Collections.addAll(coll, 1, 3, 5, 3, 4, 2, 14);
        Collections.addAll(coll, new Integer[]{ 3, 7, 12});
        System.out.println(coll);
        System.out.println(Collections.frequency(coll, 3));
        System.out.println(Collections.max(coll));
        System.out.println(Collections.min(coll));

        System.out.println("List only");

        List<Integer> list = new ArrayList<>(coll);
        Collections.swap(list, 2, 6);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        System.out.println(Collections.binarySearch(list, 3));
        Collections.sort(list, (a, b) -> b - a);
        System.out.println(list);
        System.out.println(Collections.binarySearch(list, 3, (a, b) -> b - a));
        Collections.reverse(list);
        System.out.println(list);
        Collections.rotate(list, 2);
        System.out.println(list);
        Collections.shuffle(list);
        System.out.println(list);
        Collections.replaceAll(list, 3, 8);
        System.out.println(list);
        Collections.fill(list, 0);
        System.out.println(list);
    }

    private static void print(Collection<?> objects) {
        System.out.printf("\nCollection %s: ", objects.getClass().getSimpleName());
        objects.forEach(x -> System.out.print(x + " "));
    }
}
