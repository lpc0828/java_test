package com.lpc.algorithms.queue;

/**
 * 队列的实现，有很多种，
 * 主要由顺序（单向）队列、循环（双向）队列
 * 其中 顺序则有 数组或单链表实现； 循环 由双向链表实现
 *
 * @param <E>
 */
public interface MyQueue<E> {

    /**
     * 入队到队尾, 如果没有空间，抛异常
     * @param e
     * @return
     */
    boolean add(E e);

    /**
     * 入队到队尾，如果没有空间，金范围插入失败
     * @param e
     * @return
     */
    boolean offer(E e);

    /**
     * 移除队首元素，如果当前队列为空，抛出异常
     * @return
     */
    E remove();

    /**
     * 移除队首元素，如果当前队列为空，返回空
     * @return
     */
    E poll();

    /**
     * 返回队头元素，如果为空，抛出异常，不移除元素
     * @return
     */
    E element();

    /**
     * 返回对头元素，如果为空，返回空，不移除元素
     * @return
     */
    E peek();

    boolean isEmpty();

    int size();

    void print();
}
