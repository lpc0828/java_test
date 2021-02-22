package com.lpc.algorithms.stack;


import java.util.Objects;

public class MyArrayStack<E> implements MyStack<E> {

    private final Object[] DEFAULT_TABLE = {};
    private final int DEFAULT_SIZE = 10;

    private Object[] table;
    private int size;

    public MyArrayStack() {
        table = DEFAULT_TABLE;
    }

    public MyArrayStack(int initialCapacity) {
        if (initialCapacity<0) {
            throw new ArrayIndexOutOfBoundsException("Size:"+ initialCapacity);
        }
        table = new Object[initialCapacity];
    }

    private void ensureCapacity(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index:"+ index);
        }
        if (table == DEFAULT_TABLE) {
            table = new Object[DEFAULT_SIZE];
        }
        if (size >= table.length) {
            int newCapacity = size*2;
            if (newCapacity < 0) {
                newCapacity = Integer.MAX_VALUE;
            }
            resize(newCapacity);
        } else if (size>0 && table.length>DEFAULT_SIZE && size < table.length/3){
            resize(table.length/2);
        }
    }

    private void resize(int newSize) {
        Object[] tmp = new Object[newSize];
        for (int i=0; i<size; i++) {
            tmp[i] = table[i];
        }
        table = tmp;
    }

    @Override
    public MyStack<E> push(E e) {
        ensureCapacity(size+1);
        table[size++] = e;
        return this;
    }

    @Override
    public E pop() {
        ensureCapacity(size-1);
        E e = (E) table[--size];
        table[size] = null;
        return e;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return (E) table[size-1];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size>0 ? false : true;
    }

    @Override
    public void print() {
        StringBuilder str = new StringBuilder(size*6);
        for (int i=size-1; i>=0; i--) {
            if (i == size-1) {
                str.append(Objects.toString(table[i]));
            } else {
                str.append("->").append(Objects.toString(table[i]));
            }
        }
        System.out.println(str.toString());
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyArrayStack<Integer>(2);
        for (int i=0; i<10; i++) {
            myStack.push(i);
        }
        myStack.print();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.pop();
        myStack.print();
    }
}
