package com.demkom58.lab7.event;

import com.demkom58.lab7.model.IWeight;

import java.util.EventObject;

public class ProductEvent extends EventObject {

    private IWeight product;
    private long time = System.currentTimeMillis();

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ProductEvent(Object source, IWeight product) {
        super(source);
        this.product = product;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time % 1000 + ": " + product;
    }
}
