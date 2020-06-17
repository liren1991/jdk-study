package com.example.current;

/**
 * 1. 当使用 synchronize 关键字来修饰代码块时，字节码层面上时通过 monitorenter 与monitorexit
 * 指令来实现的的锁的获取与释放动作
 * 2. 当线程进入到monitorenter指令后，线程将会持有monitor对象，退出monitorenter指令后，线程将会
 * 释放monitor对象
 * 3. 对于synchronize关键字修饰方法来说，并没有出现monitorenter 和 monitorexit指令，而是出现一个 ACC_SYNCHRONIZED
 *      jvm使用了ACC_SYNCHRONIZED访问标志来区分一个方法是否为同步方法，当方法被调用时，调用指令会检查该方法是否拥有ACC_SYNCHRONIZED标志
 *      如果有，那么执行线程会先持有方法所在对象的monitor对象，然后再去执行方法体；在该方法执行期间，其他任何线程均无法在获取到这个monitor对象，
 *      当线程执行完后，它会释放掉这个monitor对象
 * 编译后使用查看字节码:  D:\java\workspace\jdk-study\target\classes>javap -v com.example.current.MonitorEnter
 */

/**
 * jvm中的同步是基于进入与退出监视器对象（管程对象 monitor）来实现的，每个对象实例都会有个monitor对象，monitor对象会和Java对象一同创建并销毁，
 * monitor对象是由C++来实现的。
 *
 * 当多个线程同时访问一段同步代码时，这些线程会被放到一个EntryList集合中，处于阻塞状态的线程都会被放到该列表当中。接下来，当线程获取对象的monitor
 * 时，monitor是依赖于底层操作系统的 mutex lock 来实现互斥的，线程获取 mutex 成功，则会持有该 mutex，这时其它线程就无法在获取到该mutex
 *
 * 总结：同步锁在这种实现方式当中，因为monitor是依赖于底层的操作系统实现，这就存在用户态与内核态之间的切换，所以会增加性能开销。
 *
 * 通过处于EntryList与WaitSet中的线程均处于阻塞状态，阻塞操作是由操作系统来完成的，在Linux下是通过 ptheard_mutex_lock 函数实现的。线程被阻塞后
 * 便会进入到内核调度状态，这会导致系统在用户态与内核态之间来回切换，严重影响锁的性能。
 *
 * 解决上诉问题的办法便是自旋。其原理是：当发生对monitor的争用时，若Owner能够在很短的时间内释放掉锁，则那些正在争用的线程就可以稍微等待一下（即所谓的自旋），
 * 在Owner线程释放锁之后，争用线程可能会立即获取到锁，从而避免了系统阻塞。不过，当Owner运行的时间超过了临界值后，争用线程自旋一段时间后依然无法获取到锁，这时
 * 争用线程则会停止自旋而进入到阻塞状态。所以总体的思路是：先自旋，不成功再进行阻塞，尽量降低阻塞的可能性，这对那些执行时间很短的代码块来说有极大的性能提升。
 * 显然，自旋在多处理器（多核心）上才有意义。
 */

/**
 * PTHREAD_MUTEX_TIMED_NP:      这是一个缺省值，也是普通锁。当一个线程加锁以后，其余请求锁的线程将会形成一个等待队列，并且在解锁后按照优先级获取到锁。这种策略可以确保资源分配的公平性。
 * PTHREAD_MUTEX_RECURSIVE_NP:  嵌套锁（可重入锁）。允许一个线程对同一个锁成功获取多次，并通过unlock解锁。如果是不同线程请求，则在加锁线程解锁时重新进行竞争。
 * PTHREAD_MUTEX_ERRORCHECK_NP: 检错锁。如果一个线程请求同一个锁，则返回EDEADLK，否则与 PTHREAD_MUTEX_TIMED_NP 类型动作相同，这样就保证了当不允许多次加锁时就不会出现最简单情况下的死锁。
 * PTHREAD_MUTEX_ADAPTIVE_NP:   适应锁，动作最简单的锁类型，仅仅等待解锁后重新竞争。
 */
public class MonitorEnter {

    private Object object = new Object();

    // 此方法只会有一个 monitorexit 因为无论结果怎样其最总都会以异常的方式结束
    public void method() {
        synchronized (object) {
            System.out.println("hello world");
            throw new RuntimeException();
        }
    }

    // 此方法会有两个 monitorexit 一个为正常退出，另一个为异常退出
    public void method1() {
        synchronized (object) {
            System.out.println("welcome");
        }
    }

    public synchronized void method2() {
        System.out.println("hello word");
    }

}
/*

  public com.example.current.MonitorEnter();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: new           #2                  // class java/lang/Object
         8: dup
         9: invokespecial #1                  // Method java/lang/Object."<init>":()V
        12: putfield      #3                  // Field object:Ljava/lang/Object;
        15: return
      LineNumberTable:
        line 10: 0
        line 12: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      16     0  this   Lcom/example/current/MonitorEnter;

  public void method();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=3, args_size=1
         0: aload_0
         1: getfield      #3                  // Field object:Ljava/lang/Object;
         4: dup
         5: astore_1
         6: monitorenter
         7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        10: ldc           #5                  // String hello world
        12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        15: new           #7                  // class java/lang/RuntimeException
        18: dup
        19: invokespecial #8                  // Method java/lang/RuntimeException."<init>":()V
        22: athrow
        23: astore_2
        24: aload_1
        25: monitorexit
        26: aload_2
        27: athrow
      Exception table:
         from    to  target type
             7    26    23   any
      LineNumberTable:
        line 16: 0
        line 17: 7
        line 18: 15
        line 19: 23
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      28     0  this   Lcom/example/current/MonitorEnter;
      StackMapTable: number_of_entries = 1
        frame_type = 255 // full_frame
public void method1();
        descriptor: ()V
        flags: (0x0001) ACC_PUBLIC
        Code:
        stack=2, locals=3, args_size=1
        0: aload_0
        1: getfield      #3                  // Field object:Ljava/lang/Object;
        4: dup
        5: astore_1
        6: monitorenter
        7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        10: ldc           #9                  // String welcome
        12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        15: aload_1
        16: monitorexit
        17: goto          25
        20: astore_2
        21: aload_1
        22: monitorexit
        23: aload_2
        24: athrow
        25: return
        Exception table:
        from    to  target type
        7    17    20   any
        20    23    20   any
        LineNumberTable:
        line 24: 0
        line 25: 7
        line 26: 15
        line 27: 25
        LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0      26     0  this   Lcom/example/current/MonitorEnter;
        StackMapTable: number_of_entries = 2
        frame_type = 255 // full_frame
        offset_delta = 20
        locals = [ class com/example/current/MonitorEnter, class java/lang/Object ]
        stack = [ class java/lang/Throwable ]
        frame_type = 250 // chop
        offset_delta = 4

public synchronized void method2();
        descriptor: ()V
        flags: (0x0021) ACC_PUBLIC, ACC_SYNCHRONIZED
        Code:
        stack=2, locals=1, args_size=1
        0: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        3: ldc           #10                 // String hello word
        5: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        8: return
        LineNumberTable:
        line 30: 0
        line 31: 8
        LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0       9     0  this   Lcom/example/current/MonitorEnter;
        }


*/
