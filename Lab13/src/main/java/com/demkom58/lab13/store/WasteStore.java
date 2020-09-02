package com.demkom58.lab13.store;

import com.demkom58.lab13.thread.WoodShop;

public class WasteStore {
    private int maxSize;
    private int size = 0;

    public WasteStore(int maxSize) {
        this.maxSize = maxSize;
    }

    public void addWithPrint(WoodShop shop) {
        size++;
        System.out.println(shop.getName() + " add 1, became " + size);
    }

    public void removeWithPrint(WoodShop shop) {
        size--;
        System.out.println(shop.getName() + " remove 1, became " + size);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getSize() {
        return size;
    }

}
