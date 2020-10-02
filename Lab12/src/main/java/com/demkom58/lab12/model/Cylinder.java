package com.demkom58.lab12.model;

import java.util.Objects;

public class Cylinder extends AbstractForm {
    private float height;
    private float diameter;

    public Cylinder(Wood wood, float height, float diameter) {
        super(wood);

        setHeight(height);
        setDiameter(diameter);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    @Override
    public float volume() {
        return (float) (Math.PI * Math.pow(diameter / 2d, 2d)) * height;
    }

    @Override
    public float weight() {
        return volume() * wood.getDensity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cylinder cylinder = (Cylinder) o;
        return Float.compare(cylinder.height, height) == 0 &&
                Float.compare(cylinder.diameter, diameter) == 0 &&
                wood.equals(cylinder.wood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, diameter);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "wood=" + getWood() +
                ", diameter=" + diameter +
                ", height=" + height +
                ", weight=" + weight() +
                '}';
    }

}
