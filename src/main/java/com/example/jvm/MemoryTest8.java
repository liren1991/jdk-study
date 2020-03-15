package com.example.jvm;

import java.util.concurrent.TimeUnit;

/**
 *运行参数
 * -verbose:gc
 * -Xmx200m
 * -Xmn50m
 * -XX:TargetSurvivorRatio=60
 * -XX:+PrintTenuringDistribution
 * -XX:+PrintGCDetails
 * -XX:PrintGCDateStamps
 * -XX:+UseConcMarkSweepGC
 * -XX:UseParNewGC
 * -XX:MaxTenuringThreshold=3
 */
public class MemoryTest8 {

    public static void main(String[] args) throws InterruptedException {
        byte[] bytes1 = new byte[512*1024];
        byte[] bytes2 = new byte[512*1024];

        createBytes();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("111111111111111111111");

        createBytes();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("222222222222222222222");

        createBytes();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("333333333333333333333");

        createBytes();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("444444444444444444444");

        byte[] bytes3 = new byte[1024*1024];
        byte[] bytes4 = new byte[1024*1024];
        byte[] bytes5 = new byte[1024*1024];

        createBytes();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("555555555555555555555");

        createBytes();
        TimeUnit.SECONDS.sleep(1);
    }
    public static void createBytes(){
        for (int i=0;i<40;i++){
            byte[] bytes1 = new byte[1024*1024];
        }
    }

    /**
     * 输出解说
     *
     * 2020-03-10T15:18:40.308+0800: [GC (Allocation Failure) 2020-03-10T15:18:40.308+0800: [ParNew
     * Desired survivor size 3145728 bytes, new threshold 3 (max 3)
     * - age   1:    1845648 bytes,    1845648 total
     * : 39938K->1831K(46080K), 0.0016863 secs] 39938K->1831K(199680K), 0.0017455 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     *
     * Desired survivor size 3145728 bytes： Desired survivor size 3145728 bytes:survivor区的目标使空间为3M（计算公式： 年轻代总内存(-Xmn50m) * 10%(默认 8:1:1) * 60%(-XX:TargetSurvivorRatio=60)）
     * new threshold 3： 动态计算出的阈值，如果已使用的内存大于 Desired survivor size 3145728 bytes 值，则重新计算。计算方式为取当前对象年龄与最大阈值的最小值。
     * (max 3)： 最大阈值 (-XX:MaxTenuringThreshold=3)
     */
}
