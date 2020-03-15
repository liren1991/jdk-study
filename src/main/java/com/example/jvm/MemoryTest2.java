package com.example.jvm;

import java.util.concurrent.TimeUnit;

public class MemoryTest2 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(A::method,"myTreadA").start();
        new Thread(B::method,"myTreadB").start();

        TimeUnit.SECONDS.sleep(50);
    }
}

class A {
    public static synchronized void method(){
        System.out.println("method from A");
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            B.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class B {
    public static synchronized void method(){
        System.out.println("method from B");
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            A.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}