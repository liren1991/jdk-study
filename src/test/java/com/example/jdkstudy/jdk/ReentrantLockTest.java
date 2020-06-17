package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;
//import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
  /*  private static Unsafe unsafe;
    static {
        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(()->{
                lock.lock();
                try {
                   TimeUnit.SECONDS.sleep(10);
                    System.err.println("i" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
        synchronized (this){
            try {
                this.wait(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void test2() throws InterruptedException {
        InnerThread thread = new InnerThread(unsafe);
        thread.start();
//        unsafe.unpark(thread);
        TimeUnit.SECONDS.sleep(5);
        System.out.println("测试结束");
    }

    @Test
    public void test1() {
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    System.out.println("========" + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }


    static class InnerThread extends Thread {
        Unsafe unsafe;
        InnerThread(Unsafe unsafe) {
            this.unsafe = unsafe;
        }
        @Override
        public void run() {
            long start;
            System.out.println("start   " + (start = System.currentTimeMillis()));
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            unsafe.park(false, 1000000000L);
            System.out.println("end  耗时：  " + (System.currentTimeMillis()-start));
        }
    }

*/
}

/**
 * final void lock() {
 * 获取锁成功
 * if (compareAndSetState(0, 1))
 * setExclusiveOwnerThread(Thread.currentThread());
 * 获取锁失败，加入队列
 * else
 * acquire(1);
 * }
 */