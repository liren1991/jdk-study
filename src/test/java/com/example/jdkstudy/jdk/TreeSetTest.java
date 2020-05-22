package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest {

    @Test
    public void test1(){
        TreeSet<String> treeSet = new TreeSet();
        for (int i = 0; i < 10; i++) {
            treeSet.add("set" + i);
        }

        Iterator<String> stringIterator = treeSet.iterator();
        while (stringIterator.hasNext())
            System.out.println(stringIterator.next());

    }


}
