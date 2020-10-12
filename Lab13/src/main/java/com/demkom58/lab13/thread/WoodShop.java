package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.model.Waste;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;

public abstract class WoodShop<T extends IWeight> implements Runnable {

    protected final Random random = new Random();
    protected final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected final String name;
    protected final WoodDirectory woodDirectory;
    protected final ProductStore<IWeight> productStore;

    protected final long workToTime;
    protected final long timePerSingle;

    protected final Consumer<String> logger;

    protected final WasteStore wasteStore;
    protected final WoodLock woodLock;

    public WoodShop(String name, WoodDirectory woodDirectory, ProductStore<IWeight> productStore,
                    long workTime, long timePerSingle, Consumer<String> logger,
                    WasteStore wasteStore, WoodLock woodLock) {
        this.name = name;
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;

        this.workToTime = System.currentTimeMillis() + workTime;
        this.timePerSingle = timePerSingle;

        this.logger = logger;

        this.wasteStore = wasteStore;
        this.woodLock = woodLock;
    }

    @Override
    public void run() {
        log("Started");
        while (System.currentTimeMillis() < workToTime) {
            woodLock.lock();
            try {
                while (wasteStore.getSize() >= wasteStore.getMaxSize())
                    woodLock.isFull().await();

                wasteStore.addWithPrint(this);
                woodLock.isEmpty().signal();

                final T product = createProduct();
                Thread.sleep(timePerSingle);
                log("Finished creating product: " + product);
                productStore.add(product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                woodLock.unlock();
            }
        }
        log("Finished");
    }

    protected void log(String message) {
        logger.accept("[" + timeFormat.format(LocalDateTime.now()) + "] "
                + getName() + ": " + message);
    }

    public String getName() {
        return name;
    }

    protected abstract T createProduct();

}
