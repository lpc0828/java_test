package com.lpc.synchronizedTest;

public class SynchronizedMain {

    public static void test(Counter counter) throws Exception {
        long start = System.currentTimeMillis();

        Thread t1 = new Thread( () -> {
                for (int i=0; i<10000000; i++) {
                    counter.increment();
                }
            }
        );
        t1.start();
        Thread t2 = new Thread( () -> {
            for (int i=0; i<10000000; i++) {
                counter.increment();
            }
        }
        );
        t2.start();
        Thread t3 = new Thread( () -> {
            for (int i=0; i<10000000; i++) {
                counter.increment();
            }
        }
        );
        t3.start();
        for (int i=0; i<10000000; i++) {
            counter.increment();
        }
        t1.join();
        t2.join();
        t3.join();
        System.out.println("cost:" + (System.currentTimeMillis()-start));
        System.out.println("total count:" + counter.getCount());
    }

    public static void main(String[] args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>> synchronized <<<<<<<<<<<<<<<<<");
        Counter counter = new TestSynchronized();
        test(counter);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> volatile <<<<<<<<<<<<<<<<<");
        counter = new TestVolatile();
        test(counter);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> atomicInteger <<<<<<<<<<<<<<<<<");
        counter = new TestAtomicInterger();
        test(counter);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> longAdder <<<<<<<<<<<<<<<<<");
        counter = new TestLongAdder();
        test(counter);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> reentrantLock <<<<<<<<<<<<<<<<<");
        counter = new TestReentrantLock();
        test(counter);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> fairReentrantLock <<<<<<<<<<<<<<<<<");
        counter = new TestFairReentrantLock();
        test(counter);
    }
}
