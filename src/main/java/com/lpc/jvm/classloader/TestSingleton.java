package com.lpc.jvm.classloader;

public class TestSingleton {

    private static int counter_0 = 0 ;

    private static TestSingleton instance = new TestSingleton();

    private TestSingleton () {
        counter_0++;
        counter_1++;
        System.out.println("couter_0=" + TestSingleton.counter_0 + "， couter_1=" + TestSingleton.counter_1);
    }

    public static TestSingleton getInstance() {
        return instance;
    }

    private static int counter_1 = 0;

    public static void main(String[] args) {
        TestSingleton testSingleton = TestSingleton.getInstance();
        System.out.println("couter_0=" + TestSingleton.counter_0);
        System.out.println("couter_1=" + TestSingleton.counter_1);
    }
}
