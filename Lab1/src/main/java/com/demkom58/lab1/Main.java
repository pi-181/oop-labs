package com.demkom58.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        final boolean granted = budget && nationalMarks.stream().allMatch(mark -> mark.ordinal() <= EctsMark.C.ordinal());
        final String formattedMarks = nationalMarks.stream()
                .map(mark -> mark.name() + "(" + mark.getNationalMark() + ")")
                .collect(Collectors.joining(", "));

        System.out.println(String.format("%s; Marks: %s; Budget: %s; Granted: %s",
                name,
                formattedMarks,
                budget ? "Yes" : "No",
                granted ? "Yes" : "No"
        ));
    }

}
