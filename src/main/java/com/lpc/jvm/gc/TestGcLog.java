package com.lpc.jvm.gc;

/**
 * vm 参数： -verbose:gc verbose冗余的意思，此参数代表会输出GC的详细日志
 * 日志分析： https://www.cnblogs.com/webor2006/p/10989175.html
 *
 */
public class TestGcLog {

    public static void main(String[] args) {
        int OneMB = 1024*1024;
        byte[] myAlloc1 = new byte[OneMB];
        byte[] myAlloc2 = new byte[OneMB];
        byte[] myAlloc3 = new byte[OneMB];
        byte[] myAlloc4 = new byte[2*OneMB];
        byte[] myAlloc5 = new byte[2*OneMB];
        byte[] myAlloc6 = new byte[2*OneMB];
        //byte[] myAlloc7 = new byte[20*OneMB];
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hell world");
    }

}
