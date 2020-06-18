package com.example.jdkstudy.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletionServiceTest {

    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ThreadPoolExecutor(4,10,10,
                TimeUnit.SECONDS,new LinkedBlockingDeque<>(20),new ThreadPoolExecutor.AbortPolicy());
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        IntStream.range(0,10).forEach(i->{
            completionService.submit(()->{
                TimeUnit.SECONDS.sleep((long) (Math.random()*10));
                System.out.println(Thread.currentThread().getName());
                return i*i;
            });
        });

        for (int i = 0; i <10; i++) {
            int result = completionService.take().get();
            System.out.println(result);
        }
    }


}
