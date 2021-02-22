package com.lpc.algorithms.thoughts;

/**
 * 递归思想： 递(分散开) 归 (回归，回溯)
 * 优点： 代码整洁，可读性高
 * 缺点： 注意栈是否回收，时间复杂度
 * 方法：
 *  1. 找到计算公式
 *  2. 确定终止条件
 * 递归很耗性能， 如何优化
 * (1). 非递归：所有递归，都可以转化成非递归的方法实现。
 * (2). 加入缓存：分析是否有大量重复数据，将中间重复出现的数据缓存起来。
 * (3). 尾递归：什么是尾递归？尾递归就是函数一定出现在末尾，没有任何其他的操作了。因为我们编译器在编译代码时，如果发现函数末尾已经没有操作了，
 * 这时候就不会创建一个新的栈，而且覆盖到前面去。倒着算，不需要再回溯了，因为我们每次会把中间结果带下来。
 */
public class RecursionTest {

    private static int[] cache;

    /**
     * 斐波那契数 1，1，2，3，5，8，13，21，34......
     * 公式： f(n) = f(n-1) + f(n-2)
     * 终止条件: n<= 2, f(n) = 1
     * 递归可以通过画图,得出执行过程其实是个二叉树，求得时间复杂度和空间复杂度
     * 时间复杂度： O(2^n)
     * 空间复杂度： T(2^n)
     * 优化目标 O(n) or O(nlogn)
     * @param n
     * @return
     */
    public static int fibonacci(int n) {
        if (n<=2) {
            return 1;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }

    /**
     * 递归太耗时间，不如非递归
     * @param n
     * @return
     */
    public static int non_fib(int n) {
        if (n<=2) {
            return 1;
        }
        int c=0;
        int a=1;
        int b=1;
        for (int i=3; i<=n; i++) {
            c = a+b;
            a=b;
            b=c;
        }
        return c;
    }

    public static int cache_fib(int n) {
        if (n <= 2) {
            return 1;
        }
        if (cache[n] > 0) {
            return cache[n];
        }
        cache[n] = cache_fib(n-1) + cache_fib(n-2);
        return cache[n];
    }

    public static int tailFib(int pre, int res, int n) {
        if (n <= 2)
            return res;
        return tailFib(res, pre + res, n-1);
    }

    /**
     * N 的 阶乘
     * 公式: f(n) = n*f(n-1)
     * 终止条件: n<=1, f(n) = 1
     * 时间复杂度: O(n)
     * 空间复杂度: T(n)
     * @param n
     * @return
     */
    public static int factorial(int n) {
        if (n <= 1)
            return 1;
        return n*factorial(n-1);
    }

    /**
     * 非递归实现
     * @param n
     * @return
     */
    public static int non_fac(int n) {
        if (n <= 1) {
            return 1;
        }
        int total = 0;
        for (int i=n; i>1; i--) {
            total *= n;
        }
        return total;
    }

    public static int tailFac(int n, int res) {
        if (n <= 1)
            return res;
        return tailFac(n-1, n*res);
    }

    public static void main(String[] args) {
        System.out.println("=======================斐波那契数 缓存实现=========================");
        cache = new int[46];
        for (int i=40; i<45; i++) {
            long start = System.currentTimeMillis();
            System.out.println("n=" + i + ", " + cache_fib(i) + "， 耗时:" + (System.currentTimeMillis()-start));
        }
        System.out.println("======================斐波那契数 递归实现=====================");
        for (int i=40; i<=45; i++) {
            long start = System.currentTimeMillis();
            System.out.println("n=" + i + ", " + fibonacci(i) + "， 耗时:" + (System.currentTimeMillis()-start));
        }
        /*
        System.out.println("======================斐波那契数 非递归实现=====================");
        for (int i=40; i<=45; i++) {
            long start = System.currentTimeMillis();
            System.out.println("n=" + i + ", " + non_fib(i) + "， 耗时:" + (System.currentTimeMillis()-start));
        }
        System.out.println("======================斐波那契数 尾递归实现=====================");
        for (int i=40; i<=45; i++) {
            long start = System.currentTimeMillis();
            System.out.println("n=" + i + ", " + non_fib(i) + "， 耗时:" + (System.currentTimeMillis()-start));
        }*/
        System.out.println("======================阶乘 递归实现=====================");
        for (int i=1; i<=10; i++) {
            long start = System.nanoTime();
            System.out.println("n=" + i + ", " + factorial(i) + "， 耗时:" + (System.nanoTime()-start));
        }
        System.out.println("======================阶乘 非递归实现=====================");
        for (int i=1; i<=10; i++) {
            long start = System.nanoTime();
            System.out.println("n=" + i + ", " + non_fac(i) + "， 耗时:" + (System.nanoTime()-start));
        }
        System.out.println("======================阶乘 尾递归实现=====================");
        for (int i=1; i<=10; i++) {
            long start = System.nanoTime();
            System.out.println("n=" + i + ", " + tailFac(i, 1) + "， 耗时:" + (System.nanoTime()-start));
        }
    }
}
