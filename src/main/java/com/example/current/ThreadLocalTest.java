package com.example.current;

/**
 * ThreadLocal
 *  本质上，ThreadLock是通过空间换时间，从而实现每个线程当中都会有一个变量的副本，这样每个线程都会操作该副本，从而完全规避了
 *  多线程的并发问题。
 *
 *  Java中存在四种类型的引用：
 *      1. 强引用（strong）
 *      2. 软引用（soft）
 *      3. 弱引用（weak）
 *      4. 虚引用（phantom）
 */
public class ThreadLocalTest {
    private static final ThreadLocal<String> t1 = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            t1.set("hello word");
            System.out.println(t1.get());
        }finally {
            t1.remove();
        }
    }

}
