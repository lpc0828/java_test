package com.lpc.concurrent.threads;

public class TestThreadInterrupt {


    private static void printThreadInfo(String description, Thread t) {
        if (t == null) {
            t = Thread.currentThread();
        }
        StringBuilder sb = new StringBuilder(1024);

        sb.append(description).append("\n")
                .append("线程信息 thread-ID:").append(t.getId())
                .append(" thread-group:").append(t.getThreadGroup())
                .append(" thread-name:").append(t.getName())
                .append(" thread-priority:").append(t.getPriority())
                .append(" thread-state:").append(t.getState()).append("\n")
                .append(" isInterrupted:").append(t.isInterrupted())
                .append(" isAlive:").append(t.isAlive())
                .append(" isDaemon:").append(t.isDaemon());
        System.out.println(sb.toString());
    }

    private static void test1() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printThreadInfo(">>>>>>>>>>>>>>>>>run first<<<<<<<<<<<<<<<<<<", null);
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("线程正在正常运行.......");
                }
                printThreadInfo(">>>>>>>>>>>>>>>>>end before<<<<<<<<<<<<<<<<<<", null);
                System.out.println("线程即将结束");
            }
        });

        printThreadInfo(">>>>>>>>>>>>>>>>>before start<<<<<<<<<<<<<<<<<<", t1);
        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        printThreadInfo(">>>>>>>>>>>>>>>>>after end<<<<<<<<<<<<<<<<<<", t1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printThreadInfo(">>>>>>>>>>>>>>>>>end of all<<<<<<<<<<<<<<<<<<", t1);
    }

    public static void test2() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printThreadInfo(">>>>>>>>>>>>>>>>>run first<<<<<<<<<<<<<<<<<<", null);
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println("线程正在正常运行 sleep begin .......");
                        Thread.sleep(2000);
                        System.out.println("线程正在正常运行 sleep end .......");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 此时已被唤醒，需要再次 调用 Thread.interrupt()，否则将进行
                    }
                }
                printThreadInfo(">>>>>>>>>>>>>>>>>end before<<<<<<<<<<<<<<<<<<", null);
                System.out.println("线程即将结束");
            }
        });

        printThreadInfo(">>>>>>>>>>>>>>>>>before start<<<<<<<<<<<<<<<<<<", t1);
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        printThreadInfo(">>>>>>>>>>>>>>>>>after end<<<<<<<<<<<<<<<<<<", t1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printThreadInfo(">>>>>>>>>>>>>>>>>end of all<<<<<<<<<<<<<<<<<<", t1);
    }


    public static void main(String[] args) {
        //test1();
        test2();
    }
}
