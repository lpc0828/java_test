package com.lpc.concurrent.singleton;

import com.lpc.jvm.classloader.TestSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * happens-before 原则 中 有一条 管程锁定规则 按理来说，懒汉 双检测 方法，instance 不需要加volatile，我们测试下看看
 * 管程锁定规则：不论是单线程，还是多线程环境，对于同一个锁来说，一个线程对这个锁解锁之后，另外一个线程获取了这个锁，
 * 那么它就能看到前一个线程的操作结果。
 */
public class SingletonTest {
    public static SingletonTest instance = null;
    // 双层检测
    public static SingletonTest getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SingletonTest.class) {
            if (instance == null) {
                instance = new SingletonTest();
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        for (int n=0; n<10000000; n++) {
            ConcurrentHashMap<Integer, Integer> count = new ConcurrentHashMap<>();
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                Future future = executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        count.put(SingletonTest.getInstance().hashCode(), finalI);
                    }
                });
                futures.add(future);
            }
            for (Future future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (count.size() >= 2) {
                System.out.println("n = " + n + ", count:" + count.toString());
            }
            SingletonTest.instance = null;
        }
        System.out.println(" game over ");
    }


}
