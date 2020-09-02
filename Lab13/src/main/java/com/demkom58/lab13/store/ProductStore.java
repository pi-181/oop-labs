package com.demkom58.lab13.store;

import com.demkom58.lab13.event.IProductListener;
import com.demkom58.lab13.event.ProductEvent;
import com.demkom58.lab13.model.IWeight;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductStore extends AbstractStore<IWeight> {
    private final Object monitor = new Object();
    private final List<IProductListener> productListeners = new CopyOnWriteArrayList<>();
    private final List<IWeight> weights = new ArrayList<>();

    public ProductStore() {
        super("Каталог виробів");
    }

    @Override
    public boolean add(IWeight abstractForm) {
        synchronized (monitor) {
            weights.add(abstractForm);
            fireProductListener(new ProductEvent(this , abstractForm));
        }

        return true;
    }

    @Override
    public IWeight get(int index) {
        return weights.get(index);
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    @Override
    public @NotNull Iterator<IWeight> iterator() {
        return weights.iterator();
    }

    @NotNull
    public ListIterator<IWeight> listIterator() {
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
