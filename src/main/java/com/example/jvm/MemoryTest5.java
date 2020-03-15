package com.example.jvm;

/**
 * 1. 程序计数器
 * 2. 本地方法栈
 * 3. java虚拟机栈（jvm stack）： java虚拟机栈描述的是java方法的执行模型，每个方法执行的时候都会创建一个栈帧（frame）栈用于存放局部变量表、操作栈、动态链接、方法出口等信息。
 *      一个方法的执行过程就是这个方法对栈帧的入栈出栈过程。
 * 4. 堆（Heap）：堆是存放对象实例的，是Java虚拟机管理内存最大的一块。GC主要的工作区域，为了高效的GC，会把堆细分更多的子区域。
 * 5. 方法区： 存放了每个Class的结构信息，包括常量池、字段描述、方法描述。
 */

/**
 * 创建一个对象： 例  Object obj = new Object()
 *      1. 生成两部分的内存区域：
 *          （1）. 引用变量，因为是方法内的变量，放到JVM Stack里面。
 *          （2）. 实例变量，真正类型的class的实例对象，放在 heap 里面。
 *      2. 示例中 new 语句一共消耗12个 bytes ,jmv 规定引用占用4个bytes(在jvm stack)，而空对象是8个bytes(在 heap)。
 *      3. 方法结束后，对应Stack中的变量马上回收，但是heap中的对象要等GC来回收。
 */

/**
 * jvm 垃圾回收（GC）模型
 *  1. 垃圾判断算法
 *  2. GC算法
 *  3. 垃圾回收期的实现和选择
 *
 * 垃圾判断的算法
 *  1. 引用计数算法（Reference Counting）：
 *     （1）. 给对象添加一个引用计数器，当有一个地方引用它，计数器 +1，当引用失效，计数器 -1，任何时刻计数器为0的对象就是不可能再被使用。
 *     （2）. 引用计数算法无法解决对象循环引用的问题。
 *  2. 根搜索算法（Root Tracing）：
 *      （1）. 在实际的生产语言中（java、c#等），都是使用根搜索算法判定对象是否存活。
 *      （2）. 算法基本思路就是通过一系列被称作 ”GC Roots“ 的点作为起始进行乡下搜索，当一个对象到 GC Roots 没有任何引用链（Reference Chain）相连，
 *              则证明此对象是不可用的。
 *      （3）. GC Roots： jvm栈（帧中的本地变量）中的引用、方法区中的静态变量、JNI（即一般说的 native 方法） 中的引用。
 */

/**
 * 方法区：
 *  1. java虚拟机规范表示可以不要求虚拟机在方法区实现GC，方法区GC的 “性价比” 一般比较低
 *  2. 在堆中，尤其是在新生代，常规应用进行一次GC一般可以回收 70%-95% 的空间，而方法区的GC效率远小于此。
 *  3. 当前的商业jvm都有实现方法区的GC，主要回收两部分内容：废弃常量与无用类。
 *  4. 类的回收需要满足以下3个条件：
 *      （1）. 该类所有的实例都已经被GC，也就是JVM中不存在该类Class的任何实例。
 *      （2）. 加载该类的 ClassLoader 已经被GC。
 *      （3）. 该类的 java.lang.Class 对象没有在任何地方被引用，如不能在任何地方通过反射访问该类的方法。
 *  5. 在大量使用反射、动态代理、GCLib等字节码框架、动态生成JSP以及OSGi这类频繁自定义ClassLoader的场景都需要JVM具备类卸载的支持以保证方法区不会溢出。
 */

