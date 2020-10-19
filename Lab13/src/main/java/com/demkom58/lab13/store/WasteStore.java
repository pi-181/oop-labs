package com.demkom58.lab13.store;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.thread.WoodShop;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class WasteStore {
    private final Consumer<String> logger;
    private int maxSize;
    private int size = 0;

    public WasteStore(Consumer<String> logger, int maxSize) {
        this.logger = logger;
        this.maxSize = maxSize;
    }

    public void addWithPrint(WoodShop<? extends IWeight> shop) {
        size++;
        log(shop.getName() + " add 1, became " + size);
    }

    public void removeWithPrint(WoodShop<? extends IWeight> shop) {
        size--;
        log(shop.getName() + " remove 1, became " + size);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Залишки: " + getSize() + "/" + getMaxSize();
    }

    protected void log(String message) {
        logger.accept("[" + WoodShop.TIME_FORMATTER.format(LocalDateTime.now()) + "] "
                + getClass().getSimpleName() + ": " + message);
    }
}
