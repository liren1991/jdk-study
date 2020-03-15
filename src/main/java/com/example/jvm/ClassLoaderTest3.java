package com.example.jvm;

import java.util.Random;

/**
 * 当一个接口在初始化时，并不要求其父类接口都完成初始化（验证方法： 删除 class 文件）
 * 只有在真正使用到父类的时候（如：引用接口中所以定义的常量时），才会初始化
 *
 */
public class ClassLoaderTest3 {

    public static void main(String[] args) {
//        System.out.println(Child5.c);  // test 1

        System.err.println("counter1:  " + Singleton.counter1);
        System.err.println("counter2:  " + Singleton.counter2);

    }



}

interface Parent3{
//    int p = 5;  // test 1
    int p = new Random().nextInt(5);        // test2
}

interface Child3 extends Parent3{
//    int c = 6;    // test 1
    int c = new Random().nextInt(3);        // test2
}

class Singleton{
    public static int counter1;
    public static int counter2 = 0;
    private static Singleton singleton = new Singleton();

    public Singleton() {
        counter1 ++ ;
        counter2 ++ ;
    }
    public static Singleton getInstance(){
        return singleton;
    }
}