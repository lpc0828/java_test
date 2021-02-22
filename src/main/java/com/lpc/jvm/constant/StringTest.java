package com.lpc.jvm.constant;


public class StringTest {

    /**
     * 5 // 在 JDK 1.6 下输出是 false，创建了 6 个对象
     * 6 // 在 JDK 1.7 及以上的版本输出是 true，创建了 5 个对象
     * 7 // 当然我们这里没有考虑GC，但这些对象确实存在或存在过
     */
    private static void testNewString() {
        String s1 = new String("he") + new String("llo");
        String s2 = s1.intern();
        System.out.println(s1 == s2);
    }

    private static void testJingtaiBianyi() {
        String a = "a11";
        String aa = "a" + 11;
        System.out.println(a == aa); //true, 因为在编译器就确定了

        String b = "b11";
        String b0 = "11";
        String bb = "b" + b0;
        System.out.println(b == bb); //false, 因为运行时才能确认bb， 会调用 StringBuilder 的 append 和 toString 方法 new String("b11");


        String c = "c11";
        final String c0 = "11";
        String cc = c + c0;
        System.out.println(c == cc); //? 依然是false， 如果是全局静态 final变量呢?

    }


    private static String d = "d11";
    private static int d0 = 11;

    private static String e = "e11";
    private static final int e0 = 11;

    private static void testGlobalJingtaiBianyi() {
        String dd = "d" + d0;
        System.out.println( d == dd); // 依然不相等， static变量未经 final 修饰，存在变化的可能，编译期肯定不能替换。

        String ee = "e" + e0;
        System.out.println( e == ee); // 亲测相等, final 修饰的静态变量看起来会在编译器 替换成常量

        final int f0 = getFF();
        String ff = "f" + f0;
        System.out.println(f == ff);

    }

    private static String f = "f11";
    private static int getFF() {
        return 11;
    }

    public static void main(String[] args) {
        testGlobalJingtaiBianyi();
    }
}
