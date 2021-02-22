package com.lpc.synchronizedTest;

import java.util.concurrent.atomic.LongAdder;

public class TestLongAdder implements Counter {

    private LongAdder num = new LongAdder();

    @Override
    public void increment() {
        num.increment();
    }

    @Override
    public int getCount() {
        return num.intValue();
    }
}
