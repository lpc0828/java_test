package com.lpc.algorithms.queue;

import java.util.HashMap;
import java.util.Map;

public class MyDequeTest {

    public static void test(MyDeque myDeque) {
        System.out.println("===================== push ===================");
        for (int i=1; i<=6; i++) {
            myDeque.push(i);
            myDeque.print();
        }
        System.out.println("===================== pop ===================");
        for (int i=1; i<=6; i++) {
            myDeque.pop();
            myDeque.print();
        }
        System.out.println("===================== offer ===================");
        for (int i=1; i<=6; i++) {
            myDeque.offer(i);
            myDeque.print();
        }
        System.out.println("===================== poll ===================");
        for (int i=1; i<=6; i++) {
            myDeque.poll();
            myDeque.print();
        }
        System.out.println("===================== addLast ===================");
        for (int i=1; i<=6; i++) {
            myDeque.addLast(i);
            myDeque.print();
        }
        System.out.println("===================== removeLast ===================");
        for (int i=1; i<=6; i++) {
            myDeque.removeLast();
            myDeque.print();
        }
        System.out.println("===================== addFirst ===================");
        for (int i=1; i<=6; i++) {
            myDeque.addFirst(i);
            myDeque.print();
        }
        System.out.println("===================== peekFirst ===================");
        for (int i=1; i<=6; i++) {
            myDeque.peekFirst();
            myDeque.print();
        }
        System.out.println("===================== getFirst ===================");
        for (int i=1; i<=6; i++) {
            myDeque.getFirst();
            myDeque.print();
        }
        System.out.println("contains 3 = " + myDeque.contains(3));
        System.out.println("contains 7 = " + myDeque.contains(7));
        System.out.println("===================== removeFirst ===================");
        for (int i=1; i<=6; i++) {
            myDeque.removeFirst();
            myDeque.print();
        }
        System.out.println("===================== test circle ====================");
        myDeque.push(11);
        myDeque.push(22);
        myDeque.push(33);
        myDeque.push(44);
        myDeque.push(55);
        myDeque.push(66);
        myDeque.print();
        myDeque.pop();
        myDeque.push(11);
        myDeque.print();
        System.out.println("===================== test ends ===================");
    }


    public static void main(String[] args) {
        MyDeque<Integer> myDeque = new MyArrayDeque<>(7);
        System.out.println(">>>>>>>>>>>>>>>>>>>>> MyArrayDeque Tests <<<<<<<<<<<<<<<<<<<<<<");
        test(myDeque);
        System.out.println(">>>>>>>>>>>>>>>>>>>>> MyDLinkedDeque Tests <<<<<<<<<<<<<<<<<<<<<<");
        myDeque = new MyDLinkedDeque<>(7);
        test(myDeque);

    }
}
