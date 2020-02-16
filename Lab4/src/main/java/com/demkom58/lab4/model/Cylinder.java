package com.demkom58.lab4.model;

public class Cylinder extends AbstractForm {
    private float height;
    private float diameter;

    public Cylinder(Wood wood, float height, float diameter) {
        super(wood);
        this.height = height;
        this.diameter = diameter;
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
    public String toString() {
        return "Cylinder{" +
                "wood=" + getWood() +
                ", diameter=" + diameter +
                ", height=" + height +
                '}';
    }

}
