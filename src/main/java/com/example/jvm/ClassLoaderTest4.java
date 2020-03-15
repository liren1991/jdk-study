package com.example.jvm;

public class ClassLoaderTest4 {

    public static void main(String[] args) {
        Singleton1 singleton1 = Singleton1.getInstance();
        System.err.println("counter1  " + Singleton1.counter1);
        System.err.println("counter2  " + Singleton1.counter2);
    }

}

class Singleton1{
    public static int counter1;
//    public static int counter2 = 0;       // test1
    private static Singleton1 singleton1 = new Singleton1();

    public Singleton1() {
        counter1++;
        counter2++;
    }
    public static int counter2 = 0;     // test2
    public static Singleton1 getInstance(){
        return singleton1;
    }
}