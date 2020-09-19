package com.demkom58.lab10.model;

public class Waste implements IWeight {
    private float weight;

    public Waste(float weight) throws Exception {
        setWeight(weight);
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) throws Exception {
        if (weight < 1 || weight > 1000)
            throw new Exception(weight + " is not correct weight.\n" +
                    "It should be in range from 1 to 1000 kg.");

        this.weight = weight;
    }

    @Override
    public float weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Waste{" +
                "weight=" + weight +
                '}';
    }
}
