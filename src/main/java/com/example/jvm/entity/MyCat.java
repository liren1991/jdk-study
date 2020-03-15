package com.example.jdktest.jvm.entity;

public class MyCat {
    public MyCat() {
        System.out.println("MyCat is init " + this.getClass().getClassLoader());
    }

}
