package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Waste;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;

public class WasteShop extends WoodShop {

    public WasteShop(String name,
                     ProductStore productStore,
                     WasteStore wasteStore,
                     TimberShop timberShop,
                     CylinderShop cylinderShop,
                     WoodLock woodLock) {
        super(
                name,
                null,
                productStore,
                wasteStore,
                woodLock,
                timberShop.getN() + cylinderShop.getN()
        );
    }

    @Override
    protected IWeight createProduct() {
        float weight = 25 + random.nextFloat() * 75;
        Waste waste = null;

        try {
            waste = new Waste(weight);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return waste;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            woodLock.lock();
            try {
                while (wasteStore.getSize() == 0)
                    woodLock.isEmpty().await();

                wasteStore.removeWithPrint(this);
                woodLock.isFull().signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                woodLock.unlock();
            }

            final IWeight product = createProduct();
            productStore.add(product);
        }
    }
}
