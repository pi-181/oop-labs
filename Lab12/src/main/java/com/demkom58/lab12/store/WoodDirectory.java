package com.demkom58.lab12.store;

import com.demkom58.lab12.model.Wood;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WoodDirectory extends AbstractStore<Wood> {
    private final Map<Integer, Wood> map = new HashMap<>();

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

        map.put(wood.getId(), wood);
        return true;
    }

    @Override
    public Wood get(int id) {
        return map.get(id);
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public @NotNull Iterator<Wood> iterator() {
        return map.values().iterator();
    }

    @Override
    public Wood[] getArr() {
        return map.values().toArray(new Wood[0]);
    }

}
