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
        if (2 > height || height > 30)
            throw new Exception("Height of Cylinder should be in range from 2 to 30 m");

        this.height = height;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) throws Exception {
        if (1 > diameter || diameter > 40)
            throw new Exception("Diameter of Cylinder should be in range from 1 to 40 m");

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
                ", weight=" + weight() +
                '}';
    }

}
