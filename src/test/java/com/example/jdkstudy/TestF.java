package com.example.jdkstudy;

import org.junit.jupiter.api.Test;

public class TestF {
    @Test
    public void test() {
        long time = System.currentTimeMillis();
        System.out.println(time);
        System.out.println((int) time);
        System.out.println(Integer.MAX_VALUE);
        System.out.println((2147483646 & 0xf));
    }

    @Test
    public void test1() {
        int x = 1, y = 6;
        while (y-- == 6) {
            x = x + 1;
        }
        System.out.println("x=" + x + ",y=" + y);
    }

    @Test
    public void test2() {
        System.out.println(-1 << 29);
        System.out.println( 0 << 29);
        System.out.println( 1 << 29);
        System.out.println( 2 << 29);
        System.out.println( 3 << 29);
        System.out.println( (1 << 29) - 1);
        System.out.println((-1 << 29) | 0);

    }

}
