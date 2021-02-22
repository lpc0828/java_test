package com.lpc.synchronizedTest;

public class TestVolatile implements Counter {

    private volatile int num;

    @Override
    public void increment() {
        num++;
    }

    @Override
    public int getCount() {
        return num;
    }
}
