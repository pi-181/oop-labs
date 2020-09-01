package com.demkom58.lab9.model;

public class Cylinder extends AbstractForm {
    private float height;
    private float diameter;

    public Cylinder(Wood wood, float height, float diameter) throws Exception {
        super(wood);

        setHeight(height);
        setDiameter(diameter);

        final float weight = weight();
        if (weight > 1000)
            throw new Exception(weight + " is not correct weight. It should be less than 1000 kg.");
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) throws Exception {
        if (5 > height || height > 20)
            throw new Exception("Height of Cylinder should be in range from 5 to 20 m");

        this.height = height;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) throws Exception {
        if (5 > diameter || diameter > 10)
            throw new Exception("Diameter of Cylinder should be in range from 5 to 10 m");

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
