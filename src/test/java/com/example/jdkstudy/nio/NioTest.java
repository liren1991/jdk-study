package com.example.jdkstudy.nio;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NioTest {

    @Test
    public void test1(){
        IntBuffer intBuffer = IntBuffer.allocate(10);
        System.out.println("capacity: " + intBuffer.capacity());

        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            intBuffer.put(randomNumber);
        }

        System.out.println("before flip limit: " + intBuffer.limit());
        intBuffer.flip();

        System.out.println("after flip limit: " + intBuffer.limit());
        System.out.println();
        while (intBuffer.hasRemaining()){
            System.out.println("position: " + intBuffer.position());
            System.out.println(" limit: " + intBuffer.limit());
            System.out.println(" capacity: " + intBuffer.capacity());
            System.out.println(intBuffer.get() );
        }
    }

    @Test
    public void test2() throws IOException {
        FileInputStream in = new FileInputStream("input.txt");
        FileOutputStream out = new FileOutputStream("output.txt");

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true){
            buffer.clear();  // 注释此行有惊喜
            int read = inChannel.read(buffer);
            System.out.println("read:  " + read);
            if (-1 == read)
                break;

            buffer.flip();
            outChannel.write(buffer);
        }
        inChannel.close();
        outChannel.close();
    }




}
