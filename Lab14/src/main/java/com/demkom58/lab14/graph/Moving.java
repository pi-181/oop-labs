package com.demkom58.lab14.graph;

import java.util.function.Consumer;

public interface Moving<T> {

    Consumer<T> getOnDone();

    void setOnDone(Consumer<T> onDone);

    void setProgress(float progress);

    float getProgress();

    void setTarget(Point2D target);

    Point2D getTarget();

    void setSpeed(float speed);

    float getSpeed();

    boolean isDone();

}
