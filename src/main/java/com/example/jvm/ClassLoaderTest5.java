package com.example.jvm;

/**
 * 类的实例化
 *     1. 为新的对象分配内存
 *     2. 为实例变量赋默认值
 *     3. 为实例变量赋正确的初始值
 *   注：
 *     1. java编译器为它变异的每一个类都至少生成一个实例初始化方法，在 java 的 class 文件中，这个类实例化方法被称为“<init>”，针对源码中的每一个类的构造方法，java编译器都产生一个<init>方法
 *     2. jvm规范允许类加载器在预料某个类将要被使用时就预先加载它，如果在预先加载的过程遇到了 .class 文件缺失或存在错误，类加载器必须在程序首次主动使用该类时才报告错误（ LinkageError 错误）
 *     3. 若这个类一直没有被程序主动使用，那么类加载器就不会报告错误
 *     4. 当java虚拟机初始化一个类时，要求他的所有父类都已经被初始化，但是这条规则并不适用于接口
 *          在初始化一个类时，并不会先初始化它所实现的接口
 *          在初始化一个接口时，并不会先初始化它的父类接口
 *          因此，一个父接口并不会因为他的子接口或者实现类的初始化而初始化，只有当程序首次使用特定的接口的静态变量时，才会导致该接口的初始化
 * 类的加载
 *      1. 类的加载的最终产品是位于内存中的Class对象
 *      2. Class 对象封装了类在方法区内的数据结构，并且向外部提供了访问方法区内的数据结构的接口
 *      3. 类加载器并不需要等到某个类被 “首次主动使用” 时载加载它
 *
 * 类加载器：
 *      1. java 虚拟机自带的加载器
 *          根类加载器（Bootstrap）
 *          扩展类加载器（Extension）
 *          系统（应用）类加载器（System）
 *      2. 在父类委托机制中，各个加载器按照父子关系形成了树形结构，除了根类加载器之外，其余的类加载器都有且只有一个父类加载器
 */
public class ClassLoaderTest5 {
    public static void main(String[] args) {
        // 在初始化一个类时，并不会先初始化它所实现的接口
        System.err.println(Child5.child5Thread);
        // 在初始化一个接口时，并不会先初始化它的父类接口
        System.err.println(Parent5_1.parent5_1Thread);
    }
}

interface Parent5{
    Thread parent5Thread = new Thread(){{System.out.println("Parent5 类加载初始化");}};
}

interface Parent5_1 extends Parent3{
    Thread parent5_1Thread = new Thread(){{System.out.println("Parent5_1 类加载初始化");}};
}

class Child5 implements Parent5_1{
    public static final Thread child5Thread = new Thread(){{System.out.println("Child5 类加载初始化");}};
}

