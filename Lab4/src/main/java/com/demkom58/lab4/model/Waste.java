package com.demkom58.lab4.model;

public class Waste implements IWeight {
    private float weight;

    public Waste(float weight) {
        this.weight = weight;
    }

    @Override
    public float weight() {
        return weight;
    }
}
