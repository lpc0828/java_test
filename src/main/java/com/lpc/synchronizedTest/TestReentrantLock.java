package com.lpc.synchronizedTest;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock implements Counter {
    private int num;
    private ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void increment() {
        lock.lock();
        num++;
        lock.unlock();
    }

    @Override
    public int getCount() {
        return num;
    }
}
