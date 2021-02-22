package com.lpc.jvm.classloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoaderTest extends ClassLoader {

    private String classPath;

    public MyClassLoaderTest(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(this.classPath+"/"+name+".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();

        return data;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }

    /**
     * 如果打破双亲委派机制，需要 重写 loadClass 方法，不走双亲委派机制即可
     * （java安全机制，会校验包名是否和核心类包一致，如果一致，也是不可以走自定义类加载器的）
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                if (!name.startsWith("com.lpc.jvm.classloader")) {
                    c = this.getParent().loadClass(name);
                } else {
                    c = findClass(name);
                }

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    /**
     * 运行结果：
     * =======自己的加载器加载类调用方法=======
     * com.lpc.jvm.classloader.MyClassLoaderTest$MyClassLoaderTest
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
        MyClassLoaderTest classLoader = new MyClassLoaderTest("/tmp");

        Class clazz = classLoader.loadClass("com.lpc.jvm.classloader.MyClassLoaderTest");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout",null);
        method.invoke(obj,null);
        System.out.println(clazz.getClassLoader().getClass().getName());

    }
}
