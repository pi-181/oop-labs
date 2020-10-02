package com.demkom58.lab11.store;

import com.demkom58.lab11.event.IProductListener;
import com.demkom58.lab11.event.ProductEvent;
import com.demkom58.lab11.model.IWeight;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductStore<T extends IWeight> extends AbstractStore<T> {
    private final List<IProductListener> productListeners = new ArrayList<>();
    private final List<T> weights = new ArrayList<>();

    public ProductStore() {
        this("Каталог виробів");
    }

    public ProductStore(String name) {
        super(name);
    }

    @Override
    public boolean add(T t) {
        weights.add(t);
        fireProductListener(new ProductEvent(this, t));
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
    @NotNull
    public Iterator<T> iterator() {
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
