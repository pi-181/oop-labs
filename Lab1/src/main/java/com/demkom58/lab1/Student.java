package com.demkom58.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private final String name;
    private final List<EctsMark> marks = new ArrayList<>();

    private boolean budget = false;

    public Student(String name) {
        Objects.requireNonNull(name, "Name can't be null!");
        this.name = name;
    }

    public Student(String name, boolean budget) {
        this(name);
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }

    public List<EctsMark> getMarks() {
        return marks;
    }

    public boolean isGranted() {
        if (!budget)
            return false;

        return marks.stream().allMatch(mark -> mark.ordinal() <= EctsMark.C.ordinal());
    }

    public byte getAvgMark() {
        return (byte) (marks.stream().mapToInt(EctsMark::getNationalMark).sum() / marks.size());
    }

}
