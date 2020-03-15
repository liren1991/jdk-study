package com.example.jvm;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 当前类加载器（Current Classloader）
 *      每个类都会使用自己的类加载（即加载自身的类加载器） 来加载其它类（指的是所依赖的类），
 *      如果ClassX 引用了 ClassY, 那么 ClassX的类加载器就会取加载ClassY(前提是ClassY尚未被加载)
 * 线程上下文加载器（Context Classloader）
 *      1. 线程上下文类加载器是从 JDK 1.2开始引入的，类Thread 中的 getContextClassLoader() 与 setContextClassLoader(ClassLoader loader)
 *          分别用来获取和设置上下文类加载器。
 *      2. 如果没有通过 setContextClassLoader(ClassLoader loader) 进行设置的话，线程将继承其父线程的上下文类加载器。java应用运行时的初始线程
 *          的上下文类加载器是系统类加载器。在线程中运行的代码可以通过该类加载器来加载类与资源。
 * 线程上下文类加载器的重要性：
 *    SPI（Service Provider Interface）
 *      1. 父 ClassLoader 可以使用当前线程 Thread.currentThread().getContextClassLoader() 所指定的 ClassLoader 加载的类。这就改变了父 ClassLoader
 *          不能使用子 ClassLoader 或是其他没有直接父子关系的 ClassLoader 加载的类的情况，即改变了双亲委托模型。
 *      2. 线程上下文类加载器就是当前线程的 Current ClassLoader。
 *      3. 在双亲委托模型下，类加载是由上至下的，即下层的类加载器会委托上层进行加载。但是对于SPI来说，有些接口是java核心库所提供的，而java核心库是由启动类
 *          加载器来加载的，而这些接口的实现却来自不同的jar包（厂商提供），java的启动类加载器是不会加载其它来源的jar包，这样传统的双亲委托模型就无法满足
 *          SPI的要求。而通过给当前线程设置上下文类加载器，就可以由设置的上下文类加载器来实现对于接口实现类的加载。
 *      4. 线程上下文类加载器的一般使用模式（获取-使用-还原）
 *          伪代码:
 *         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 *         try {
 *             Thread.currentThread().setContextClassLoader(targetClassLoader);
 *             myMethod();
 *         }finally {
 *             Thread.currentThread().setContextClassLoader(classLoader);
 *         }
 *         （1）. myMethod里面则调用了 Thread。currentThread().getContextClassLoader()， 获取当前线程的上下文类加载器做某些事情。
 *         （2）. 若果一个类由类加载器A加载，那么这个类的依赖类也是由相同的类加载器加载的（如果该依赖类之前没有被加载过的话）
 *                  ContextClassLoader 的作用就是为了破坏Java的类加载委托机制
 *         （3）. 当高层提供了统一的接口让低层去实现，同时又要在高层加载（或实例化） 低层的类时，就必须要通过线程上下文类加载器来帮助高层的
 *                  ClassLoader找到并加载该类
 *
 *
 *
 */
public class ClassLoaderTest11 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());

        System.out.println("=========================");
        System.out.println();

        /**
         * 此处输出 driver:  class com.mysql.cj.jdbc.Driver, loader: sun.misc.Launcher$AppClassLoader
         * 若使用 双亲委托模型 应该是输出 null  因为 ServiceLoader 的类加载器是 启动类加载器
         * 详情 需看  {@link ServiceLoader} 源码
         */
        // 将扩展类加载器赋值给上线文类加载器   while 循环不会执行
//        Thread.currentThread().setContextClassLoader(Thread.currentThread().getContextClassLoader().getParent());
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterable = loader.iterator();

        while (iterable.hasNext()){
            Driver driver = iterable.next();
            System.out.println("driver:  " + driver.getClass() + ", loader: " + driver.getClass().getClassLoader());
        }
        System.out.println("当前线程上下文类加载器：  " + Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader 的类加载器：  " + ServiceLoader.class.getClassLoader());


    }


}
