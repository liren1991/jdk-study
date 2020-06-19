package com.example.current;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 *  关于Lock与synchronize关键字在锁的处理上的重要差别
 *      1. 锁的获取方式：前者是通过程序代码的方式由开发者手动获取，后者是通过JVM来获取的；
 *      2. 具体实现方式：前者是通过Java代码的方式来实现，后者是通过JVM底层来实现的（无需开发者关注）
 *      3. 所得释放方式： 前者必须通过unlock方法在finally块中手动释放，后者是通过JVM来释放（无需开发者关注）
 *      4. 锁的具体类型：前者提供了多种，如公平锁、非公平锁；后者与前者均提供了可重入锁
 *
 */
public class LockTest {

    private Lock lock = new ReentrantLock();

    public void getMethod1(){
        try {
            lock.lock();
            System.out.println("myMethod invoked");
        }finally {
            lock.unlock();
        }
    }

    public void getMethod2(){
        try {
            lock.lock();
            System.out.println("myMethod invoked");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest lockTest = new LockTest();
        Thread thread = new Thread(()->{
            IntStream.range(0,10).forEach(item->{
                lockTest.getMethod1();
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        Thread thread1 = new Thread(()->{
            IntStream.range(0,10).forEach(item->{
                lockTest.getMethod2();
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        thread.start();
        thread1.start();
    }
}
