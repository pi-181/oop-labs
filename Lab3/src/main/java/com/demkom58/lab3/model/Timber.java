package com.demkom58.lab3.model;

import java.util.Objects;

public class Timber extends AbstractForm {
    private float length;
    private float height;
    private float width;

    public Timber(Wood wood, float length, float height, float width) {
        super(wood);
        Objects.requireNonNull(wood, "Wood can't be null");

        this.length = length;
        this.height = height;
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
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
                '}';
    }
}
