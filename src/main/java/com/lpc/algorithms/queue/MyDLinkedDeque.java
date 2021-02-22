package com.lpc.algorithms.queue;

import com.lpc.algorithms.list.MyDoubleLinkedList;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.NoSuchElementException;
import java.util.Objects;

public class MyDLinkedDeque<E> implements MyDeque<E> {

    private int size;     // 当前队列长度
    private DNode rear;   // 队尾指针
    private DNode front;  // 队首指针

    private int MaxCapacity = Integer.MAX_VALUE;

    public MyDLinkedDeque(int maxCapacity) {
        this.MaxCapacity = maxCapacity;
    }
    public MyDLinkedDeque() {
    }

    private boolean isFull() {
        return size >= MaxCapacity;
    }

    @Override
    public void addFirst(E e) {
        if (isFull()) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        offerFirst(e);
    }

    @Override
    public void addLast(E e) {
        if (isFull()) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        if (isFull()) {
            return false;
        }
        DNode node = new DNode(e);
        node.next = front;
        if (isEmpty()) {
            front = rear = node;
        } else {
            front.pre = node;
            front = node;
        }
        size++;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull()) {
            return false;
        }
        DNode node = new DNode(e);
        if (isEmpty()) {
            front = rear = node;
        } else {
            node.pre = rear;
            rear.next = node;
            rear = node;
        }
        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("当前队列为空");
        }
        return pollFirst();
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("当前队列为空");
        }
        return pollLast();
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        DNode node = front;
        front = front.next;
        if (front != null) {
            front.pre = null;
        } else {
            rear = null;
        }
        size--;
        return (E) node;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        DNode node = rear;
        rear = rear.pre;
        if (rear != null) {
            rear.next = null;
        } else {
            front = null;
        }
        size--;
        return (E) node;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空");
        }
        return peekFirst();
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列为空");
        }
        return peekLast();
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return front.getValue();
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return rear.getValue();
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
        DNode node = front;
        while (node != null) {
            if (Objects.equals(node.getValue(), o)) {
                return true;
            }
            node = node.getNext();
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
        return pollFirst();
    }

    @Override
    public Object poll() {
        return pollFirst();
    }

    @Override
    public Object element() {
        return removeFirst();
    }

    @Override
    public Object peek() {
        return peekFirst();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void print() {
        if (isEmpty()) {
            System.out.println("");
            return;
        }
        StringBuilder str = new StringBuilder(5*size);
        DNode node = front;
        while (node != null) {
            if (node == front) {
                str.append(Objects.toString(node.getValue()));
            } else {
                str.append(" < ").append(Objects.toString(node.getValue()));
            }
            node = node.getNext();
        }
        System.out.println(str.toString());
    }

    class DNode {
        private E value;
        private DNode next;
        private DNode pre;

        DNode(E e) {
            this.value = e;
            this.next = null;
            this.pre = null;
        }

        public E getValue() {
            return value;
        }

        public DNode getNext() {
            return next;
        }

        public DNode getPre() {
            return pre;
        }

        public void setNext(DNode next) {
            this.next = next;
        }

        public void setPre(DNode pre) {
            this.pre = pre;
        }
    }
}
