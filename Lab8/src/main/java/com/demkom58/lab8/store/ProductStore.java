package com.demkom58.lab8.store;

import com.demkom58.lab8.event.IProductListener;
import com.demkom58.lab8.event.ProductEvent;
import com.demkom58.lab8.model.IWeight;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductStore extends AbstractStore<IWeight> {
    private final List<IProductListener> productListeners = new CopyOnWriteArrayList<>();

    public ProductStore() {
        super("Каталог виробів");
    }

    @Override
    public boolean add(IWeight abstractForm) {
        final boolean result = super.add(abstractForm);
        fireProductListener(new ProductEvent(this , abstractForm));
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
