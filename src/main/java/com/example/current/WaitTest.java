package com.example.current;


import java.util.concurrent.TimeUnit;

/**
 * 在调用wait方法时，线程必须要持有被调用对象的锁，当调用wait方法后，线程就会释放掉该对象的锁（monitor）
 * 在调用 Thread 类的sleep方法时，线程时不会释放掉对象的锁
 * 关于wait与notify 和 notifyAll方法总结：
 *  1. 当调用wait时，首先需要确保调用了wait方法的线程已经有了对象的锁。
 *  2. 当调用wait后，该线程就会释放掉这个对象的锁，然后进入到等待状态（wait set）
 *  3. 当线程调用了wait后进入到等待状态，就可以等待其它线程调用相同对象的notify 或 notifyAll方法来使得自己
 *      被唤醒。
 *  4.  一旦这个线程被其他线程唤醒后，该线程就会与其他线程一同开始竞争这个对象的锁（公平竞争）；只有当该线程获取到这个对象的锁
 *      后，线程才会继续往下执行。
 *  5.  调用wait方法的代码片段需要放在一个synchronize块或是synchronize方法中，这样才可以确保线程在调用
 *      wait方法前已经获取到该对象的锁。
 *  6.  当调用对象的notify方法时，他会随机唤醒该对象等待集合（wait set）中的任意一个线程，当某个线程被唤醒后，他
 *      就会与其他线程一同竞争对象的锁
 *  7.  当调用对象的notifyAll方法时，它会唤醒该对象等待集合（wait set）中的所有线程，这些线程被唤醒后，又会
 *      开始竞争对象的锁
 *  8.  在某一时刻，只有唯一一个线程可以拥有该对象的锁
 */
public class WaitTest {

    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        Thread increaseThread = new IncreaseThread(myObject);
        Thread decreaseThread = new DecreaseThread(myObject);
        increaseThread.start();
        decreaseThread.start();
    }
}

class IncreaseThread extends Thread{

    private MyObject myObject;

    public IncreaseThread(MyObject myObject) {
        this.myObject = myObject;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myObject.increase();
        }
    }
}

class DecreaseThread extends Thread{

    private MyObject myObject;

    public DecreaseThread(MyObject myObject) {
        this.myObject = myObject;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myObject.decrease();
        }
    }
}

class MyObject {
    private int counter;

    public synchronized void increase(){
        while (counter != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter++;
        System.out.println(counter);
        notify();
    }

    public synchronized void decrease(){
        while (counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter --;
        System.out.println(counter);
        notify();
    }

}