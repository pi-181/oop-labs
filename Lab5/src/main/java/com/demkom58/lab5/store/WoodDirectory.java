package com.demkom58.lab5.store;

import com.demkom58.lab5.model.Wood;

import java.util.Arrays;

public class WoodDirectory {
    private Wood[] arr = new Wood[3];
    private int count;

    {
        arr[0] = new Wood(1, "Модрина", 1.1f);
        arr[1] = new Wood(2, "Ялина", 0.9f);
        arr[2] = new Wood(3, "Сосна", 0.7f);
        count = arr.length;
    }

    public Wood get(int id) {
        for (Wood wood : arr)
            if (wood.getId() == id)
                return wood;

        return null;
    }

    public boolean add(Wood wood) {
        if (get(wood.getId()) != null)
            return false;

        if (arr.length == count)
            arr = Arrays.copyOf(arr, count + count / 2);

        arr[count++] = wood;
        return true;
    }

    public Wood[] getArr() {
        return Arrays.copyOf(arr, count);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Каталог деревини: \n");
        for (int i = 0; i < count; i++)
            builder.append(arr[i]).append("\n");

        return builder.toString();
    }
}