/**
 * 常见GC算法
 *  1. 标记-清除算法（Mark-Sweep）
 *      （1）.解释： 算法分为 “标记” 和 “清除” 两个阶段，首先标记出所有需要回收的对象，然后回收所有需要回收的对象 。
 *      （2）.缺点： 1.效率问题: 标记和清除两个过程效率都不高（需要扫描所有对象。堆越大，GC越慢）；
 *                  2.空间问题: 标记清理之后会产生大量不连续的内存碎片，空间碎片太多可能会导致后续使用中无法找到足够的连续内存而提前出发另一次的垃圾收集动作（内存碎片问题，GC次数越多，碎片严重）；
 *
 *  2. 标记-整理算法（Mark-Compact）
 *      （1）.解释： 标记过程仍然一样，但是后续步骤不是进行直接清理，而是令所有存活对象向一端移动，然后直接清理掉这端边界以外的内存。
 *      （2）.优点： 没有内存碎片。
 *      （3）.缺点： 比 Mark-Sweep 耗费更多的时间进行 Compact。
 *  3. 复制算法（Copying）
 *      （1）.解释： 将可用内存划分为两块，每次只适用其中的一块，当半区内存用完了，仅将还存活的对象复制到另一块半区上面，然后就把原来整块内存空间一次性清理掉。
 *                      这样使得每次内存回收都是对整个半区的回收，内存分配时也就不用考虑内存碎片等复杂情况，主要移动堆顶点指针，按顺序分配内存就可以了，实现简单，运行效率高。
 *      （2）.缺点： 1.这种算法的代价是将内存缩小为原来的一半，代价高昂；
 *                  2.复制收集算法在对象存活率高的时候，效率有所下降；
 *                  3.如果不想浪费百分之五十的空间，就需要有额外的空间进行分配担保用于应付半区内存中所有对象都能百分之百存活的极端情况，所以在老年代一般不能直接使用这种算法。
 *      （3）.优点： 1.只需要扫描存活的对象，效率更高；
 *                  2.不会产生内存碎片；
 *      （4）.说明： 1.现在的商业虚拟机中都是用了这一种收集算法来回收新生代。
 *                  2.将内存分为一块较大的 Eden 空间和2块较小的 Survivor 空间，每次使用 Eden和其中一块 Survivor，当回收时将 Eden和Survivor还存活的对象一次性拷贝到另一块 Survivor 空间上，然后清理掉Eden和使用过的Survivor空间。
 *                  3.Oracle HostPot 虚拟机默认的 Eden 和 Survivor的大小比例是 8:1:1,也就是说每次只有百分之十的内存是 “浪费” 的。
 *                  4.复制算法非常适合生命周期比较短的对象，因为每次GC总能回收大部分的对象，复制的开销比较小。
 *                  5.根据IBM的专门研究，百分之九十八的java对象只会存活1个GC周期，这些对象很适合用复制算法。而且不用 1:1 划分工作区和复制区的空间。
 *
 *  4. 分代算法（Generation）
 *      （1）.解释： 一般是把java堆分作新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法，比如新生代每次GC都有大批对象死去，只有少量存活，那就选用复制算法只需要付出少量存活对象的复制成本就可以完成收集。
 *      （2）.说明： 1.当前商业虚拟机的垃圾收集都是采用 ”分代收集（Generational Collecting）“ 算法，根据对象不同的存活周期将内存划分为几个块。
 *                  2.HotSpot jvm6中共划分为三个代： 年轻代（Young Generation）、老年代（Old Generation）、永久代（Permanent Generation）。
 *                  3.年轻代：
 *                      （1）.新生成的对象都放在年轻代。年轻代用复制算法进行GC（理论上，年轻代对象的生命周期非常短，所以适合复制算法）。
 *                      （2）.年轻代分为三个区: 一个Eden区，两个Survivor区（可以通过参数进行设置Survivor个数）。对象在Eden区中生成，当Eden区满时，还存活的对象将被复制到一个Survivor区，当这个Survivor区满时，
 *                              此区的存活对象将被复制到一个外一个Survivor区，当第二个Survivor区也满了时，从第一个Survivor区复制过来的并且此时还存活的对象将被复制到老年代。两个Survivor区是完全对称的，轮流替换。
 *                  4.老年代：
 *                      （1）.存放了经过一次或多次GC还存活的对象
 *                      （2）.一般采用Mark-Sweep或者Mark-Compact算法进行GC
 *                      （3）.有多种垃圾收集器可以选择。每种垃圾收集器可以看做一个GC算法的具体实现。可以根据具体应用的需求选用合适的垃圾收集器。
 *                 5.永久代（已废弃）：
 *                      （1）.并不属于堆（Heap），但是GC也会涉及到这个区域。
 *                      （2）.存放了每个Class的机构信息，包括常量池、字段描述、方法描述。与垃圾收集器要收集的java对象关系不大。
 *                 6.备注： 在HotSpot虚拟机中本地方法栈和jvm方法栈是同一个，因此也可以用-Xss控制。
 */

