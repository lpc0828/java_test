package com.lpc.algorithms.list;

import java.util.Objects;

public class MyDoubleLinkedList<E> {

    private DNode head;
    private DNode tail;

    private int size;

    public MyDoubleLinkedList() {}

    public boolean insertHead(E e) {
        DNode dNode = new DNode(e);
        if (head == null) {
            head = tail = dNode;
        } else {
            dNode.next = head;
            head.pre = dNode;
        }
        size++;
        return true;
    }

    public E removeHead() {
        if (head == null) {
            return null;
        }
        DNode dNode = head;
        if (head == tail) {
            head = tail = null;
            size--;
            return (E) dNode.getValue();
        }
        head = head.next;
        head.pre = null;
        size--;
        return (E) dNode.getValue();
    }

    public boolean insertTail(E e) {
        DNode dNode = new DNode(e);
        if (tail == null) {
            head = tail = dNode;
            size++;
            return true;
        }
        dNode.pre = tail;
        tail.next = dNode;
        tail = dNode;
        size++;
        return true;
    }

    public E removeTail() {
        if (tail == null) {
            return null;
        }
        DNode dNode = tail;
        tail = tail.pre;
        tail.next = null;
        size--;
        return (E) dNode.getValue();
    }

    public int indexOf(E e) {
        DNode dNode = head;
        int index = 0;
        while (dNode != null) {
            if (Objects.equals(dNode.getValue(), e)) {
                return index;
            }
            dNode = dNode.next;
            index++;
        }
        return -1;
    }

    private DNode findNode(int index) {
        DNode dNode = head;
        int i = 0;
        while (dNode != null) {
            if (i == index) {
                break;
            }
            dNode = dNode.next;
            i++;
        }
        return dNode;
    }

    private void checkPositionIndex(int index) {
        if (index<0 || index>=size){
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
    }

    public boolean add(int index, E e) {
        checkPositionIndex(index);
        DNode dNode = findNode(index);
        DNode node = new DNode(e);
        if (dNode == head) {
            node.next = head;
            head.pre = node;
            head = node;
        } else if (dNode == tail) {
            tail.next = node;
            node.pre = tail;
            tail = node;
        } else {
            node.pre = dNode.pre;
            dNode.pre.next = node;
            node.next = dNode;
            dNode.pre = node;
        }
        size++;
        return true;
    }

    public boolean add(E e) {
        return insertTail(e);
    }


    public boolean remove(E e) {
        DNode dNode = head;
        DNode node = null;
        while (dNode != null) {
            if (Objects.equals(dNode.getValue(), e)) {
                node = dNode;
                dNode.pre.next = dNode.next;
                if (dNode.next != null) {
                    dNode.next.pre = dNode.pre;
                }
                size--;
                return true;
            }
            dNode = dNode.next;
        }
        return false;
    }

    public E remove(int index) {
        checkPositionIndex(index);
        DNode dNode = findNode(index);
        if (dNode == head) {
            removeHead();
        } else if (dNode == tail) {
            removeTail();
        } else {
            dNode.next.pre = dNode.pre;
            dNode.pre.next = dNode.next;
            size--;
        }
        return (E) dNode.getValue();
    }

    public void print() {
        DNode dNode = head;
        StringBuilder str = new StringBuilder(size*6);
        while (dNode != null) {
            str.append(dNode.getValue());
            if (dNode.next != null) {
                str.append("->");
            }
            dNode = dNode.next;
        }
        System.out.println(str.toString());
    }

    public int size() {
        return size;
    }

    public void reverse() {
        if (head == tail) {
            return;
        }
        DNode targetHead = null;
        DNode targetTail = null;
        DNode sourceHead = head;
        while (sourceHead != null) {
            DNode nextSource = sourceHead.next;
            sourceHead.next = targetHead;
            if (targetHead != null) {
                targetHead.pre = sourceHead;
            } else {
                targetTail = sourceHead;
            }
            targetHead = sourceHead;
            sourceHead = nextSource;
        }
        head = targetHead;
        tail = targetTail;
    }

    public static void main(String[] args) {
        MyDoubleLinkedList list = new MyDoubleLinkedList();
        for (int i=0; i<10; i++) {
            list.add(i);
        }
        System.out.println(list.size());
        list.print();
        list.add(0, -1);
        System.out.println(list.size());
        list.print();
        list.remove(0);
        System.out.println(list.size());
        list.print();
        list.add(list.size()-1, -11);
        System.out.println(list.size());
        list.print();
        list.remove(list.size()-1);
        System.out.println(list.size());
        list.print();
        list.add(list.size()/2, -55);
        System.out.println(list.size());
        list.print();
        list.remove(list.size()/2);
        System.out.println(list.size());
        list.print();

        list.insertHead(-100);
        System.out.println(list.size());
        list.print();

        list.remove(Integer.valueOf(-100));
        System.out.println(list.size());
        list.print();

        list.insertTail(200);
        System.out.println(list.size());
        list.print();

        list.remove(Integer.valueOf(200));
        System.out.println(list.size());
        list.print();

        System.out.println(list.indexOf(8));
        System.out.println(list.indexOf(200));
        list.reverse();
        list.print();
    }

}

class DNode<E> {
    DNode pre;
    DNode next;
    E value;

    DNode(E value) {
        this.value = value;
        this.pre = null;
        this.next = null;
    }

    public E getValue() {
        return this.value;
    }
}
