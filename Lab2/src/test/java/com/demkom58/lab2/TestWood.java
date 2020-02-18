package com.demkom58.lab2;

import com.demkom58.lab2.model.Wood;
import com.demkom58.lab2.store.WoodDirectory;

public class TestWood {
    public static void main(String[] args) {
        WoodDirectory woodDirectory = new WoodDirectory();
        System.out.println(woodDirectory.get(3) + "\n");

        Wood wood = new Wood(4, "Dub", 1f);
        if (woodDirectory.add(wood)) {
            System.out.println(woodDirectory);
        } else System.err.println(wood + " вже існує");
    }
}
