package com.demkom58.ex1.model;

public class Triangle extends Rectangle {

    public Triangle(double height, double base) {
        super(height, base);
    }

    @Override
    public double perimeter() {
        return 2 * Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height, 2)) + width;
    }

    @Override
    public double square() {
        return (height * width) / 2;
    }

    @Override
    public String toString() {
        return "Triangle: " +
                "h = " + String.format("%.3f", height) + "; " +
                "base = " + String.format("%.3f", width) + "; " +
                "perimeter = " + String.format("%.3f", perimeter()) + "; " +
                "square = " + String.format("%.3f", square()) + ";";
    }

}
