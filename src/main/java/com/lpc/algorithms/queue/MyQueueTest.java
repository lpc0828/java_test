package com.lpc.algorithms.queue;

public class MyQueueTest {


    private static void testQueue(MyQueue myQueue) {
        for (int i=1; i<100; i++) {
            myQueue.add(i);
        }
        myQueue.print();
        System.out.println(myQueue.element());
        System.out.println(myQueue.peek());
        myQueue.remove();
        myQueue.print();
        myQueue.add(1);
        myQueue.print();
        myQueue.poll();
        myQueue.print();
        myQueue.offer(1);
        myQueue.print();
        while (!myQueue.isEmpty()) {
            myQueue.poll();
            myQueue.print();
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyArrayQueue<>(8);
        testQueue(myQueue);
        System.out.println("===========================================================");
        MyQueue<Integer> myLinkedQueue = new MyLinkedQueue<>(100);
        testQueue(myLinkedQueue);
    }
}
