package com.example.jdktest.jvm.entity;

public class MySample {
    public MySample() {
        System.out.println("MySample is init " + this.getClass().getClassLoader());
        new MyCat();
    }
}
