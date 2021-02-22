package com.lpc.deepCopy;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestBeanCopier {

    static BeanCopier copier = BeanCopier.create(Person1.class, Person2.class, false);

    static void test(int flag) {
        long start = System.currentTimeMillis();
        for (int i = 0; i<10000000; i++) {
            final  int index = i;
            Person1 person1 = new TestBeanCopier.Person1();
            person1.address = new String[]{"北京市", "北京市", "朝阳区"};
            person1.age = 1;
            person1.name = "person_" + index;
            person1.properties = new HashMap<String, Object>() {
                {
                    put("asdsa_"+ index, new Object());
                    put("asdsadas_" + index, new Object());
                }
            };

            Person2 person2 = new TestBeanCopier.Person2();
            if (flag == 1) {
                copier.copy(person1, person2, null);
            } else if (flag == 2) {
                BeanUtils.copyProperties(person1, person2);
            } else {
                try {
                    org.apache.commons.beanutils.BeanUtils.copyProperties(person1, person2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("cost:" + (System.currentTimeMillis()-start));
    }

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>BeanCopier<<<<<<<<<<<<<<<<");
        test(1);
        System.out.println(">>>>>>>>>>>>>>>>>>Spring.BeanUtils<<<<<<<<<<<<<<<<");
        test(2);
        System.out.println(">>>>>>>>>>>>>>>>>>Apache.BeanUtils<<<<<<<<<<<<<<<<");
        test(0);
    }


    @Data
    static class Person1 {
        private int age;
        private String name;
        private String[] address;
        private Map<String, Object> properties;
    }

    static class Person2 {
        private int age;
        private String name;
        private String[] address;
        private Map<String, Object> properties;
    }
}
