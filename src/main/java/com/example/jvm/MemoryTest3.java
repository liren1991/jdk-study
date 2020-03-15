package com.example.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 元空间内存溢出
 *实验参数：
 * -XX:MaxMetaspaceSize=200m
 * -XX:+TraceClassLoading
 */
public class MemoryTest3 {
    public static void main(String[] args) {
        for (;;){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MemoryTest3.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor)(obj, method, args1, proxy)->proxy.invoke(obj,args1));
            System.out.println("hello world");
            enhancer.create();
        }
    }

}

