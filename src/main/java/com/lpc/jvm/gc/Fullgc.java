package com.lpc.jvm.gc;

public class Fullgc {

    public static void main(String[] args) throws InterruptedException {
        System.gc();
        Runtime.getRuntime().runFinalization();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Thread.sleep(10000);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
