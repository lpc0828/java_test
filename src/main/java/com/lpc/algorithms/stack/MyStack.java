package com.lpc.algorithms.stack;

public interface MyStack<E> {

    MyStack<E> push(E e);

    E pop();

    E peek();

    int size();

    boolean isEmpty();

    void print();
}
