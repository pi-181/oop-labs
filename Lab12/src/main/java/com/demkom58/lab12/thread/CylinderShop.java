package com.demkom58.lab12.thread;

import com.demkom58.lab12.model.Cylinder;
import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.model.Wood;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;

public class CylinderShop extends WoodShop<Cylinder> {

    public CylinderShop(String name, WoodDirectory woodDirectory, ProductStore<IWeight> productStore, int n) {
        super(name, woodDirectory, productStore, n);
    }

    @Override
    protected Cylinder createProduct() {
        Wood wood = woodDirectory.get(random.nextInt(3) + 1);

        float height = 0.1f + random.nextFloat();
        float width = 0.1f + random.nextFloat();

        return new Cylinder(wood, height, width);
    }
}
