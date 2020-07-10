package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    @Test
    public void HashMapTest() {
        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>(32);
        for (int i = 0; i < 47; i++)
            hashMap.put("abc"+i,i);

        new Thread(()->{
            hashMap.put("通话","11");
            System.out.println("--------------------");
        }).start();

        new Thread(()->{
            hashMap.put("重地","22");
            System.out.println("====================");
        }).start();

        new Thread(()->{
            hashMap.put("abc5","xx");
            System.out.println("....................");
        }).start();

    }

    /**
     * 测试代码中的运算
     */
    @Test
    public void test1(){
        int RESIZE_STAMP_BITS = 16;
        int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
        System.out.println(MAX_RESIZERS);
        System.out.println(1792&4);
        System.out.println(1792&8);
        System.out.println(1792&16);
        System.out.println(1792&32);
        System.out.println(1792&64);
        System.out.println(1792&128);
        System.out.println(1792&256);
        System.out.println(1792&512);
        System.out.println(1792&1024);
    }
}
