package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Timber;
import com.demkom58.lab13.model.Wood;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;

import java.util.function.Consumer;

public class TimberShop extends WoodShop<Timber> {

    public TimberShop(String name, WoodDirectory woodDirectory, ProductStore<IWeight> productStore,
                      WasteStore wasteStore, WoodLock woodLock, long workTime,
                      long timePerSingle, Consumer<String> logger) {
        super(name, woodDirectory, productStore, workTime, timePerSingle, logger, wasteStore, woodLock);
    }

    @Override
    protected Timber createProduct() {
        Wood wood = woodDirectory.get(random.nextInt(3) + 1);

        float length = 1.0f + random.nextFloat() * 10;
        float height = 0.1f + random.nextFloat();
        float width = 0.1f + random.nextFloat();

        return new Timber(wood, length, height, width);
    }

}
