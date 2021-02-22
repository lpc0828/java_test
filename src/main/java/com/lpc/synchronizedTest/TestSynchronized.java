package com.lpc.synchronizedTest;

public class TestSynchronized implements Counter {

    private int num;

    public void increment() {
        synchronized (this) {
            num++;
        }
    }

    public int getCount() {
        return num;
    }
}
