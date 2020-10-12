package com.demkom58.lab14.graph;

import java.awt.*;

public interface Drawable extends Entity {

    /**
     * Draws entity
     *
     * @param graphics canvas renderer object
     */
    void draw(Graphics2D graphics);

    /**
     * Getter for Z axes, that means render order
     *
     * @return axes int value
     */
    int getZ();
}
