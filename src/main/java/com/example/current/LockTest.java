package com.example.current;


import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;



public class LockTest {
    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
        IntStream.range(0,10).forEach(i->new Thread(()->{
            try {
                boundedBuffer.put("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

}

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[10];
    int putIndex, takeIndex, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putIndex] = x;
            if (++putIndex == items.length) putIndex = 0;
            ++count;
            System.out.println(" put method: " + Arrays.toString(items));
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeIndex];
            if (++takeIndex == items.length) takeIndex = 0;
            --count;
            System.out.println(" put method: " + Arrays.toString(items));
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}