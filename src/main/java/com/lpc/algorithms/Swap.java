package com.lpc.algorithms;

public class Swap {

    public static void main(String[] args) {
        int a=123;
        int b=34;
        int tmp = a^b;
        a = tmp ^ a;
        b = tmp ^ b;
        System.out.println("a=" + a );
    }
}
