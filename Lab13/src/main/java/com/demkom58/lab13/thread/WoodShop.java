package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Waste;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;

import java.util.Random;

public abstract class WoodShop implements Runnable {

    protected final Random random = new Random();
    protected final String name;
    protected final WoodDirectory woodDirectory;
    protected final ProductStore productStore;
    protected final WasteStore wasteStore;
    protected final WoodLock woodLock;
    protected final int n;

    public WoodShop(String name,
                    WoodDirectory woodDirectory,
                    ProductStore productStore,
                    WasteStore wasteStore,
                    WoodLock woodLock,
                    int n) {
        this.name = name;
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;
        this.wasteStore = wasteStore;
        this.woodLock = woodLock;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            IWeight timber = createProduct();
            productStore.add(timber);

            woodLock.lock();
            try {
                while (wasteStore.getSize() >= wasteStore.getMaxSize())
                    woodLock.isFull().await();

                wasteStore.addWithPrint(this);
                woodLock.isEmpty().signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                woodLock.unlock();
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getN() {
        return n;
    }

    protected abstract IWeight createProduct();

}
