package com.lpc.jvm.visualvm;

public class VisualVMTest {

    int count = 0;

    public void redo() {
        count++;
        redo();
    }

    /**
     * 可以通过改变JVM -Xss 的大小来控制 递归层数，默认大小 1MB
     */
    public void testStackOverFlow() {
        try {
            redo();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("count = " + count);
        }
    }

    /**
     * 通过 VisualVM 查看JVM各个区域变化
     *
     */
    public void testVisualVM() {
        User user = new User();
        user.age = 1;
        user.name = "test";
        //user.userB = new UserB();
        //user.userB.age = 2;
    }

    public static void main(String[] args) throws Exception {
         VisualVMTest test = new VisualVMTest();
        // test.testStackOverFlow();
        while (true) {
            test.testVisualVM();
        }

    }

    static class User {
        int age;
        String name;
        //UserB userB;
    }

    static class UserB {
        int age;
    }
}
