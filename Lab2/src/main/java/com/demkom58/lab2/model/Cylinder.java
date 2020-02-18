package com.demkom58.lab2.model;

public class Cylinder {
    private Wood wood;
    private float height;
    private float diameter;

    public Cylinder(Wood wood, float height, float diameter) {
        this.wood = wood;
        this.height = height;
        this.diameter = diameter;
    }

    public float volume() {
        return (float) (Math.PI * Math.pow(diameter / 2d, 2d)) * height;
    }

    public float weight() {
        return volume() * wood.getDensity();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "wood=" + wood.getName() +
                ", diameter=" + height +
                ", height=" + height +
                '}';
    }
}