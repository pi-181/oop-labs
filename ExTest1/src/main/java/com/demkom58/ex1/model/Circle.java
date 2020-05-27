package com.demkom58.ex1.model;

import java.util.Objects;

public class Circle implements Shape {
    private final double diameter;

    public Circle(double diameter) {
        this.diameter = diameter;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public double perimeter() {
        return Math.PI * diameter;
    }

    @Override
    public double square() {
        return Math.PI * Math.pow((diameter / 2), 2);
    }

    @Override
    public String toString() {
        return "Circle: " +
                "d = " + String.format("%.3f", diameter) + "; " +
                "perimeter = " + String.format("%.3f", perimeter()) + "; " +
                "square = " + String.format("%.3f", square()) + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.diameter, diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diameter);
    }
}
