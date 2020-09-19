package com.demkom58.lab10.store;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractStore<T> implements Iterable<T>, Serializable {
    protected final String name;
    protected final List<T> list = new ArrayList<>();

    public AbstractStore(@NotNull String name) {
        this.name = name;
    }

    public boolean add(T t) {
        return list.add(t);
    }

    public T get(int index) {
        return list.get(index);
    }

    public void remove(Predicate<T> condition) {
        list.removeIf(condition);
    }

    public void doOnlyFor(Predicate<T> condition, Consumer<T> action) {
        for (T t : list)
            if (condition.test(t))
                action.accept(t);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @NotNull
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public Object[] getArr() {
        return list.toArray();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(name).append(": \n");
        if (list.size() == 0) {
            builder.append("Пусто...\n");
            return builder.toString();
        }

        for (T o : this)
            builder.append(o).append("\n");

        return builder.toString();
    }


}
