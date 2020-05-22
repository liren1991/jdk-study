package com.example.jvm;

/**
 * 启动参数
 -verbose:gc
 -Xms20m
 -Xmx20m
 -Xmn10m
 -XX:+PrintGCDetails
 -XX:SurvivorRatio=8
 */
public class MemoryTest6 {
    public static void main(String[] args) {
        // 注： 数组长度根据JKD版本可能需要变化
        int  size = 1024*1024;
        byte[] bytes1 = new byte[2*size];
        byte[] bytes2 = new byte[3*size];

        // 不会执行 full GC 原因为：大对象无法在新生代分配，故直接分配至老年代中
//        byte[] bytes3 = new byte[4*size];
//        byte[] bytes4 = new byte[4*size];

        // 会执行full GC
        byte[] bytes3 = new byte[3*size];
        byte[] bytes4 = new byte[3*size];
    }

    /**
     * 输出：
     * [GC (Allocation Failure) [PSYoungGen: 7676K->895K(9216K)] 7676K->6023K(19456K), 0.0031401 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [Full GC (Ergonomics) [PSYoungGen: 895K->0K(9216K)] [ParOldGen: 5128K->5895K(10240K)] 6023K->5895K(19456K), [Metaspace: 3061K->3061K(1056768K)], 0.0064995 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     * Heap
     *  PSYoungGen      total 9216K, used 6548K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *   eden space 8192K, 79% used [0x00000000ff600000,0x00000000ffc652d0,0x00000000ffe00000)
     *   from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     *  ParOldGen       total 10240K, used 5895K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   object space 10240K, 57% used [0x00000000fec00000,0x00000000ff1c1e98,0x00000000ff600000)
     *  Metaspace       used 3068K, capacity 4556K, committed 4864K, reserved 1056768K
     *   class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
     *
     * [Full GC (Ergonomics) [PSYoungGen: 895K->0K(9216K)] [ParOldGen: 5128K->5895K(10240K)] 6023K->5895K(19456K), [Metaspace: 3061K->3061K(1056768K)], 0.0064995 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     *  Full GC (Ergonomics)： full GC 垃圾回收
     * PSYoungGen: 895K->0K(9216K)： {@link MemoryTest5}
     * [ParOldGen: 5128K->5895K(10240K)]： ParOldGen:Parallel Old （并行的老年代垃圾收集器）； 5128K:GC回收前老年代中存活对象所占据空间；
     *                                      5895K:GC回收后老年代中存活对象所占据空间（回收后比回收前大是因为老年代GC回收性价比不高并且 Full GC 会触发 Scavenge GC，使新生代对象晋升老年代）；
     *  ：
     */
}
