package com.demkom58.lab7.store;

import com.demkom58.lab7.event.IProductListener;
import com.demkom58.lab7.event.ProductEvent;
import com.demkom58.lab7.model.IWeight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductStore implements Serializable {
    private final Object monitor = new Object();

    private List<IWeight> arr = new ArrayList<>();
    private List<IProductListener> productListeners = new CopyOnWriteArrayList<>();

    public void add(IWeight abstractForm) {
        synchronized (monitor) {
            arr.add(abstractForm);
            fireProductListener(new ProductEvent(this , abstractForm));
        }
    }

    public IWeight[] getArr() {
        return arr.toArray(new IWeight[0]);
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
    public String toString() {
        final StringBuilder builder = new StringBuilder("Каталог виробів: \n");
        for (IWeight iWeight : arr)
            builder.append(iWeight).append("\n");

        return builder.toString();
    }
}
