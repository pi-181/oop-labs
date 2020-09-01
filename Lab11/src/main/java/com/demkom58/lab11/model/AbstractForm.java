package com.demkom58.lab11.model;

public abstract class AbstractForm implements IWeight {
    protected Wood wood;

    public AbstractForm(Wood wood) {
        this.wood = wood;
    }

    public Wood getWood() {
        return wood;
    }

    public void setWood(Wood wood) {
        this.wood = wood;
    }

    public abstract float volume();

}
