package com.lpc.algorithms.stack;

import java.util.ArrayList;
import java.util.Objects;

public class MyLinkedStack<E> implements MyStack<E> {

    private Node top;
    private int size;

    @Override
    public MyStack<E> push(E e) {
        Node<E> node = new Node<>(e);
        node.setNext(top);
        top = node;
        size++;
        return this;
    }

    @Override
    public E pop() {
        Node node = top;
        if (top != null) {
            top = top.getNext();
            size--;
        }
        return node == null ? null : (E) node.getValue();
    }

    @Override
    public E peek() {
        Node node = top;
        return node == null ? null : (E) node.getValue();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void print() {
        StringBuilder str = new StringBuilder(6*this.size());
        Node node = top;
        for (int i=0; i<size; i++) {
            if (i != 0) {
                str.append("->");
            }
            str.append(Objects.toString(node.getValue()));
            node = node.getNext();
        }
        System.out.println(str.toString());
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyLinkedStack<>();
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

class Node<E> {
    private E value;
    private Node next;

    Node(E value) {
        this.value = value;
        this.next = null;
    }

    public E getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
