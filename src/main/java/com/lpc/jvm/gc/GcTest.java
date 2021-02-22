package com.lpc.jvm.gc;

/**
 * 测试GC的分配情况
 * 1. 当我们仅开启 allocation1 分配时，发现 eden取基本满了
 * ，from 和 to 两个 survivor 区 使用率是 0%
 * 2. 当我们再打开 allocation2 的初始化代码，发现 空间不够用了 GC (Allocation Failure)，这时候，会触发minor GC，
 * 会把大对象 allocation1 复制survivor区，但是发现空间不够，于是把新生代对象直接转移到 ParOldGen 区，老年代由于空间够用，不会触发FULL GC，
 * 再给 allocation2 在 eden 区 分配内存空间
 * 3. 再打开 allocation3， allocation4，allocation5，allocation6 区域之后，发现eden区依然够用，会直接分配到 eden区
 */
public class GcTest {

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6;
        allocation1 = new byte[60000*1024];

        allocation2 = new byte[8000*1024];

        allocation3 = new byte[1000*1024];
        allocation4 = new byte[1000*1024];
        allocation5 = new byte[1000*1024];
        allocation6 = new byte[1000*1024];

    }
}
