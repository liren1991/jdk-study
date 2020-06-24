package com.example.current;

/**
 * volatile 关键字主要作用：
 *  1. 实现long/double 类型变量的原子操作
 *  2. 防止指令重排
 *  3. 实现变量的可变性
 *
 * 当使用volatile修饰变量时，应用就不会从寄存器中获取该变量的值，而是从内存中获取
 *  volatile与锁类似的地方有两点：
 *      1. 确保变量的内存可见性
 *      2. 防止指令重排序
 * volatile 可以确保对变量写操作的原子性，但也不具备排他性。
 *      另外的重要一点在于:使用锁可能会导致线程的上下文切换(内核态与用户态之间的切换)。但使用volatile并不会出现这种情况
 *
 * 防止指令重排序与实现变量的可见性都是通过一种手段来实现的：内存屏障（memory barrier）
 *  volatile 写入操作：
 *      int a = 1;
 *      String s = "hello";
 *      // 内存屏障（Release Barrier，释放屏障）
 *      // Release Barrier: 防止下面的 volatile 与上面的所有操作的指令重排序
 *      volatile boolean v = false;
 *      // 内存屏障（Store Barrier，存储屏障）
 *      // Store Barrier: 重要作用是刷新处理器缓存，结果是可以确保该存储屏障之前一切的操作所生成的结果对于其它处理器来说都可见
 *
 *  volatile 读操作：
 *      int a = 1;
 *      String s = "hello";
 *      // 内存屏障（Load Barrier，加载屏障）
 *      // 可以刷新处理器缓存，同步其它处理器对该 volatile变量的修改结果
 *      volatile v1 = v;
 *      // 内存屏障（Acquire Barrier，获取屏障）
 *      // 可以防止上面的volatile读取操作与下面的所有操作语句的指令重排序
 *  对于volatile关键字变量的读写操作，本质上都是通过内存屏障来执行的。内存屏障兼具了两方面能力：1.防止指令重排序 2. 实现变量内存的可见性
 *      1. 对于读取操作来说，volatile可以确保该操作与其后续的所有读写操作都不会进行指令重排序。
 *      2. 对于修改操作来说，volatile可以确保该操作与其上面的所有读写操作都不会进行指令重排序。
 *
 *  volatile 与 synchronize 的一些比较
 *      1. synchronize 同样具备内存可见性与防止指令重排序的功能
 *      2. synchronize 操作情景
 *          monitorenter
 *          内存屏障（Acquire Barrier，获取屏障）
 *          ...... 代码
 *          内存屏障（Release Barrier，释放屏障）
 *          monitorexit
 *
 */

/**
 *  Java内存模型（Java Memory Model， JMM） 以及 happen-before
 *      1. 变量的原子性问题。
 *      2. 变量的可见性问题。
 *      3. 变量修改的时序性问题。
 *      happen-before 重要规则：
 *      1. 顺序执行规则（限定在单个线程上的）：该线程的每个动作都happen-before它的后面的动作
 *      2. 隐式锁（monitor）规则：unlock happen-before lock，之前的线程对于同步代码块的所有执行结果对于后续获取锁的线程来说都是可见的。
 *      3. volatile读写规则：对于一个volatile变量的写操作一定会happen-before后续对该变量的读操作。
 *      4. 多线程的启动规则： Thread对象的start方法happen-before该线程的run方法中的任何一个动作，包括在其启动的任何子线程。
 *      5. 多线程的终止规则：一个线程启动了一个子线程，并且调用了子线程的join方法等待其结束，那么当子线程结束后，父线程的接下来的所有操作都可以看到子线程run方法中的执行结果
 *      6. 线程的中断规则：可以调用interrupt方法来中断线程，这个调用happen-before对该线程中的检查（isInterrupted）.
 *
 *
 */
public class VolatileTest {
}
