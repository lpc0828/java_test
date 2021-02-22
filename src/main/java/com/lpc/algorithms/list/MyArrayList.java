package com.lpc.algorithms.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MyArrayList<E> {

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_SIZE = 10;

    private int size;
    transient Object[] elementData;

    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public MyArrayList(int initialCapcity) {
        if (initialCapcity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else if (initialCapcity > 0) {
            this.elementData = new Object[initialCapcity];
        } else {
            throw new IllegalArgumentException("illegal initialCapcity:" + initialCapcity);
        }
    }

    public MyArrayList(Collection<? extends E> collection) {
        this.elementData = collection.toArray();
        size = this.elementData.length;
    }

    public void add(E element) {
        ensureCapcity(size+1);
        elementData[size++] = element;
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity > Integer.MAX_VALUE-8) {
            if (minCapacity < 0) {
                throw new OutOfMemoryError("");
            }
            newCapacity =  minCapacity > (Integer.MAX_VALUE-8) ? Integer.MAX_VALUE : (Integer.MAX_VALUE-8);
        }
        Object[] tmp = new Object[newCapacity];
        for (int i=0; i<oldCapacity; i++) {
            tmp[i] = elementData[i];
        }
        elementData = tmp;
    }

    private void ensureCapcity(int minCapcity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapcity = Math.max(DEFAULT_SIZE, minCapcity);
        }
        if (minCapcity >= elementData.length) {
            grow(minCapcity);
        }
    }

    private void checkIndexLeggal(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("illegal index:" + index);
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("illegal index:" + index);
        }
    }

    public void add(int index, E element) {
        checkIndexLeggal(index);
        ensureCapcity(size+1);
        for (int i=size; i>index; i--) {
            elementData[i] = elementData[i-1];
        }
        elementData[index] = element;
        size++;
    }


    public E remove(int index) {
        checkIndexLeggal(index);
        E oldValue = (E) elementData[index];
        for (int i= index; i<size-1; i++) {
            elementData[i] = elementData[i+1];
        }
        elementData[size-1] = null;
        size--;
        return oldValue;
    }

    public boolean remove(Object e) {
        int index = 0;
        while (index < size) {
            if (Objects.equals(e, elementData[index])) {
                remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public E set(int index, E e) {
        checkIndexLeggal(index);
        E oldValue = (E) elementData[index];
        elementData[index] = e;
        return oldValue;
    }

    public boolean contains(E e) {
        int index = 0;
        while (index < size) {
            if (Objects.equals(e, elementData[index])) {
                return true;
            }
            index++;
        }
        return false;
    }

    public int indexOf(E e) {
        int index = 0;
        while (index < size) {
            if (Objects.equals(e, elementData[index])) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    private void print() {
        StringBuilder str = new StringBuilder(elementData.length*4);
        str.append("[");
        for (int i=0; i<size; i++) {
            if (i == 0) {
                str.append(elementData[i]);
            } else {
                str.append(", ").append(elementData[i]);
            }
        }
        str.append("]");
        System.out.println(str.toString());
    }

    private void reverse() {
        for (int i=0; i<(size>>1); i++) {
            Object tmp = elementData[i];
            elementData[i] = elementData[size-1-i];
            elementData[size-1-i] = tmp;
        }
    }

    public static void main(String[] args) {
        MyArrayList<Integer> array = new MyArrayList<Integer>();
        for (int i=0; i<10; i++) {
            array.add(i);
        }
        array.print();
        array.add(array.size()-1, array.size());
        array.add(0, -1);
        array.add(1, -2);
        array.add(array.size(), array.size());
        array.print();
        System.out.println(array.remove(array.size()-1));
        System.out.println(array.remove(Integer.valueOf(-2)));
        array.print();
        array.reverse();
        array.print();

        ArrayList<Integer> a = new ArrayList<>();
        for (int i=0; i<10; i++) {
            a.add(i);
        }
    }
}
