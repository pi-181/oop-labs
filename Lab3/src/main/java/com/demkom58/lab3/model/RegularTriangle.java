package com.demkom58.lab3.model;

public class RegularTriangle extends AbstractForm {
    private float height;
    private float side;

    public RegularTriangle(Wood wood, float height, float side) {
        super(wood);
        this.height = height;
        this.side = side;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSide() {
        return side;
    }

    public void setSide(float side) {
        this.side = side;
    }

    @Override
    public float volume() {
        return (float) ((Math.pow(side, 2) / 4d) * Math.sqrt(3)) * height;
    }

    @Override
    public float weight() {
        return volume() * wood.getDestiny();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "wood=" + getWood() +
                ", side=" + side +
                ", height=" + height +
                '}';
    }

}
