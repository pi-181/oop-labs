package com.demkom58.lab8.event;

import java.util.EventListener;

public interface IProductListener extends EventListener {
    void onProductEvent(ProductEvent event);
}
