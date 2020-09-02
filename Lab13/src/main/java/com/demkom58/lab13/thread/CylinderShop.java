package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.Cylinder;
import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Wood;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WoodDirectory;

public class CylinderShop extends WoodShop {

    public CylinderShop(String name, WoodDirectory woodDirectory, ProductStore productStore, int n) {
        super(name, woodDirectory, productStore, n);
    }

    @Override
    protected IWeight createProduct() {
        Wood wood = woodDirectory.get(random.nextInt(3) + 1);

        float height = 0.1f + random.nextFloat();
        float width = 0.1f + random.nextFloat();

        return new Cylinder(wood, height, width);
    }
}
