package com.lpc.synchronizedTest;

import java.util.concurrent.locks.ReentrantLock;

public class TestFairReentrantLock implements Counter {

    private int num;
    private ReentrantLock reentrantLock = new ReentrantLock(true);

    @Override
    public void increment() {
        reentrantLock.lock();
        num++;
        reentrantLock.unlock();
    }

    @Override
    public int getCount() {
        return num;
    }
}
