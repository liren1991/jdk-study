package com.example.current;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(3);
        IntStream.range(0,3).forEach(i->new Thread(()->{
            try {
                Thread.sleep(2000);
                System.out.println("hello   " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                downLatch.countDown();
            }
        }).start());
        System.out.println("启动子线程完毕！！！");

        try {
            downLatch.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完毕！！！！");
    }



}
