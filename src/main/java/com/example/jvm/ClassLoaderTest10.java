package com.example.jvm;

import com.google.gson.Gson;

/**
 * 1. 在运行期，一个java类是由该类的完全限定名（binary name，二进制限定名）和用于加载该类的定义类加载器（definition loader）所共同决定的。
 *      如果同样的名字（即相同的完全限定名）的类是由两个不同的类加载器所加载，那么这些类就是不同的，即便 .class 文件的字节码完全一样，并且
 *      从相同的位置加载亦是如此
 * 2. 在 Oracle 的 Hotspot 实现中，系统属性 sun.boot.class.path 如果修改了，则运行会出错，提示如下错误信息：
 *      Error occurred during initialization of VM
 *      java/lang/NoClassDefFoundError: java/lang/Object
 */
public class ClassLoaderTest10 {
    public static void main(String[] args) {
        // 改变 启动类加载器，和 扩展类加载器 需显式的指定jvm启动参数
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
        /**
         * jdk 1.8 时才会打出 sun.boot.class.path  java.ext.dirs  相关信息
         *  内建于jvm中的启动类加载器会加载 java.lang.ClassLoader 以及其它的java平台类，当jvm启动时，一块特殊的机器码会运行，它会加载扩展类加载器与系统类加载器，这块特殊的机器码叫做启动类加载器
         *  启动类加载器并不是Java类，而其他的加载器则都是Java类，启动类加载器是特定于平台的机器指令，它负责开启整个加载过程
         *  所有类加载器（除了启动类加载器） 都被实现为java类。不过，总归要有一个组件来加载第一个java类加载器，从而让整个加载过程能够顺利进行下去，加载第一个纯java类加载器就是启动类加载器的职责
         *  启动类加载器还会负责加载提供JRE 正常运行所需要的基本组件，这包括 java.util 与java.lang包中的类 等等
         *
         */
        Gson gson = new Gson();
        System.out.println(gson.toJson(System.getProperties()));

    }



}
