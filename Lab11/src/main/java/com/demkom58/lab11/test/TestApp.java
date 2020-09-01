package com.demkom58.lab11.test;

import com.demkom58.lab11.model.Cylinder;
import com.demkom58.lab11.model.IWeight;
import com.demkom58.lab11.model.Timber;
import com.demkom58.lab11.model.Waste;
import com.demkom58.lab11.store.ProductStore;
import com.demkom58.lab11.store.WoodDirectory;

public class TestApp {
    private WoodDirectory woodDirectory = new WoodDirectory();
    private ProductStore productStore = new ProductStore();

    public void startApp() throws Exception {
        productStore.add(new Timber(woodDirectory.getById(1), 5f, 2f, 7f));
        productStore.add(new Timber(woodDirectory.getById(2), 10f, 2f, 6f));
        productStore.add(new Cylinder(woodDirectory.getById(3), 5f, 6f));
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
