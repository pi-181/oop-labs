package com.demkom58.lab10.event;

import java.util.EventListener;

public interface IProductListener extends EventListener {
    void onProductEvent(ProductEvent event);
}
