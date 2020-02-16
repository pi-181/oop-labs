package com.demkom58.lab2.store;

import com.demkom58.lab2.model.Timber;

import java.util.Arrays;

public class ProductStore {
    private Timber[] arr = new Timber[3];
    private int count = 0;

    public void add(Timber timber) {
        if (arr.length == count)
            arr = Arrays.copyOf(arr, count + count / 2);

        arr[count++] = timber;
    }

    public Timber[] getArr() {
        return Arrays.copyOf(arr, count);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Каталог виробів: \n");
        for (int i = 0; i < count; i++)
            builder.append(arr[i]).append("\n");

        return builder.toString();
    }
}
