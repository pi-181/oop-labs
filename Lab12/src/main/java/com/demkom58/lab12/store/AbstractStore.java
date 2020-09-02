package com.demkom58.lab12.store;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractStore<T> implements Iterable<T>, Serializable {
    protected final String name;

    public AbstractStore(@NotNull String name) {
        this.name = name;
    }

    public abstract boolean add(T t);

    public abstract T get(int index);

    public void remove(Predicate<T> condition) {
        final Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (condition.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public void doOnlyFor(Predicate<T> condition, Consumer<T> action) {
        for (T t : this)
            if (condition.test(t))
                action.accept(t);
    }

    public abstract int getCount();

    @NotNull
    @Override
    public abstract Iterator<T> iterator();

    public abstract Object[] getArr();

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(name).append(": \n");

        for (T o : this)
            builder.append(o).append("\n");

        return builder.toString();
    }

}