/**
 * 内存分配：
 *  1. 堆上分配：大多数情况在Eden上分配，偶尔会直接在old上分配，细节取决于GC实现。
 *  2. 栈上分配：原子类型的局部变量。
 * 内存回收：
 *  1. GC要做的是将那些死亡（dead）的对象占用的内存回收掉。
 *      （1）.HotSpot认为没有引用的对象是死亡的。
 *      （2）.HotSpot将引用分为四种： Strong（默认通过 Object o = new Object()）这种方式赋值直接引用；Soft、Week、Phantom 这三种则都是继承Reference。
 *      （3）.在Full GC时会对Reference类型的引用进行特殊处理。
 *              1.Soft：内存不过时一定会被GC，长时间不用也会被GC。
 *              2.Weak：一定会被GC，当被Mark为dead，会在ReferenceQueue中通知。
 *              3.Phantom：本来就没有引用，当从jvm heap中释放时会通知。
 */

/**
 *
 * GC的时机
 *  1.在分代模型的基础上，GC从时机上分为两种： Scavenge GC和 Full GC。
 *  2.Scavenge GC（Minor GC）
 *      （1）.触发时机：新对象生成时，Eden空间满了。
 *      （2）.理论上Eden区大多数对象会在Scavenge GC回收，复制算法的执行效率会很高，Scavenge GC时间比较短。
 *  3.Full GC
 *      （1）.对整个jvm进行整理，包括Young、Old、Perm。
 *      （2）.主要触发的时机： Old满了；Perm满了；System.gc()。
 *      （3）.效率很低，尽量减少Full GC。
 *
 * 垃圾收集器（Garbage Collector）
 *  1. 分代模型： GC的宏观愿景。
 *  2. 垃圾回收器： GC的具体实现。
 *  3. HotSpot JVM 提供了很多种垃圾收集器，我们需要根据具体应用的需要采用不同回收器。
 *  4. 没有万能的垃圾收集器，每种垃圾收集器都有字节的适用场景。
 *
 * 垃圾收集器的 “并行” 和 “并发”
 *  1. 并行（Parallel）：指多个收集器的线程同时工作，但是用户线程处于等待状态。
 *  2. 并发（concurrent）：指收集器在工作的同事，可以允许用户线程工作。并发并不代表解决了GC停顿问题，在关键的步骤还是要停顿。比如再收集器标记垃圾的时候。但在清楚的垃圾的时候，用户线程可以和GC线程并发执行。
 *
 * Serial 收集器
 *  1. 单线程收集器，收集时会暂停所有工作线程（Stop The Word，简称STW），使用复制收集算法，虚拟机运行在Client模式时的默认新生代收集器。
 *  2. 最早的收集器，单线程进行GC。
 *  3. Young和Old Generation都可以使用。
 *  4. 在年轻代，采用复制算法；在老年代采用Mark-Compact算法
 *  5. 因为是单线程GC，没有多线程切换的额外开销，简单实用。
 *  6. HotSpot Client模式缺省的收集器。
 * ParNew收集器
 *  1. ParNew收集器就是Serial的多线程版本，除了使用多个收集线程外，其余行为包括算法、STW、对象分配规则、回收策略等都与Serial收集器一模一样
 *  2. 对应的这种收集器是虚拟机运行在Server模式的默认新生代收集器，在单CPU的环境中，ParNew收集器并不会比Serial收集器效果好。
 *  3. 使用复制算法（因为针对新生代）。
 *  4. 只有在多CPU的环境下，效率才会比Serial收集器高。
 *  5. 可以通过 -XX:ParallelGCTreads来控制GC线程数的多少。需要结合具体CPU的个数。
 *  6. Server模式下新生代的缺省垃圾收集器。
 * Parallel Scavenge收集器
 *  1.Parallel Scavenge收集器也是一个多线程收集器，也是使用复制算法，但它的对象分配规则与回收策略都与ParNew收集器有所不同，
 *      它是以吞吐量最大化（即GC时间占运行时间最小）为目标的收集器实现，它允许较长时间的STW换取总吞吐量的最大。
 * Parallel Old收集器
 *  1. Parallel Scavenge收集器在老年代的实现。
 *  2. 在jvm1.6才出现Parallel Old。
 *  3. 采用多线程， Mark-Compact 算法。
 *  4. 更注重吞吐量。
 *  5. Parallel Scavenge + Parallel Old = 高吞吐量，但GC停顿时间可能不理想。
 */

