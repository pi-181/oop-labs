package com.demkom58.ex1.model;

import java.util.Objects;

public class Rectangle implements Shape {
    protected final double height;
    protected final double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public double square() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle: " +
                "h = " + String.format("%.3f", height) + "; " +
                "w = " + String.format("%.3f", width) + "; " +
                "perimeter = " + String.format("%.3f", perimeter()) + "; " +
                "square = " + String.format("%.3f", square()) + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.height, height) == 0 &&
                Double.compare(rectangle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }
}
