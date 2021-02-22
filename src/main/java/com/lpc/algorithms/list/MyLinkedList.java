package com.lpc.algorithms.list;

import java.util.Objects;

public class MyLinkedList<E> {

    private SNode head;

    private int size;

    public MyLinkedList() {
    }

    public boolean add(E e) {
        if (head == null) {
            return insertHead(e);
        }
        SNode node = head;
        while (node.next != null) {
            node = node.next;
        }
        SNode sNode = new SNode(e);
        node.next = sNode;
        size++;
        return true;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
    }

    public boolean add(int index, E e) {
        checkPositionIndex(index);
        if (head == null || index == 0) {
            return insertHead(e);
        }
        SNode sNode = new SNode(e);
        SNode fNode = findNode(index-1);
        if (fNode.next == null) {
            fNode.next = sNode;
        } else {
            sNode.next = fNode.next;
            fNode.next = sNode;
        }
        size++;
        return true;
    }

    public boolean insertHead(E e) {
        SNode sNode = new SNode(e);
        sNode.next = head;
        head = sNode;
        size++;
        return true;
    }

    public E removeHead() {
        if (head == null) {
            return null;
        }
        SNode tmp = head;
        head = head.next;
        size--;
        return (E) tmp.getValue();
    }

    private SNode findNode(int index) {
        checkPositionIndex(index);
        int i=0;
        SNode node = head;
        while (i++ < index) {
            node = node.next;
        }
        return node;
    }

    public E remove(int index) {
        checkPositionIndex(index);
        if (head == null) {
            return null;
        }
        if (index == 0) {
            return removeHead();
        }
        if (index == size) {

        }
        int i=0;
        SNode sNode = findNode(index-1);
        SNode node = sNode.next;
        if (sNode.next.next != null) {
            sNode.next = sNode.next.next;
        } else {
            sNode.next = null;
        }
        size--;
        return (E) node.getValue();
    }

    public E remove(E e) {
        if (head == null) {
            return null;
        }
        if (Objects.equals(head.getValue(), e)) {
            return removeHead();
        }
        SNode node = head;
        while (node.next != null) {
            if (Objects.equals(node.next.getValue(), e)) {
                break;
            }
            node = node.next;
        }
        SNode sNode = node.next;
        if (node.next.next == null) {
            node.next = null;
        } else {
            node.next = node.next.next;
        }
        size--;
        return (E) sNode.getValue();
    }

    public boolean contains(E e) {
        SNode sNode = head;
        while (sNode != null) {
            if (Objects.equals(sNode.getValue(), e)) {
                return true;
            }
            sNode = sNode.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int indexOf(E e) {
        int index = 0;
        SNode node = head;
        while (node != null) {
            if (Objects.equals(node.getValue(), e)) {
                return index;
            }
            node = node.next;
            index++;
        }
        return -1;
    }

    public void print() {
        SNode sNode = head;
        StringBuilder str = new StringBuilder(size*6);
        while (sNode != null) {
            str.append(sNode.getValue());
            if (sNode.next != null) {
                str.append("->");
            }
            sNode = sNode.next;
        }
        System.out.println(str.toString());
    }

    public void reverse() {
        if (head == null || head.next == null) {
            return;
        }
        SNode sourceHead = head;
        SNode targetHead = null;
        while (sourceHead != null) {
            SNode nextNode = sourceHead.next;
            sourceHead.next = targetHead;
            targetHead = sourceHead;
            sourceHead = nextNode;
        }
        head = targetHead;
    }


    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i=0; i<10; i++) {
            list.add(i);
        }
        list.print();
        list.add(0, -1);
        list.print();
        list.add(-2);
        list.print();
        list.add(list.size()/2, -3);
        list.print();
        list.remove(list.size()/2);
        list.print();
        list.remove(list.size()-1);
        list.print();
        list.remove(0);
        list.print();
        list.remove(Integer.valueOf(5));
        list.print();
        list.insertHead(-11);
        list.print();
        System.out.println(list.indexOf(Integer.valueOf(4)));
        System.out.println(list.contains(Integer.valueOf(4)));
        System.out.println(list.contains(Integer.valueOf(44)));
        list.reverse();
        list.print();
    }
}

class SNode<E> {
    E value;
    SNode next;

    SNode(E value) {
        this.value = value;
        this.next = null;
    }

    public E getValue() {
        return this.value;
    }
}
