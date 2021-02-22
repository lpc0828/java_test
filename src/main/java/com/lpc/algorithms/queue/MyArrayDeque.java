package com.lpc.algorithms.queue;

import com.lpc.algorithms.stack.MinStack;

import java.util.NoSuchElementException;
import java.util.Objects;

public class MyArrayDeque<E> implements MyDeque<E> {

    private transient Object[] items; //存放数据的数组

    private int front; // 队首
    private int rear;  //队尾
    private int size;  //队元素个数

    private static final int MIN_INITIAL_CAPACITY = 8;

    public MyArrayDeque(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("illegal capacity:" + capacity);
        }
        items = new Object[capacity];
    }

    private boolean isFull() {
        if ( (rear+1)%items.length == front) {
            return true;
        }
        return false;
    }

    private void resize(int newCapacity) {
        if (newCapacity == items.length) {
            return;
        }
        Object[] tmp = new Object[newCapacity];
        if (front < rear) {
            for (int i=front; i<rear; i++) {
                tmp[i-front] = items[i];
            }
            front = 0;
            rear = front+size;
        } else if (front > rear){
            for (int i=front; i<items.length; i++) {
                tmp[i-front] = items[i];
            }
            for (int i=0; i<rear; i++) {
                tmp[items.length-front+i] = items[i];
            }
            front = 0;
            rear = front+size;
        }
        items = tmp;
    }

    private int caculateCapacity(int oldCapacity, boolean throwEx) {
        if (oldCapacity >= Integer.MAX_VALUE) {
            if (throwEx) {
                throw new OutOfMemoryError("容量已满");
            }
            return oldCapacity;
        }
        int newCapacity = oldCapacity + oldCapacity>>1;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        } else if (newCapacity == oldCapacity) {
            newCapacity = MIN_INITIAL_CAPACITY;
        }
        return newCapacity;
    }

    @Override
    public void addFirst(E e) {
        if (isFull()) {
            resize(caculateCapacity(items.length, true));
        }
        front = (front-1+items.length)%items.length;
        if (front < 0) {
            front = size+front;
        }
        items[front] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (isFull()) {
            resize(caculateCapacity(items.length, true));
        }
        items[rear] = e;
        rear = (rear+1)%items.length;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        if (isFull()) {
            resize(caculateCapacity(items.length, false));
        }
        if (isFull()) {
            return false;
        }
        front = (front-1+items.length)%items.length;
        items[front] = e;
        size++;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull()) {
            resize(caculateCapacity(items.length, true));
        }
        if (isFull()) {
            return false;
        }
        items[rear] = e;
        rear = (rear+1)%items.length;
        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("队列已空");
        }
        E node = (E) items[front];
        items[front] = null;
        front = (front+1)%items.length;
        size--;
        return node;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("队列已空");
        }
        rear = (rear-1+items.length)%items.length;
        E node = (E) items[rear];
        items[rear] = null;
        size--;
        return node;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        E node = (E) items[front];
        items[front] = null;
        front = (front+1)%items.length;
        size--;
        return node;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear-1+items.length)%items.length;
        E node = (E) items[rear];
        items[rear] = null;
        size--;
        return node;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列已空");
        }
        return (E) items[front];
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列已空");
        }
        return (E) items[(rear-1+items.length)%items.length];
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return (E) items[front];
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return (E) items[(rear-1+items.length)%items.length];
    }

    @Override
    public void push(E e) {
        addLast(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        for (int i=front; i<(front+size); i++) {
            if (Objects.equals(o, items[i%items.length])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean add(Object o) {
        return offerLast((E) o);
    }

    @Override
    public boolean offer(Object o) {
        return offerLast((E) o);
    }

    @Override
    public Object remove() {
        return removeFirst();
    }

    @Override
    public Object poll() {
        return pollFirst();
    }

    @Override
    public Object element() {
        return peekFirst();
    }

    @Override
    public Object peek() {
        return peekFirst();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void print() {
        if (isEmpty()) {
            System.out.println("");
        }
        StringBuilder str = new StringBuilder(6*size);
        for (int i=front; i<(front+size); i++) {
            if (i != front) {
                str.append(" < ");
            }
            str.append(Objects.toString(items[i%items.length]));
        }
        System.out.println(str.toString());
    }
}
