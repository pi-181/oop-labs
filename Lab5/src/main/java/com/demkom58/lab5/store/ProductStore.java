package com.demkom58.lab5.store;

import com.demkom58.lab5.model.IWeight;

import java.util.Arrays;

public class ProductStore {
    private IWeight[] arr = new IWeight[3];
    private int count = 0;

    public void add(IWeight abstractForm) {
        if (arr.length == count)
            arr = Arrays.copyOf(arr, count + count / 2);

        arr[count++] = abstractForm;
    }

    public IWeight[] getArr() {
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
