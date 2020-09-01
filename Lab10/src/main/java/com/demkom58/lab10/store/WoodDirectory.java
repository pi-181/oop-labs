package com.demkom58.lab10.store;

import com.demkom58.lab10.model.Wood;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class WoodDirectory extends AbstractStore<Wood> {

    public WoodDirectory() {
        super("Каталог деревини");
        add(new Wood(1, "Модрина", 1.1f));
        add(new Wood(2, "Ялина", 0.9f));
        add(new Wood(3, "Сосна", 0.7f));
    }

    @Override
    public boolean add(Wood wood) {
        if (get(wood.getId()) != null)
            return false;

        return super.add(wood);
    }

    @Override
    public Wood get(int id) {
        for (Wood wood : this) {
            if (wood.getId() == id)
                return wood;
        }

        return null;
    }

    @Override
    public Wood[] getArr() {
        return Arrays.copyOf(arr, count, Wood[].class);
    }

}
