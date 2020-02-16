package com.demkom58.lab3.model;

import java.util.Objects;

public class Wood {
    private int id;
    private String name;
    private float destiny;

    public Wood(int id, String name, float destiny) {
        Objects.requireNonNull(name, "Name of wood can't be null!");
        this.id = id;
        this.name = name;
        this.destiny = destiny;
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

    public float getDestiny() {
        return destiny;
    }

    public void setDestiny(float destiny) {
        this.destiny = destiny;
    }

    @Override
    public String toString() {
        return "Wood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destiny=" + destiny +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wood wood = (Wood) o;
        return id == wood.id &&
                Float.compare(wood.destiny, destiny) == 0 &&
                name.equals(wood.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, destiny);
    }
}
