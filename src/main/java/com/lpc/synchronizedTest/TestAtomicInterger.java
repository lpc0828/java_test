package com.lpc.synchronizedTest;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInterger implements Counter {
    private AtomicInteger num = new AtomicInteger(0);
    @Override
    public void increment() {
        num.incrementAndGet();
    }

    @Override
    public int getCount() {
        return num.get();
    }
}
