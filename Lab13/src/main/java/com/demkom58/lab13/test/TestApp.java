package com.demkom58.lab13.test;

import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WoodDirectory;
import com.demkom58.lab13.thread.CylinderShop;
import com.demkom58.lab13.thread.TimberShop;

public class TestApp {
    private final WoodDirectory woodDirectory = new WoodDirectory();
    private final ProductStore productStore = new ProductStore();

    public void startApp() throws Exception {
        TimberShop shop1 = new TimberShop("timberShop", woodDirectory, productStore, 3);
        CylinderShop shop2 = new CylinderShop("cylinderShop", woodDirectory, productStore, 3);
        Thread thread1 = new Thread(shop1);
        Thread thread2 = new Thread(shop2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Виконання завершено! В сховищі: " + productStore.getCount());
    }

}
