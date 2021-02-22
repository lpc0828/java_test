package com.lpc.algorithms.queue;

public interface MyDeque<E> extends MyQueue {

    /**
     * 加到对头，会抛异常
     * @param e
     */
    void addFirst(E e);

    /**
     * 加入队尾，会抛异常
     * @param e
     */
    void addLast(E e);

    /**
     * 加入到队头，队列满时，不抛异常
     * @param e
     * @return
     */
    boolean offerFirst(E e);

    /**
     * 加入队尾，队列满时，不抛异常
     * @param e
     * @return
     */
    boolean offerLast(E e);

    /**
     * 移除第一个元素，抛异常
     * @return
     */
    E removeFirst();

    /**
     * 移除最后一个元素，抛异常
     * @return
     */
    E removeLast();

    /**
     * 返回并移除队首，队列为空时，返回空
     * @return
     */
    E pollFirst();

    /**
     * 返回并移除队尾，队列为空时，返回空
     * @return
     */
    E pollLast();

    /**
     * 返回不移除队首，队列为空时，抛异常
     * @return
     */
    E getFirst();

    /**
     * 返回不移除队尾，队列为空时，抛异常
     * @return
     */
    E getLast();

    /**
     * 返回不移除队首，队列为空时，返回空
     * @return
     */
    E peekFirst();

    /**
     * 返回不移除队尾，队列为空时，返回空
     * @return
     */
    E peekLast();

    /**
     * 入队尾，如果空间不足，抛出异常
     * @param e
     */
    void push(E e);

    /**
     * 队首弹出，如果队列为空，抛出异常
     * @param
     */
    E pop();

    /**
     * 从队首开始判断元素是否存在
     * @param o
     * @return
     */
    boolean contains(Object o);

    void print();
}
