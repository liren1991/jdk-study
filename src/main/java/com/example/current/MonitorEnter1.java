package com.example.current;

/**
 * 编译器对于锁的优化措施：
 *  锁消除技术：JIT编译器（Just In Time编译器） 可以在动态编译同步代码时，使用一种叫做逃逸分析的技术，来通过该项技术判别程序所使用的锁的对象是否只被一个
 *              线程所使用，而没有散布到其它线程中；如果情况就是这样的话，那么JIT编译器在编译这个同步代码时就不会生成synchronize关键字所标识的锁的申请与
 *              释放机器码，从而消除了锁的使用。
 */
public class MonitorEnter1 {

//    Object object = new Object();
    public void method(){
        Object object = new Object();
        synchronized (object){
            System.out.println("hello word");
        }
    }



}
