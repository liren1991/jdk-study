package com.example.current;

import java.util.stream.IntStream;

public class LockTest {
    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
        IntStream.range(0,10).forEach(i->new Thread(()->{
            try {
                boundedBuffer.put("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

}
