package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapTest {

    @Test
    public void test1(){
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < 10; i++) {
            linkedHashMap.put("key" + i,"value" + i);
        }

        for (String str:linkedHashMap.keySet()){
            System.out.println(linkedHashMap.get(str));
        }
    }

    @Test
    public void test2(){
        LRULinkedHashMap<String,String> linkedHashMap = new LRULinkedHashMap(10,0.75f,true);
        for (int i = 0; i < 100; i++) {
            linkedHashMap.put("key" + i,"value" + i);
        }
    }

    private static final class LRULinkedHashMap<K,V> extends LinkedHashMap<K,V>{
        private int maxSize;

        public LRULinkedHashMap(int maxSize,float loadFactor,boolean accessOrder) {
            super(maxSize,loadFactor,accessOrder);
            this.maxSize = maxSize;
        }

        /**
         * 此方法为钩子方法，map插入元素时会调用此方法
         * 此方法返回true则证明删除最老的因子
         * 具体删除实现 {@link #afterNodeInsertion(boolean evict)}
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > maxSize;
        }
    }
}


