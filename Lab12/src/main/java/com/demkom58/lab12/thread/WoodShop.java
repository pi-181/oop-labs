package com.demkom58.lab12.thread;

import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;

import java.util.Random;

public abstract class WoodShop<T extends IWeight> implements Runnable {

    protected final Random random = new Random();
    protected final String name;
    protected final WoodDirectory woodDirectory;
    protected final ProductStore<IWeight> productStore;
    protected final int n;

    public WoodShop(String name, WoodDirectory woodDirectory, ProductStore<IWeight> productStore, int n) {
        this.name = name;
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            T timber = createProduct();
            productStore.add(timber);
        }
    }

    public String getName() {
        return name;
    }

    protected abstract T createProduct();

}
