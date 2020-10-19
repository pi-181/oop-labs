package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Waste;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;

import java.util.function.Consumer;

public class WasteShop extends WoodShop<Waste> {

    public WasteShop(String name, ProductStore<IWeight> productStore, WasteStore wasteStore,
                     WoodLock woodLock, long workTime, long timePerSingle, Consumer<String> logger) {
        super(name, null, productStore, workTime, timePerSingle, logger, wasteStore, woodLock);
    }

    @Override
    protected Waste createProduct() {
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
        log("Started");
        while (System.currentTimeMillis() < workToTime) {
            // join lock
            woodLock.lock();
            try {
                // pre check of condition, for correct logging
                if (wasteStore.getSize() == 0) {
                    log("is empty");
                    while (wasteStore.getSize() == 0)
                        woodLock.isEmpty().await();
                }

                // decrement 1 waste
                wasteStore.removeWithPrint(this);
                woodLock.isFull().signal();

                final IWeight product = createProduct();
                // wait for creation product
                Thread.sleep(super.timePerSingle);
                productStore.add(product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                woodLock.unlock();
            }

        }
        log("Finished");
    }
}
