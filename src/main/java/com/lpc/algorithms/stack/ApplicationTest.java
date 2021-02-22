package com.lpc.algorithms.stack;

import com.lpc.algorithms.list.MyLinkedList;

import java.util.*;

public class ApplicationTest {

    /**
     * 判断公式是否合法 如 {[][]()}{[()]}[]()
     * 思路是：
     * 1. 左括号入栈，
     * 2. 右括号判断栈的左括号是否和右括号匹配，匹配弹出，否则判断失败
     * 3. 最后一定要记得 判断完毕的时候，栈为空
     * @param strBrackets
     */
    private static boolean judgeLegal(String strBrackets) {
        if (Objects.isNull(strBrackets)) {
            return false;
        }
        char[] brackets = strBrackets.toCharArray();
        MyStack<Character> myStack = new MyLinkedStack<>();
        for (char ch : brackets) {
            if (Objects.equals(ch, '(') || Objects.equals(ch, '[') || Objects.equals(ch, '{')) {
                myStack.push(ch);
                continue;
            }
            char top = myStack.pop();
            if (Objects.equals(ch, ')')) {
                if (!Objects.equals(top, '(')) {
                    return false;
                }
            } else if (Objects.equals(ch, ']')) {
                if (!Objects.equals(top, '[')) {
                    return false;
                }
            } else if (Objects.equals(ch, '}')) {
                if (!Objects.equals(top, '{')) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("符号:"+ ch +", 无法识别");
            }
        }
        return myStack.isEmpty();
    }


    private static String trimSpace(String str) {
        if (str == null || Objects.equals(str.trim(), "")) {
            return "";
        }
        String[] array = str.split("\\s");
        StringBuilder sb = new StringBuilder(str.length());
        for (String arr : array) {
            if (arr != null && !arr.trim().equals("")) {
                sb.append(arr.trim());
            }
        }
        return str.toString();
    }

    private static boolean isNumber(char n) {
        if (n >= 0 && n<= 9) {
            return true;
        }
        if (Objects.equals(n, '.')) {
            return true;
        }
        return false;
    }

    private static boolean isSymbol(char ch) {
        if (Objects.equals('+', ch) || Objects.equals('-', ch) || Objects.equals('*', ch) || Objects.equals('/', ch)) {
            return true;
        }
        return false;
    }

    private static boolean isStandard(String strFormula) {
        // 最后一个是不是等号
        if (strFormula.indexOf('=') != strFormula.length()-1){
            return false;
        }
        strFormula = strFormula.substring(0, strFormula.indexOf('=')-1);
        // 检查中间是不是全是数字（同时可以检查，是不是存在两个符号连在一起的情况）
        String[] arrs = strFormula.split("\\+|-|\\*|/");
        if (arrs == null || arrs.length == 0) {
            return false;
        }
        for (String arr : arrs) {
            if (arr == null || arr.trim().equals("")) {
                return false;
            }
            for (int i=0; i<arr.length(); i++) {
                if (!isNumber(arr.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }



    /**
     * 实现一个计算器，带括号，优先级 (1+2)+6/3+(-2*2)+(-2*6)
     * @param strFormula
     * @return
     */
    private static double caculator(String strFormula) {
        //去空格
        strFormula = trimSpace(strFormula);

        // 检查计算表达式是否合法
        if (!isStandard(strFormula)) {
            throw new IllegalArgumentException("表达式有误");
        }
        MyStack<Double> numStack = new MyLinkedStack<>();
        MyStack<Character> symbolStack = new MyLinkedStack<>();
        StringBuilder curNum = new StringBuilder(10);
        for (int i=0; i<strFormula.length()-1; i++) {
            char n = strFormula.charAt(i);
            if (isNumber(n)) {
                curNum.append(n);
            }
        }
        return 0;
    }


    public static int minKBitFlips(int[] A, int K) {
        int res = 0;
        Deque<Integer> que = new LinkedList<>();
        for (int i = 0; i < A.length; i++) {
            if (que.size() > 0 && i > que.peek() + K - 1) {
                que.removeFirst();
            }
            //1.本来是1，翻转奇数次变为0，所以需要再次翻转，放入队列
            //2.本来是0，翻转偶数次还是0，所以需要再次翻转，放入队列
            if (que.size() % 2 == A[i]) {
                if (i + K > A.length) return -1;
                que.add(i);
                res += 1;
            }
        }
        return res;
    }


    public static int minKBitFlips2(int[] A, int K) {
        int n = A.length;
        int[] diff = new int[n + 1];
        int ans = 0, revCnt = 0;
        for (int i = 0; i < n; ++i) {
            revCnt += diff[i];
            if ((A[i] + revCnt) % 2 == 0) {
                if (i + K > n) {
                    return -1;
                }
                ++ans;
                ++revCnt;
                --diff[i + K];
            }
        }
        return ans;
    }

    public static boolean isValid(String s) {
        if (Objects.isNull(s) || s.trim() == null) {
            return false;
        }
        char[] stack = new char[s.length()];
        int top = -1;
        for (char ch : s.toCharArray()) {
            if (Objects.equals('(', ch) || Objects.equals('[', ch) || Objects.equals('{', ch)) {
                stack[++top] = ch;
                continue;
            }
            if (top == -1) {
                return false;
            }
            char c = stack[top--];
            if (Objects.equals('(', c) && !Objects.equals(')', ch)) {
                return false;
            }
            if (Objects.equals('[', c) && !Objects.equals(']', ch)) {
                return false;
            }
            if (Objects.equals('{', c) && !Objects.equals('}', ch)) {
                return false;
            }
        }
        return top == -1;
    }


    public static void main(String[] args) {
        String[] strBrackets = new String[] {
                "{[][]()}{[()]}[]()",
                "{[][](])}{[()]}[]()",
                "{{{[{()}]}}"
        };
        for (String strBracket : strBrackets) {
            System.out.println("括号：" + strBracket + ", 判断结果为：" + ApplicationTest.judgeLegal(strBracket));
            System.out.println("括号：" + strBracket + ", 判断结果为：" + ApplicationTest.isValid(strBracket));
        }
        System.out.println("括号：" + "]" + ", 判断结果为：" + ApplicationTest.isValid("]"));

        int[] A = new int[] {0,0,0,1,0,1,1,0};
        System.out.println(minKBitFlips(A, 3));
    }
}
