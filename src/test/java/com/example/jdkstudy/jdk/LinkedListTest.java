package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {

    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        LinkedList list1 = new LinkedList();
        list1.addAll(list);
        System.out.println(list1.get(2));
    }

}
