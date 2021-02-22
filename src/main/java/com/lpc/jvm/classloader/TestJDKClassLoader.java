package com.lpc.jvm.classloader;

import com.sun.crypto.provider.DESKeyFactory;
import sun.misc.Launcher;

import java.net.URL;

public class TestJDKClassLoader {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader());
        System.out.println(TestJDKClassLoader.class.getClassLoader());

        System.out.println("------------------------------------------------");

        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(" the bootstrapClassLoader is: " + bootstrapClassLoader);
        System.out.println(" the extClassLoader is: " + extClassLoader);
        System.out.println(" the appClassLoader is: " + appClassLoader);

        System.out.println("==================================================");

        System.out.println(" the bootstrap classLoader 加载以下文件:");
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url);
        }

        System.out.println("==================================================");

        System.out.println(" the ext classloader 加载以下文件:");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("==================================================");
        System.out.println(" the app classloader 加载以下文件:");
        System.out.println(System.getProperty("java.class.path"));
    }
}
