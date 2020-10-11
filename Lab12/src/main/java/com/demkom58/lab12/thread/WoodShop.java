package com.demkom58.lab12.thread;

import com.demkom58.lab12.model.IWeight;
import com.demkom58.lab12.store.ProductStore;
import com.demkom58.lab12.store.WoodDirectory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public WoodShop(String name, WoodDirectory woodDirectory, ProductStore<IWeight> productStore,
                    long workTime, long timePerSingle, Consumer<String> logger) {
        this.name = name;
        this.woodDirectory = woodDirectory;
        this.productStore = productStore;

        this.workToTime = System.currentTimeMillis() + workTime;
        this.timePerSingle = timePerSingle;

        this.logger = logger;
    }

    @Override
    public void run() {
        log("Started");
        while (System.currentTimeMillis() < workToTime) {
            log("Started creating product");
            try {
                Thread.sleep(timePerSingle);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final T product = createProduct();
            log("Finished creating product: " + product);
            productStore.add(product);
        }
        log("Finished");
    }

    private void log(String message) {
        logger.accept("[" + timeFormat.format(LocalDateTime.now()) + "] "
                + getClass().getSimpleName() + ": " + message);
    }

    public String getName() {
        return name;
    }

    protected abstract T createProduct();

}
