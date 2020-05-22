package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    private static int i = 0;
    @Test
    public void test() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        for (int j = 0; j < 1000; j++) {
            new Thread(()->{
                try {
                    writeLock.lock();
                    i += 1;
                }finally {
                    writeLock.unlock();
                }
            }).start();
        }
        System.out.printf("i===  " + i);
        TimeUnit.SECONDS.sleep(10);
    }




}
