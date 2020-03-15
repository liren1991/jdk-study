package com.example.jvm;

/**
 * 方法的动态分派
 *  1. 方法动态分派涉及到一个重要的概念： 方法接受者（方法调用者）。
 *  2. invokevirtual字节码指令的多态查找流程：
 *      找到操作数栈顶的第一个元素它所指向对象的实际类型，如果在实际类型里找到了与常量池中的方法描述符，方法名称等都完全相同并且能通过访问权限的方法就直接返回实际调用的方法直接引用，
 *      如果没有找到就沿着从下至上的继承层次继续查找，若最后仍然无法找到则抛出异常
 *  3. 比较方法重载（overload）与方法重写（overwrite）可以得到一个结论：方法重载是静态的，是编译期行为；方法重写是动态的，是运行期行为
 */

/**
 * 针对于方法调用动态分派的过程，虚拟机会在类的方法区建立一个虚方法表的数据结构（virtual method table，vtable),
 * 针对于invokeinterface指令来说，虚拟机会建立一个叫做接口方法表的数据结构（interface method table，itable）
 */
public class ByteCodesTest5 {

    public static void main(String[] args) {
           Fruit apple = new Apple();
           Fruit orange = new Orange();
           apple.test();
           orange.test();
           apple = new Orange();
           apple.test();
    }
}
class Fruit{
    public void test(){
        System.out.println("Fruit");
    }
}
class Apple extends Fruit{
    public void test(){
        System.out.println("Apple");
    }
}
class Orange extends Fruit{
    public void test(){
        System.out.println("Orange");
    }
}