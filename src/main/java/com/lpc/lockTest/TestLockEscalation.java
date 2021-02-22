package com.lpc.lockTest;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 先来说明一下synchronized锁的升级过程：
 * JDK1.8 之后 JVM启动，默认需要 4s 后，开启偏向锁，如果创建对象时为开启偏向锁，则对对象synchronized 会直接升级为 轻量级锁
 * 如果开启了偏向锁，先升级偏向锁，再升级为 轻量级锁，
 * 轻量级锁本质就是在自旋，当自旋超过一定次数(自适应自旋机制判定次数)，就升级成重量级锁
 * 当超过2个线程对对象加锁，会升级为重量级锁
 * 对象无引用时，经过多次标记(默认 15次，最多 15次) 放入老年代，待GC回收。
 *
 * synchronized java规定锁升级之后，则无法锁降级；升级之后，如果系统的并发下来了，无疑降低了处理速度
 * 我们可以进行一段时间进行统计，统计并发度已经很低，如果还是重量级锁，则进行锁对象的切换。
 * 换一个锁对象，这样又开始偏向锁状态，提升了处理速度；锁对象切换时候，需要注意并发操作；
 */
public class TestLockEscalation {

    private int id;
    private String name;


    /**
     * 对象布局，参考文档： https://www.cnblogs.com/jajian/p/13681781.html
     * 参考链接 结合图，看对象头(Object Header)中锁的升级过程
     * @param args
     */
    public static void main(String[] args) throws Exception {
        TestLockEscalation test0 = new TestLockEscalation();
        System.out.println("无状态(001)：" + ClassLayout.parseInstance(test0).toPrintable());
        synchronized (test0) {
            System.out.println("查看加锁后是否会自动偏向锁功能(00)：" + ClassLayout.parseInstance(test0).toPrintable());
            /** 通过输出比较得出 如果不开启偏向锁功能，首次相同线程加锁后，实际上会直接越过偏向锁，直接开启轻量锁 **/
        }
        /**
         * jvm 默认延时4s JVM自动开启偏向锁，可通过 -XX:BiasedLockingStartupDelay=0 取消延时；
         * 如果不要偏向锁，可通过 -XX:BiasedLocking = false 来设置
         * 这里只是开启偏向锁功能，但是并没有加偏向锁, 开启之后，创建的对象实例，默认开启偏向锁功能, 并且线程号信息不存在
         */
        Thread.sleep(5000);
        TestLockEscalation test = new TestLockEscalation();
        System.out.println("启用偏向锁(101)(无线程ID)：" + ClassLayout.parseInstance(test).toPrintable());
        synchronized (test) {
            System.out.println("当前线程ID:" + Long.toHexString(Thread.currentThread().getId()));
            System.out.println("偏向锁(101)(带线程ID)：" + ClassLayout.parseInstance(test).toPrintable());
            synchronized (test) {
                System.out.println("偏向锁(101)(带线程ID)：" + ClassLayout.parseInstance(test).toPrintable());
            }
        }
        /** 从下面一行看出，偏向锁释放，并不会对对象头做任何处理，不会主动释放的, 目的是下次便于直接判断是不是偏向锁*/
        System.out.println("偏向锁释放(101)(带线程ID)：" + ClassLayout.parseInstance(test).toPrintable());

        new Thread( ()->{
            synchronized (test) {
                System.out.println("轻量级锁(00)：" + ClassLayout.parseInstance(test).toPrintable());
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(1000);

        new Thread( ()->{
            synchronized (test) {
                System.out.println("重量级锁(10)：" + ClassLayout.parseInstance(test).toPrintable());
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.MILLISECONDS.sleep(5000);
    }
}
