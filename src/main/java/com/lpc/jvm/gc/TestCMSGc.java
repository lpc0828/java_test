package com.lpc.jvm.gc;

public class TestCMSGc {
    public static void main(String[] args) {
        int OneMB = 1024*1024;

        byte[] myAlloc1 = new byte[4*OneMB];

        System.out.println("1111111");

        byte[] myAlloc2 = new byte[4*OneMB];

        System.out.println("2222222");

        byte[] myAlloc3 = new byte[2*OneMB];

        System.out.println("3333333");

        byte[] myAlloc4 = new byte[4*OneMB];

        System.out.println("4444444");

//        byte[] myAlloc5 = new byte[4*OneMB];
//
//        System.out.println("5555555");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
