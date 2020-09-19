package com.demkom58.lab10.model;

import java.util.Objects;

public class Timber extends AbstractForm {
    private float length;
    private float height;
    private float width;

    public Timber(Wood wood, float length, float height, float width) throws Exception {
        super(wood);
        Objects.requireNonNull(wood, "Wood can't be null");

        setLength(length);
        setHeight(height);
        setWidth(width);

        final float weight = weight();
        if (weight > 1000)
            throw new Exception(weight + " is not correct weight. It should be less than 1000 kg.");
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) throws Exception {
        if (1 > length || length > 100)
            throw new Exception("Length of Timber should be in range from 1 to 100 m");

        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) throws Exception {
        if (1 > height || height > 40)
            throw new Exception("Height of Timber should be in range from 1 to 40 m");

        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) throws Exception {
        if (1 > width || width > 60)
            throw new Exception("Width of Timber should be in range from 1 to 60 m");

        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timber timber = (Timber) o;
        return Float.compare(timber.length, length) == 0 &&
                Float.compare(timber.height, height) == 0 &&
                Float.compare(timber.width, width) == 0 &&
                wood.equals(timber.wood);
    }

    @Override
    public float volume() {
        return length * height * width;
    }

    @Override
    public float weight() {
        return volume() * wood.getDensity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(wood, length, height, width);
    }

    @Override
    public String toString() {
        return "Timber{" +
                "wood=" + wood +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", weight=" + weight() +
                '}';
    }
}
