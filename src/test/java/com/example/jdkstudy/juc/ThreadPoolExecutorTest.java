package com.example.jdkstudy.juc;

import org.junit.jupiter.api.Test;

/**
 * 对于线程池来说，提供了execute和submit两种方式来向线程池提交任务，总体来说，submit方法是可以取代execute方法的，因为它既可以接收Callable任务，也可以接收Runnable任务。
 *
 * 关于线程池的总体执行策略：
 *  1. 如果线程池中正在执行的 线程数 < corePoolSize，那么线程池就会优先选择创建新的线程而非将提交的任务加到阻塞队列中
 *  2. 如果线程池中正在执行的 线程数 >= corePoolSize，那么线程池就会优先选择将提交的任务加到阻塞队列中而非创建新的线程
 *  3. 如果提交的任务无法加入到阻塞队列中，那么线程池就会创建新的线程；如果创建的 线程数 超过了 maximumPoolSize，那么就执行拒绝策略。
 *
 * 关于线程池提交任务的总结：
 *  1. 线程池的两种提交方式：submit {@link java.util.concurrent.AbstractExecutorService.submit()} 与 execute {@link java.util.concurrent.AbstractExecutorService.execute()}
 *  2. submit有三种方式，无论哪种方式最终都是将传递进来的任务转换为一个Callable对象进行处理
 *  3. 当Callable对象构造完毕后，最终会都会调用Executor接口中声明的execute方法进行统一处理
 *
 * 对于线程池来说，存在两个变量需要维护：
 *  1. 线程池本身的状态： ctl的高3位来表示 {@link java.util.concurrent.ThreadPoolExecutor.ctl}
 *  2. 线程池中所运行着的线程数量： ctl的其余29位来表示
 *
 * 线程池一共存在5中状态
 *  1. RUNNING   线程池可以接受新的任务提交，并且还可以正常处理阻塞队列中的任务
 *  2. SHUTDOWN  不再接受新的任务提交，不过线程池还可以继续处理阻塞队列中的任务
 *  3. STOP      不再接受新的任务提交，同时还会丢弃阻塞队列中的既有任务；此外它还会中断正在处理的任务
 *  4. TIDYING   所有的任务都执行完毕后（同时也涵盖了阻塞队列中的任务），当前线程池中的活动线程数量降为0，将会调用terminated方法
 *  5. TERMINATED 线程池的终止状态，当terminated方法执行完毕后，线程池将会处于该状态之下
 *
 * 线程池的状态流转：
 *  RUNNING -> SHUTDOWN:    当调用了线程池的 shutdown方法时，或者当finalize方法被隐式调用后 （该方法会调用shutdown方法）
 *  RUNNING,SHUTDOWN -> STOP: 当调用了线程池的shutdownNow方法时
 *  STOP -> TIDYING:        在线程池变为空时
 *  TIDYING -> TERMINATED:  在terminated方法被执行完时
 */
public class ThreadPoolExecutorTest {

    // 验证高3位和低29位数字不冲突
    @Test
    public void testStatus(){
        System.out.println(-1 << 29);
        System.out.println( 0 << 29);
        System.out.println( 1 << 29);
        System.out.println( 2 << 29);
        System.out.println( 3 << 29);
        System.out.println( (1 << 29) - 1);
    }





}
