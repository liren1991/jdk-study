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

/**
 * 对于ReentrantLock来说，其执行逻辑如下所示：
 *  1. 尝试获取对象的锁，如果获取不到（意味着已经有其它线程持有了锁，并且尚未释放锁），那么它就会进入到AQS的阻塞队列当中。
 *  2. 如果获取到，那么根据锁是否公平锁还是非公平锁来进行不同的处理
 *      （1）. 如果是公平锁，那么线程会直接放置到AQS阻塞队列的末尾。
 *      （2）. 如果是非公平锁，那么线程会首先尝试进行CAS计算，如果成功，则直接获取到锁；如果失败，则与公平锁的处理方式一致，被放到阻塞队列末尾。
 *  3. 当锁被释放时（调用了unlock方法），那么底层会调用release方法对state成员变量值进行减一操作，如果减一后，state值不为0，那么release操作就执行完毕；
 *      如果减一操作后，state值为0，则调用LockSupport的unpark方法唤醒该线程后的等待队列的第一个后继线程（pthread mutex_unlock）,将其唤醒，
 *      使之能够获取到对象的锁（release时，对于公平锁与非公平锁的处理逻辑是一致的）；之所以调用release方法后state值不可能为0，原因在于ReentrantLock
 *      是可重入锁，标识线程可以多次调用lock方法，导致每调用一次，state值都会+1。
 *
 * 对于ReentrantLock来说，所谓的上锁，本质上就是对AQS中的state成员变量的操作：对 state+1 ，表示上锁；对state-1 表示释放锁。
 */
public class LockTest {

    private Lock lock = new ReentrantLock();

    public void getMethod1(){
        try {
            lock.lock();
            System.out.println("myMethod1 invoked");
        }finally {
            lock.unlock();
        }
    }

    public void getMethod2(){
        try {
            lock.lock();
            System.out.println("myMethod2 invoked");
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
