package com.demkom58.lab12.model;

import java.io.Serializable;
import java.util.Objects;

public class Wood implements Serializable {
    private int id;
    private String name;
    private float density;

    public Wood(int id, String name, float density) {
        Objects.requireNonNull(name, "Name of wood can't be null!");
        this.id = id;
        this.name = name;
        this.density = density;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    @Override
    public String toString() {
        return "Wood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destiny=" + density +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wood wood = (Wood) o;
        return id == wood.id &&
                Float.compare(wood.density, density) == 0 &&
                name.equals(wood.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, density);
    }
}
