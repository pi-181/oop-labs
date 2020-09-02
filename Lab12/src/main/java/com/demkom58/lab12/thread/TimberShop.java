package com.demkom58.lab12.thread;

import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.model.Timber;
import com.demkom58.lab12.model.Wood;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;

public class TimberShop extends WoodShop {

    public TimberShop(String name, WoodDirectory woodDirectory, ProductStore productStore, int n) {
        super(name, woodDirectory,productStore, n);
    }

    @Override
    protected IWeight createProduct() {
        Wood wood = woodDirectory.get(random.nextInt(3) + 1);

        float length = 1.0f + random.nextFloat() * 10;
        float height = 0.1f + random.nextFloat();
        float width = 0.1f + random.nextFloat();

        return new Timber(wood, length, height, width);
    }

}
