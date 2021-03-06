package com.demkom58.lab3.test;

import com.demkom58.lab3.model.*;
import com.demkom58.lab3.store.ProductStore;
import com.demkom58.lab3.store.WoodDirectory;

public class TestApp {
    private WoodDirectory woodDirectory = new WoodDirectory();
    private ProductStore productStore = new ProductStore();

    public void startApp() {
        productStore.add(new Timber(woodDirectory.get(1), 5f, 0.5f, 0.4f));
        productStore.add(new Timber(woodDirectory.get(2), 10f, 0.5f, 0.4f));
        productStore.add(new Cylinder(woodDirectory.get(3), 5f, 0.8f));
        productStore.add(new Triangle(woodDirectory.get(1), 3f, 0.9f));
        productStore.add(new Waste(20f));

        System.out.println(woodDirectory);
        System.out.println(productStore);

        System.out.printf("Загальна вага: %f", calcWeight());
    }

    private float calcWeight() {
        float totalWeight = 0;
        for (IWeight obj : productStore.getArr())
            totalWeight += obj.weight();

        return totalWeight;
    }
}
