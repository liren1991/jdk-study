package com.example.jvm;

public class ClassLoaderTest6 {
    public static void main(String[] args) {
        // 使用属性所在的类即为主动使用类， 何其调用对象无关 ，此处并不会初始化 Child6
        System.err.println(Child6.a);
        System.err.println("---------------------");
        Child6.doSomething();
    }


}

class Parent6{
    static int a = 3;
    static {
        System.err.println("Parent6 类初始化");
    }
    static void doSomething(){
        System.err.println("doSomething 方法调用");
    }
}

class Child6 extends Parent6{
    static {
        System.err.println("Child6 类初始化");
    }
}

