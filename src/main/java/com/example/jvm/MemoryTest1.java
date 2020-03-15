package com.example.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 虚拟机栈 Stack Frame（栈帧）： 线程私有的，存储八大基本类型和引用类型
 * 程序计数器（Program Counter）： 线程私有的，记录线程执行的行号
 * 本地方法栈： 主要用于处理本地 native 方法
 * 堆（heap）： jvm管理的最大一块内存空间，与对相关的一个重要概念是垃圾收集器。现代几乎所有的垃圾收集器都是采用分代收集算法，所以堆空间也基于这一点进行了相应的划分：
 *                  新生代（Eden，From Survivor，To Survivor）。
 * 方法区（Method Area）： 存储元信息。永久代（Permanent Generation），从 JDK1.8 开始，已经彻底废弃了永久代，使用元空间（meta space）
 * 直接内存（Direct Memory）： 与 java NIO 密切相关， jvm 通过对堆上的 DirectByteBuffer 来操作直接内存
 *
 * new 关键字的三个步骤
 *  1. 在堆内存中创建出对象的实例
 *  2. 为对象的实例成员变量赋初始值
 *  3. 将对象的引用返回
 *
 * 对象在堆中的内存分配
 *  1. 指针碰撞： 前提是堆中的空间通过一个指针进行分割，一侧是已经被占用的空间，另一侧是未被占用的空间
 *  2. 空闲列表： 前提是堆内存空间中已经被使用与未被使用的空间是交织在一起的，这时，虚拟机就需要通过一个列表来记录哪些空间是可以使用的，哪些空间是已经被使用的，
 *                  接下来就可以容纳下新创建对象的且未被使用的空间，在此空间存放该对象，同时还要修改列表上的记录
 * 对象在内存中的布局
 *  1. 对象头
 *  2. 实例数据（一个类中所声明的各项信息）
 *  3. 对齐填充（可选）
 *
 * 引用访问对象的方式：
 *  1. 使用句柄：
 *  2. 使用直接指针的方式：
 */

/**
 * 实验参数
 * -Xms10m
 * -Xmx10m
 * -XX:+HeapDumpOnOutOfMemoryError
 */
public class MemoryTest1 {
    public static void main(String[] args) throws InterruptedException {
        List<MemoryTest1> memoryTest1List = new ArrayList<>();
//        TimeUnit.SECONDS.sleep(10);
        for (;;){
            memoryTest1List.add(new MemoryTest1());
            System.gc();
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
