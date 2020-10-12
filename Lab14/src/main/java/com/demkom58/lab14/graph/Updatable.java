package com.demkom58.lab14.graph;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Comparator;

public interface Updatable extends Entity{
    /**
     * Updates (or ticks) entity
     * or in other words do some business logic
     *
     * @param container container with info about window container
     */
    void update(Container container);

    /**
     * Getter for update priority
     * @return int value
     */
    int getPriority();
}
