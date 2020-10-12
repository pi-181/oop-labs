package com.demkom58.lab14.graph;

public interface Entity {
    /**
     * If value is true it means that this valued
     * already or will be removed from to-render list.
     *
     * @return dead marker
     */
    boolean isDead();

    /**
     * Marks entity as dead,
     * that will not update and rendered.
     */
    void kill();
}
