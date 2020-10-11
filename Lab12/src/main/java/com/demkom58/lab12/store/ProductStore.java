package com.demkom58.lab12.store;

import com.demkom58.lab12.event.IProductListener;
import com.demkom58.lab12.event.ProductEvent;
import com.demkom58.lab12.model.IWeight;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductStore<T extends IWeight> extends AbstractStore<T> {
    private final List<IProductListener> productListeners = new CopyOnWriteArrayList<>();
    private final List<T> weights = new ArrayList<>();

    public ProductStore() {
        this("Каталог виробів");
    }

    public ProductStore(String name) {
        super(name);
    }

    @Override
    public boolean add(T t) {
        synchronized (super.lock) {
            weights.add(t);
            fireProductListener(new ProductEvent(this , t));
        }

        return true;
    }

    @Override
    public T get(int index) {
        return weights.get(index);
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return weights.iterator();
    }

    @NotNull
    public ListIterator<T> listIterator() {
        return weights.listIterator();
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
        return weights.toArray(new IWeight[0]);
    }

}
