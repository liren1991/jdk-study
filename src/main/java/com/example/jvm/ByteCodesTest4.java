package com.example.jvm;

/**
 * 1. 栈帧： 栈帧是一种用于帮助虚拟机执行方法调用与方法执行的数据结构。栈帧本身是一种数据结构，封装了方法的局部变量表、动态链接信息、方法的返回地址及操作数栈等信息。
 * 2. 符号引用，直接引用：
 *      有些符号引用是在类加载阶段或是第一次使用时就会转换为直接引用，这种转换叫做静态解析；另外一些符号引用则是在每次运行期转换为直接引用，
 *      这种转换叫做动态链接，这体现为java的多态性。
 */

/**
 * 字节码指令
 *  1. invokeinterface： 调用接口中的方法，实际上是在运行期决定的，决定到底是调用实现该接口的哪个对象的特定方法。
 *  2. invokestatic：    调用静态法
 *  3. invokespecial：   调用自己的私有方法、构造方法（<init>）以及父类方法。
 *  4. invokevirtual：   调用虚方法，运行期动态查找的过程。
 *  5. invokedynamic：   动态调用方法。
 */

/**
 * 静态解析的4中情况：
 *  1. 静态方法
 *  2. 父类方法
 *  3. 构造方法
 *  4. 私有方法
 * 以上4种方法称作非虚方法，他们是在类加载阶段就可以将符号引用转换为直接引用的
 */

/**
 * 方法的静态分派：
 *      Grandpa father = new Father();
 *      以上代码，father的静态类型是Grandpa,而father的实际类型（真正指向的类型） 是Father。变量的静态类型是不会发生变化的，
 *      而变量的实际类型则是乐意发生变化的（多态的一种体现），实际类型在运行期方可确定。
 *
 */
public class ByteCodesTest4 {
    // 方法重载，是一种静态行为，编译期就可以完全确定
    public void test(Grandpa grandpa){
        System.out.println("grandpa");
    }
    public void test(Father father){
        System.out.println("father");
    }
    public void test(Son son){
        System.out.println("son");
    }

    public static void main(String[] args) {
        Grandpa father = new Father();
        Grandpa son = new Son();
        ByteCodesTest4 byteCodesTest4 = new ByteCodesTest4();
        byteCodesTest4.test(father);
        byteCodesTest4.test(son);
    }
}
class Grandpa{}
class Father extends Grandpa{}
class Son extends Father{}
