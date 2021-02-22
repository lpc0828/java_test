package com.lpc.algorithms.queue;

import com.lpc.algorithms.list.MyLinkedList;

import javax.net.ssl.SNIHostName;
import java.util.Objects;

public class MyLinkedQueue<E> implements MyQueue<E> {

    private final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;

    private QNode front; //队头
    private QNode rear;  //队尾

    private int size;    //队元素大小
    private int maxSize; //防止无限制的消耗内存空间，此队列允许的最大长度

    public MyLinkedQueue() {
        this.maxSize = DEFAULT_MAX_SIZE;
    }
    public MyLinkedQueue(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("illegal max capacity:" + maxCapacity);
        }
        this.maxSize = maxCapacity;
    }

    @Override
    public boolean add(E e) {
        if (isFull()) {
            throw new IllegalArgumentException("容量:" + size +", 队列已满");
        }
        QNode node = new QNode(e);
        if (isEmpty()) {
            rear = front = node;
            node.setNext(null);
        } else {
            if (size == 1) {
                front.next = rear;
            }
            rear.next = node;
            rear = node;
        }
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        }
        QNode node = new QNode(e);
        if (isEmpty()) {
            rear = front = node;
            node.setNext(null);
        } else {
            if (size == 1) {
                front.next = rear;
            }
            rear.next = node;
            rear = node;
        }
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new IllegalArgumentException("队列已空");
        }
        QNode node = front;
        front = front.getNext();
        size--;
        return (E) node.getE();
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        QNode node = front;
        front = front.getNext();
        size--;
        return (E) node.getE();
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new IllegalArgumentException("队列已空");
        }
        QNode node = front;
        return (E) node.getE();
    }

    @Override
    public E peek() {
        QNode node = front;
        return (E) node.getE();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private boolean isFull() {
        return this.size == this.maxSize;
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
        StringBuilder str = new StringBuilder(6*size());
        QNode node = front;
        while (node != null) {
            str.append("<-").append(Objects.toString(node.getE()));
            node = node.getNext();
        }
        System.out.println(str.toString().replaceFirst("<-", ""));
    }


    class QNode<E> {
        private E e;
        private QNode next;

        QNode(E e) {
            this.e = e;
            this.next = null;
        }

        public E getE() {
            return e;
        }

        public QNode getNext() {
            return next;
        }

        public void setNext(QNode next) {
            this.next = next;
        }
    }
}


