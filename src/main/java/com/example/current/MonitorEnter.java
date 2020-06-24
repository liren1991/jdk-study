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
 * 4. 编译器对锁的优化措施：锁消除。JIT编译器可以在动态编译同步代码时，使用一种叫做逃逸分析的技术，来通过该项技术判断程序中所使用的锁对象是否只
 *      被一个线程所使用，而没有散布到其它线程当中；如果情况就是这样的话，那么JIT编译器在编译这个同步代码时就不会生成synchronize关键字所标识
 *      的锁的申请与释放机器码，从而消除了锁的使用流程
 *      public void method（）{
 *          Object object = new Object（）；
 *          synchronize （object）{
 *              System.out.println();
 *          }
 *      }
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

/**
 * 在jdk1.5之前，若想实现线程同步，只能通过synchronize关键字这一种方式来达成；底层java也是通过synchronize关键字来做到数据的原子性维护；synchronize关键字是jvm实现的一种内置锁，
 * 从底层角度来说，这种锁的获取与释放都是有jvm隐式实现的。
 *
 * 从jdk1.5开始，并发包引入了Lock锁，Lock同步锁是基于java实现的，因此锁的获取与释放都是通过java代码来实现与控制的；然而synchronize是基于底层操作系统的Mutex Lock来实现的，每次对锁的获取
 * 与释放都会带来用户态与内核态之间的切换，这种切换也极大的增加系统的负担；在并发量较高时，也就是说锁的竞争比较激烈时，synchronize锁在性能上的表现比较差
 *
 * 从jkd1.6开始，synchronize锁的实现发生了很大的变化；jvm引入了相应的优化手段来提升synchronize锁的性能，这种提升涉及到了偏向锁、轻量级锁、重量级锁，从而减少锁的竞争所带来的用户态与内存态之间的切换；
 * 这种锁的优化实际上是通过java对象头中的一些标志位来去实现的；对应多的访问与改变，实际上都与java对象头息息相关。
 *
 * 从jdk1.6开始，对象实例在堆中会被划分为三个组成部分：对象头、实例数据与对齐填充。
 * 对象头主要也是由三块内容构成：
 *      1. Mark Word
 *      2. 指向类的指针
 *      3. 数组长度
 *  其中 Mark Word（它记录了对象、锁、垃圾回收相关信息。在64位的jvm中，其长度也是64bit）的位信息包括了如下组成部分：
 *      1. 无锁标记
 *      2. 偏向锁标记
 *      3. 轻量级锁标记
 *      4. 重量级锁标记
 *      5. GC标记
 *  对于synchronize锁来说，锁的升级主要是通过Mark Word中的锁标志位与是否偏向锁标志位来达成的；synchronize 关键字所对应的锁都是先从偏向锁开始，随着锁竞争的不断升级，逐步演化至轻量级锁，最后则变成重量级锁。
 *
 *  对于所得演化来说，它会经历如下阶段：
 *      无锁-> 偏向锁 -> 轻量级锁 -> 重量级锁
 *      偏向锁：针对一个线程来说的。它的主要作用就是优化同一个线程多次获取一个锁的情况；如果一个 synchronize方法被一个线程访问，那么这个方法所在的对象就会在其 Mark Word 中将偏向锁进行标记，同时还会有一个字段
 *              来存储该线程的ID；当这个线程再次访问同一个synchronize方法时，它会检查这个对象的 Mark Word的偏向锁标记以及是否指向了其它线程ID，如果是的话，那么该线程就无需再去进入管程（Monitor）了，而是
 *              直接进入该方法中。
 *      轻量级锁： 若第一个线程已经获取到了当前对象的锁，这时第二个线程又开始尝试争抢该对象的锁，由于该对象的锁已经被第一个线程获取到，因此它是偏向锁，而第二个线程在争抢时，会发现该对象头中的 Mark Word已经是
 *                  偏向锁，但里面储存的线程ID 并不是自己的（第一个线程的） ， 那么它会进行CAS（compare and swap），从而获取到锁，这里面存在两种情况：
 *                   1. 获取锁成功：那么它会直接将 Mark Word中的线程ID由第一个线程的改成自己的（偏向锁标记位保持不变），这样该对象依然会保持偏向锁的状态。
 *                   2. 获取锁失败： 则表示这时可能会有多个线程同时在尝试争抢该对象的锁，那么这时偏向锁就会进行升级，升级为轻量级锁。
 *               自旋锁：若自旋锁失败（依然无法获取锁），那么锁就会转化为重量级锁，在这种情况下，无法获取到所得线程都会进入到 monitor（即内核态）。自旋锁最大的特点就是避免了线程从用户态到内核态。
 *      重量级做： 线程最终从用户态进入到内核态。
 *
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
