package com.example.jvm;

/**
 * jcmd （从jdk1.7 开始增加的命令）
 *  1. jcmd pid VM.flags                    查看 jvm 启动参数
 *  2. jcmd pid help                        列出当前运行的java进程
 *  3. jcmd pid help JFR.dump               查看具体命令的选项
 *  4. jcmd pid PerfCounter.print           查看 jvm 性能相关参数
 *  5. jcmd pid VM.update                   查看 jvm 启动时长
 *  6. jcmd pid GC.heap_dump filename       导出 Heap dump 文件，导出的文件可以通过 jvisualvm 查看
 *  7. jcmd pid VM.system_properties        查看jvm的属性信息
 *  8. jcmd pid GC.class_histogram          查看系统中类的统计信息
 *  9. jcmd pid Thread.print                查看线程堆栈信息
 *  10. jcmd pid VM.version                 查看目标JVM进程的版本信息
 *  11. jcmd pid VM.command_line            查看 jvm 启动的命令行参数信息
 *  12. jstack                              可以查看或是导出java应用线程中的堆栈信息
 *  jcm 最好用的工具
 */
public class MemoryTest4 {
    public static void main(String[] args) {
        for (;;){
            System.out.println("hello word");
        }
    }
}
