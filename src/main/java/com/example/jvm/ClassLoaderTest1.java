package com.example.jvm;


import java.util.UUID;

/**
 * 类加载：
 *      类的加载指的是将类的 .class 文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在内存中创建一个 java.lang.Class 对象（规范并未说明Class
 *      对象位于哪里， HostSpot 虚拟机将其放在了方法区中）用来封装类在方法区内的数据结构
 *      加载 .class 文件的方式
 *          1. 从本地系统中直接加载
 *          2. 通过网络下载 .class 文件
 *          3. 从 zip,jar 等归档文件中加载 .class 文件
 *          4. 从专有数据库中提取 .class 文件
 *          5. 将 java 源文件动态编译为 .class 文件（动态代理，jsp文件）
 * 一. 主动使用的七种
 *  1. 创建类的实例
 *  2. 访问某个类或接口的静态变量（只有当程序访问的静态变量后静态方法确实在当前类或当前接口中定义时，才可以认为是对类或是接口的主动使用），或者对该静态变量赋值
 *  3. 调用累得静态方法
 *  4. 反射（例： Class.forName(com.example.jdktest.jvm.ClassLoaderTest)）
 *  5. 初始化一个类的子类
 *  6. java虚拟机启动时被标记为启动类的类（java Test，启动main方法的类）
 *  7. JDK 1.7 开始提供的动态语音支持： java.lang.invoke.MethodHandle实例的解析结果 REF_getStatic, REF_putStatic, REF_invokeStatic
 *      句柄对应的类没有初始化，则初始化
 *  注： 除以上七种情况，其他使用java累得方式都被看做是对类的被动使用，都不会导致类的初始化
 *
 *
 */
public class ClassLoaderTest1 {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();

//        System.err.println(Parent2.bipsuh);
//        System.err.println(Parent2.sipush);
//        System.err.println(Parent2.icount_2);
    }

    // 主动使用 2 情况
    private static void test1(){
        System.err.println(Child1.parentStr);
    }

    // 主动使用 5 情况
    private static void test2(){
        System.err.println(Child1.childStr);
    }

    private static void test3(){
        System.err.println(Parent1_2.parentStr);
    }
    private static void test4(){
        System.err.println(Parent1_3.parentStr);
    }
}

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化。参考类加载中的 连接-解析
 * 当一个类在初始时，要求其父类全部已初始化完毕
 * -XX:+TraceClassLoading 参数, 用于追踪累得加载信息并打印出来
 * -XX:+UnTraceClassLoading 参数, 用于追踪累得加载信息并打印出来
 * -XX:+<option> , 表示开启 option 选项
 * -XX:-<option> , 表示关闭 option 选项
 * -XX:<option>=<value> , 表示将 option 选项的值设置为 value
 */
class Parent1{
    public static String parentStr = "hello world";
    static {
        System.err.println("Parent1 static block");
    }
}

class Child1 extends Parent1{
    public static String childStr = "welcome";
    static {
        System.err.println("Child1 static block");
    }
}

/**
 * final 本身表示的是常量，在编译阶段，常量就会被存入调用这个常量的方法所在的类的常量池中，
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会出发定义常量的类的初始化
 * 注： 这里指的是将常量存放到 ClassLoaderTest 的常量池中，之后 ClassLoaderTest 与 Parent2 没有任何关系，
 *     甚至可以将 Parent2 的 class 文件删除，程序依然正常运行， 可以使用 javap -c com.example.jdktest.jvm.ClassLoaderTest
 * 助记符：
 *      ldc 表示将 int，float 或是 String 类型的常量值从常量池中推送至栈顶     {@link com.sun.org.apache.bcel.internal.generic.LDC}
 *      bipush 表示将单字节（-128 至 127） 的常量推送至栈顶                  {@link com.sun.org.apache.bcel.internal.generic.BIPUSH}
 *      sipush 表示将一个短整型常量值（-32768 至 32767） 推送至栈顶          {@link com.sun.org.apache.bcel.internal.generic.SIPUSH}
 *      iconst_2 表示将 int 类型 2 推送至栈顶 （iconst_m1(-1) 至 iconst_5(5)） {@link com.sun.org.apache.bcel.internal.generic.ICONST}
 */
class Parent1_2{
    public static final String parentStr = "hello world";
    public static final short bipsuh = 127;
    public static final int sipush = 128;
    public static final int icount_2 = 2;

    static {
        System.err.println("Parent2 static block");
    }
}

/**
 * 当一个常量的值并非编译期间可以确定的，那么其值就不会被放到调用类的常量池中，
 * 这时在程序运行时，会导致主动使用这个常量所在的类，进而导致这个类被初始化
 */
class Parent1_3{
    public static final String parentStr = UUID.randomUUID().toString();
    static {
        System.err.println("Parent1_3 static block");
    }
}
