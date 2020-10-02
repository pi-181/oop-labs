package com.demkom58.lab11.store;

import com.demkom58.lab11.event.IProductListener;
import com.demkom58.lab11.event.ProductEvent;
import com.demkom58.lab11.model.Cylinder;
import com.demkom58.lab11.model.IWeight;
import com.demkom58.lab11.model.Timber;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ProductStore<T extends IWeight> extends AbstractStore<T> {
    private final List<IProductListener> productListeners = new ArrayList<>();
    private final Map<T, Integer> weights = new TreeMap<>((o1, o2) -> {
        final Class<? extends IWeight> o1c = o1.getClass();
        final Class<? extends IWeight> o2c = o2.getClass();
        if (o1c == Timber.class) {
            if (o2c == Timber.class)
                return Float.compare(o1.weight(), o2.weight()); // weight compare
            return o2c == Cylinder.class ? 1 : -1; // Timbers should be lower than cylinders
        }

        // if compare Cylinder and some other
        if (o1c == Cylinder.class)
            return o2c == Cylinder.class ?
                    Float.compare(o1.weight(), o2.weight()) // so as we compare cylinders, lets compare their weight
                    : -1; // cylinder always in top, so return -1

        // second argument is cylinder or timber, it always in top so return 1
        if (o2c == Cylinder.class || o2c == Timber.class)
            return 1;

        // lefts only waste, just compare it's weight
        return Float.compare(o1.weight(), o2.weight());
    });

    public ProductStore() {
        this("Каталог виробів");
    }

    public ProductStore(String name) {
        super(name);
    }

    @Override
    public boolean add(T t) {
        weights.compute(t, (k, v) -> v == null ? 1 : v + 1);
        fireProductListener(new ProductEvent(this, t));
        return true;
    }

    @Override
    public T get(int index) {
        int i = 0;
        for (Map.Entry<T, Integer> t : weights.entrySet()) {
            for (int j = 0; j < t.getValue(); j++) {
                if (i == index)
                    return t.getKey();
                i++;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    /**
     * In that case to provide backwards compatibility that iterator must work as
     * default collection iterator.
     */
    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<Map.Entry<T, Integer>> iterator = weights.entrySet().iterator();
            private Map.Entry<T, Integer> entry = iterator.hasNext() ? iterator.next() : null;
            private int current = entry == null ? 0 : entry.getValue();

            @Override
            public boolean hasNext() {
                return current == -1 ? iterator.hasNext() : current != 0;
            }

            @Override
            public T next() {
                // if is -1 it means that we must fetch next entry from map iterator
                if (current == -1) {
                    entry = iterator.next();
                    current = entry.getValue();
                }

                // send handle not allowed next to map iterator
                if (current == 0)
                    return iterator.next().getKey();

                current--;
                final T k = entry.getKey();

                // mark to fetch next if needed and possible
                if (current == 0 && iterator.hasNext())
                    current = -1;

                return k;
            }

            @Override
            public void remove() {
                if (entry != null && entry.getValue() > 1) {
                    entry.setValue(entry.getValue() - 1);
                } else iterator.remove();
            }
        };
    }

    @NotNull
    public Iterator<Map.Entry<T, Integer>> mapIterator() {
        return weights.entrySet().iterator();
    }

    public void addProductListener(IProductListener listener) {
        productListeners.add(listener);
    }

    public void removeProductListener(IProductListener listener) {
        productListeners.remove(listener);
    }

    protected void fireProductListener(ProductEvent event) {
        productListeners.forEach(listener -> listener.onProductEvent(event));
    }

    @Override
    public IWeight[] getArr() {
        final ArrayList<IWeight> objects = new ArrayList<>(weights.size() * 2);
        // simple fill list
        weights.forEach((key, value) -> {
            for (int i = 0; i < value; i++)
                objects.add(key);
        });
        return objects.toArray(new IWeight[0]);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(name).append(": \n");
        if (getCount() == 0)
            return builder.append("Пусто...\n").toString();

        final Iterator<Map.Entry<T, Integer>> entryIterator = mapIterator();
        while (entryIterator.hasNext()) {
            final Map.Entry<T, Integer> next = entryIterator.next();
            builder.append(next.getValue()).append("x - ").append(next.getKey()).append("\n");
        }

        return builder.toString();
    }

}
