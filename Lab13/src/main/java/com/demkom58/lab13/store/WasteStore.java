package com.demkom58.lab13.store;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.thread.WoodShop;

import java.util.function.Consumer;

public class WasteStore {
    private final Consumer<String> logger;
    private final int maxSize;
    private int size = 0;

    public WasteStore(Consumer<String> logger, int maxSize) {
        this.logger = logger;
        this.maxSize = maxSize;
    }

    public void addWithPrint(WoodShop<? extends IWeight> shop) {
        size++;
        logger.accept(shop.getName() + " add 1, became " + size);
    }

    public void removeWithPrint(WoodShop<? extends IWeight> shop) {
        size--;
        logger.accept(shop.getName() + " remove 1, became " + size);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Залишки: " + getSize() + "/" + getMaxSize();
    }
}
