package com.demkom58.lab13.thread;

import com.demkom58.lab13.model.IWeight;
import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Consumer;

public abstract class WoodShop<T extends IWeight> implements Runnable {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected final Random random = new Random();

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
            // join lock
            woodLock.lock();
            try {
                // pre check of condition, for correct logging
                if (wasteStore.getSize() >= wasteStore.getMaxSize()) {
                    log("waste storage is full, waiting...");
                    while (wasteStore.getSize() >= wasteStore.getMaxSize())
                        woodLock.isFull().await();
                    log("waste storage is not full, continue work");
                }

                // add to waste storage
                wasteStore.addWithPrint(this);
                woodLock.isEmpty().signal();

                final T product = createProduct();
                // wait for creation product
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

    /**
     * Log message in correct format
     */
    protected void log(String message) {
        logger.accept("[" + TIME_FORMATTER.format(LocalDateTime.now()) + "] "
                + getName() + ": " + message);
    }

    public String getName() {
        return name;
    }

    protected abstract T createProduct();

}
