package com.demkom58.lab13.test;

import com.demkom58.lab13.store.ProductStore;
import com.demkom58.lab13.store.WasteStore;
import com.demkom58.lab13.store.WoodDirectory;
import com.demkom58.lab13.thread.CylinderShop;
import com.demkom58.lab13.thread.TimberShop;
import com.demkom58.lab13.thread.WasteShop;
import com.demkom58.lab13.thread.WoodLock;

public class TestApp {
    private final WoodDirectory woodDirectory = new WoodDirectory();
    private final ProductStore productStore = new ProductStore();
    private final WasteStore wasteStore = new WasteStore(3);

    public void startApp() throws Exception {
        final WoodLock woodLock = new WoodLock();
        TimberShop shop1 = new TimberShop("timberShop", woodDirectory, productStore, wasteStore, woodLock,10);
        CylinderShop shop2 = new CylinderShop("cylinderShop", woodDirectory, productStore, wasteStore, woodLock,10);
        WasteShop shop3 = new WasteShop("wasteShop", productStore, wasteStore, shop1, shop2, woodLock);
        Thread thread1 = new Thread(shop1);
        Thread thread2 = new Thread(shop2);
        Thread thread3 = new Thread(shop3);
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
