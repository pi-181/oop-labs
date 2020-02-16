package com.demkom58.lab1;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentProgram {
    private final List<Student> students = new ArrayList<>();
    private final List<String> menu = Arrays.asList(
            "",
            "",
            "----------- Student Program -----------",
            "0. Add new student",
            "1. Remove student",
            "2. Show students",
            "3. Show granted students",
            "4. Exit",
            "---------------------------------------"
    );

    public void printGreetings() {
        Arrays.asList(
                "",
                "        Hello user!",
                "This java program developed",
                "by student Demydenko Maskym",
                "    from PI-181 of NUChP",
                "",
                "   Thanks for attention!",
                ""
        ).forEach(System.out::println);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu.forEach(System.out::println);
            System.out.println("Choose operation: ");

            final String input = scanner.nextLine().trim();
            for (int i = 0; i < 5; i++)
                System.out.println();

            switch (input) {
                case "0":
                    addStudent();
                    break;
                case "1":
                    removeStudent();
                    break;
                case "2":
                    showStudents();
                    break;
                case "3":
                    showGrantedStudents();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Unknown operation...");
            }
        }
    }

    public void addStudent() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("> Enter student name.");
        final String name = scanner.nextLine().trim();

        System.out.println("> Is budget? (Yes/No)");
        final boolean budget = scanner.nextLine().trim().equalsIgnoreCase("yes");

        System.out.println("> Enter marks (in ECTS format), delimited by space.");
        final String marks = scanner.nextLine().trim();

        final List<EctsMark> nationalMarks = Arrays.stream(marks.split(Pattern.quote(" ")))
                .map(Byte::parseByte)
                .map(EctsMark::fromEcts)
                .collect(Collectors.toList());

        final Student student = new Student(name, budget);
        student.getMarks().addAll(nationalMarks);

        students.add(student);
        System.out.println(String.format("> New student with name %s successfully added.", student.getName()));
    }

    public void removeStudent() {

        showStudents();

        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the identifier of student (It's specified in [] of list): ");

        final int identifier;
        try {
            identifier = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("> Student remove operation canceled.");
            return;
        }

        final Student student = students.get(identifier);
        if (student == null) {
            System.out.println("> Unknown student selected.");
            return;
        }

        System.out.println("> Student " + student.getName() + " successfully removed.");
    }

    public void showStudents() {
        System.out.println("> Registered Students: ");
        for (int i = 0; i < students.size(); i++)
            System.out.println(formatInfo(i, students.get(i)));
    }

    public void showGrantedStudents() {
        System.out.println("> Granted Students: ");
        for (int i = 0; i < students.size(); i++) {
            final Student student = students.get(i);
            if (!student.isGranted())
                continue;

            System.out.println(formatInfo(i, student));
        }
    }

    private String formatInfo(int id, Student student) {
        final String marks = student.getMarks().stream()
                .map(EctsMark::name)
                .collect(Collectors.joining(", "));

        return String.format("[%d] %s; Avg mark: %d; Marks: %s; Budget: %s",
                id,
                student.getName(),
                student.getAvgMark(),
                marks,
                student.isBudget() ? "Yes" : "No"
        );
    }

}
