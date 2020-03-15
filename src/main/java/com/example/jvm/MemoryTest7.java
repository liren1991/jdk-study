package com.example.jvm;

/**
 * -verbose:gc
 * -Xms20m
 * -Xmx20m
 * -Xmn10m
 * -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=4194304
 * -XX:+UseSerialGC
 * 注： -XX:PretenureSizeThreshold 与 -XX:+UseSerialGC需联合使用
 */

/**
 * -XX:PretenureSizeThreshold： 设置对象超过多大时直接在老年代进行分配
 * -XX:MaxTenuringThreshold： 在可以自动调节对象晋升（Promote） 到老年代阈值的GC中，设置该阈值的最大值。该参数的默认值为15，CMS 中默认值为5，G1 中默认值为15（在JVM中，该数值是由4个bit来表示的，所以最大值 1111，即15）。
 * 说明： 经理多次GC后，存活的对象会在From Survivor 与 To Survivor之间来回存放，而这里面的一个前提则是这两个空间有足够大的容量来存放这些数据，在GC算法中，
 *          会计算每个对象年龄的大小，如果达到某个年龄后发现总大小已经大于了 Survivor 空间的百分之五十，那么这时候就需要调整阈值，不在继续等到默认的15次GC后才完成晋升，
 *          因为这样会导致 Survivor 空间不足，所以需要调整阈值，让这些存活对象尽快完成晋升。
 *
 */
public class MemoryTest7 {
    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] bytes1 = new byte[2 * size];
        byte[] bytes2 = new byte[2 * size];
//        byte[] bytes3 = new byte[2 * size];
        byte[] bytes4 = new byte[3 * size];
    }
    /**
     * 运行参数
     * -verbose:gc
     * -Xms20m
     * -Xmx20m
     * -Xmn10m
     * -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     * -XX:+PrintCommandLineFlags
     * -XX:+PrintTenuringDistribution
     * -XX:MaxTenuringThreshold=5
     *
     * 输出结果分析
     * Desired survivor size 1048576 bytes, new threshold 5 (max 5)： Desired survivor size 1048576 bytes:所需要的的内存空间 1M; new threshold 5: 运行时动态计算出的结果，最大 -XX:MaxTenuringThreshold 的值; (max 5): -XX:MaxTenuringThreshold 的值。
     */
}
