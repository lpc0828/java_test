package com.lpc.algorithms.stack;

import java.util.Stack;

public class MinStack {

    Stack<Long> stack;
    long min;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack();
        min = Long.MAX_VALUE;
    }

    public void push(int x) {
        if (stack.isEmpty()) {
            min = x;
        }
        stack.push(x-min);
        if (x<min) {
            min = x;
        }
    }

    public void pop() {
        if (stack.peek() < 0) {
            min = min - stack.peek();
        }
        stack.pop();
        if (stack.isEmpty()) {
            min = Long.MAX_VALUE;
        }
    }

    public int top() {
        if (stack.peek() < 0) {
            return (int) min;
        }
        return (int) (min + stack.peek());
    }

    public int getMin() {
        return (int) min;
    }

    public static void main(String[] args) {
        MinStack ms = new MinStack();
        ms.push(2147483646);
        ms.push(2147483646);
        ms.push(2147483647);
        System.out.println(ms.top());
        ms.pop();
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
        ms.pop();
        ms.push(2147483647);
        System.out.println(ms.top());
        System.out.println(ms.getMin());
        ms.push(-2147483648);
        System.out.println(ms.top());
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());

    }
}