/**
 * 启动参数
 * -verbose:gc
 * -Xms20m
 * -Xmx20m
 * -Xmn10m
 * -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8
 */
public class MemoryTest5 {
    public static void main(String[] args) {
        int  size = 1024*1024;
        byte[] bytes1 = new byte[2*size];
        byte[] bytes2 = new byte[2*size];
        byte[] bytes3 = new byte[2*size];
        System.out.println("hello word");

        /**
         * 输出
         * [GC (Allocation Failure) [PSYoungGen: 6652K->895K(9216K)] 6652K->4999K(19456K), 0.0295227 secs] [Times: user=0.08 sys=0.00, real=0.03 secs]
         * hello word
         * Heap
         *  PSYoungGen      total 9216K, used 3347K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *   eden space 8192K, 29% used [0x00000000ff600000,0x00000000ff864f90,0x00000000ffe00000)
         *   from space 1024K, 87% used [0x00000000ffe00000,0x00000000ffedfcb0,0x00000000fff00000)
         *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
         *  ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
         *  Metaspace       used 3073K, capacity 4556K, committed 4864K, reserved 1056768K
         *   class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
         *
         * 解释
         * [GC (Allocation Failure) [PSYoungGen: 6652K->895K(9216K)] 6652K->4999K(19456K), 0.0295227 secs] [Times: user=0.08 sys=0.00, real=0.03 secs]
         *      GC (Allocation Failure)： GC类型，Scavenge GC(Minor GC)，触发GC原因 Allocation Failure
         *      [PSYoungGen: 6652K->895K(9216K)]： PSYoungGen: 年轻代 Parallel Scavenge垃圾回收器（简写：PS）； 6652K: 新生代中存活对象所占据的空间。895K: GC回收后新生代中存活对象所占据空间。9216K: 新生代内存总计可用内存（8:1:1），有1兆始终为 toSurvivor 故只有9兆
         *      6652K->4999K(19456K)： 6652K: 执行GC之前堆空间存活对象占用内存大小。 4999K: 执行GC之后堆空间存活对象占用内存大小。 19456K: 堆空间总计可用大小。
         *      0.0295227 secs： 本次GC执行时间。
         *      user=0.08： 用户空间GC执行时间
         *      sys=0.00： 系统空间GC执行时间
         *      real=0.03： 实际GC执行时间
         * PSYoungGen      total 9216K, used 3347K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *      PSYoungGen      total 9216K, used 3347K： PSYoungGen: 年轻代 Parallel Scavenge垃圾回收器。 total 9216K: 总计空间 9兆。 used 3347K: 使用空间3兆。
         * ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *      ParOldGen       total 10240K, used 4104K： ParOldGen: 老年代 par 垃圾回收器。total 10240K: 总计10M，已使用 4104K，计算公式： 6652K-895K=5757(执行完GC后，新生代释放的空间容量)  --> 6652K-4999K=1653(执行完GC后，总的堆空间释放的空间容量）  --> 5757-1653=4104
         */
    }
}
