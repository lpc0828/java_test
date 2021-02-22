package com.lpc.algorithms.queue;


import java.util.Objects;


public class MyArrayQueue<E> implements MyQueue<E> {

    private Object[] data; // 数据元素
    private int front; // 指向队头
    private int rear; // 指向队尾

    private int MaxSize; // 队列最大容量

    private static final int DefaultSize = 16;

    public MyArrayQueue() {
        // 默认初始化长度16
        this(DefaultSize);
    }

    private void allocateData(int size) {
        data = new Object[size];
    }

    public MyArrayQueue(int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE);
    }

    public MyArrayQueue(int initialCapacity, int maxSize) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity:" + initialCapacity);
        }
        if (initialCapacity > maxSize) {
            throw new IllegalArgumentException("Illegal initial capacity:" + initialCapacity + " large than maxSize:" + maxSize);
        }
        this.MaxSize = maxSize;
        allocateData(initialCapacity);
    }

    private boolean isFull() {
        return rear == data.length-1;
    }

    private void formatData() {
        for (int i = 0; i<rear-front; i++) {
            data[i] = data[i+front];
        }
        for (int i= rear-front; i<rear-1; i++) {
            data[i] = null;
        }
    }

    private void resize() {
        // 判断是否还有浪费空间，需要移动数据
        if (front > 0) {
            formatData();
            return;
        }
        // 如果没有空间浪费，申请新的资源
        int oldCapacity = data.length;
        if (oldCapacity >= MaxSize) {
            throw new RuntimeException("当前队列已满，达到最大容量:" + MaxSize + "，无法继续扩容");
        }
        int newCapacity = oldCapacity + (oldCapacity>>1);
        if (newCapacity < 0 ) {
            newCapacity = MaxSize;
        } else if (newCapacity > MaxSize) {
            newCapacity = MaxSize;
        } else if (newCapacity == oldCapacity) {
            newCapacity = DefaultSize;
        }
        Object[] tmp = new Object[newCapacity];
        for (int i = front; i<rear-front; i++) {
            tmp[i] = data[i];
        }
        data = tmp;
    }

    private boolean addLast(E e) {
        if (isFull()) {
            resize();
        }
        data[rear++] = e;
        return true;
    }

    @Override
    public boolean add(E e) {
        return addLast(e);
    }

    @Override
    public boolean offer(E e) {
        try {
            if (isFull()) {
                resize();
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return false;
        }
        data[rear++] = e;
        return true;
    }

    public boolean isEmpty() {
        if (rear == front) {
            return true;
        }
        return false;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new RuntimeException("当前队列为空，无数据");
        }
        return (E) data[front++];
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return (E) data[front++];
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new RuntimeException("当前队列为空，无数据");
        }
        return (E) data[front];
    }

    @Override
    public E peek() {
        return (E) data[front];
    }

    @Override
    public int size() {
        return rear-front;
    }

    @Override
    public void print() {
        if (isEmpty()) {
            System.out.println("");
        }
        StringBuilder str = new StringBuilder(6*size());
        for (int i=front; i<rear; i++) {
            if (i != front) {
                str.append("<-");
            }
            str.append(Objects.toString(data[i]));
        }
        System.out.println(str.toString());
    }
}
