package com.demkom58.lab2.model;

public class Cylinder extends Timber {
    public Cylinder(Wood wood, float height, float diameter) {
        super(wood, diameter, height, diameter);
    }

    @Override
    public float volume() {
        return (float) (Math.PI * Math.pow(getWidth() / 2d, 2d)) * getHeight();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "wood=" + getWood() +
                ", radius=" + getLength() +
                ", height=" + getHeight() +
                '}';
    }

}
