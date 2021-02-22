package com.lpc.lockTest;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.openjdk.jol.info.ClassLayout;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassLayoutSpace extends SupperClassSpace {

    private boolean isMale;

    private String name;

    private byte age;

    private char b;

    private int score;

    private float aa;

    private Long da;

    private Object object = new Object();

    private char[] chs = new char[11];

    private Object[] os = new Object[]{new Integer(1), new BigDecimal(2), new AtomicInteger(8)};
    private static Object[] os1 = new Object[]{new Integer(1), new BigDecimal(2), new AtomicInteger(8)};

    public static void main(String[] args) {
        ClassLayoutSpace o = new ClassLayoutSpace();
        System.out.println(ClassLayout.parseClass(ClassLayoutSpace.class).toPrintable());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(Integer.toHexString(o.hashCode()));
        System.out.println(Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(ClassLayout.parseInstance(o.os1).toPrintable());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }
}
