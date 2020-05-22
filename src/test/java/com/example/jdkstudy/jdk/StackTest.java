package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 栈  先进后出  底层基于 Vector 线程安全的 效率低下
 */
public class StackTest {

    @Test
    public void test(){
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(stack.pop());
        }
    }

}
