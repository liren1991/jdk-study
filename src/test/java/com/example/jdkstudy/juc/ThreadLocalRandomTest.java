package com.example.jdkstudy.juc;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * 对于一个随机数生成器来说，有两个要素需要考量：
 *  1. 随机数生成器的种子
 *  3. 随机数生成算法（函数）
 * 对于ThreadLocalRandom来说，其随机数生成器的种子是存放在每个线程的ThreadLocal中的。
 *
 *
 *
 */
public class ThreadLocalRandomTest {

    @Test
    public void test(){
        Random random = new Random();
        IntStream.range(0,10).forEach(i->System.out.println(random.nextInt(20)));
    }

    @Test
    public void test1(){
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        IntStream.range(0,1000).forEach(i-> System.out.println(threadLocalRandom.nextInt(10)));
    }


}
