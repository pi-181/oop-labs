package com.demkom58.lab9.store;

import com.demkom58.lab9.event.IProductListener;
import com.demkom58.lab9.event.ProductEvent;
import com.demkom58.lab9.model.IWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductStore<T extends IWeight> extends AbstractStore<T> {
    private final List<IProductListener> productListeners = new ArrayList<>();

    public ProductStore() {
        this("Каталог виробів");
    }

    public ProductStore(String name) {
        super(name);
    }

    @Override
    public boolean add(T t) {
        final boolean result = super.add(t);
        fireProductListener(new ProductEvent(this , t));
        return result;
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
        return Arrays.copyOf(arr, count, IWeight[].class);
    }

}
