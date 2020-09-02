package com.demkom58.lab13.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WoodLock extends ReentrantLock {
    private Condition full = this.newCondition();
    private Condition empty = this.newCondition();

    public Condition isFull() {
        return full;
    }

    public Condition isEmpty() {
        return empty;
    }
}
