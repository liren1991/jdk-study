package com.example.current;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTest {

    public static void main(String[] args) {
//        test1();
        test4();
    }

    private static void test1() {
        Callable<Integer> callable = () -> {
            System.out.println("pre  execution");
            TimeUnit.SECONDS.sleep(5);
            int randomNumber = new Random().nextInt(500);
            System.out.println(" post execution");
            return randomNumber;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println("thread has started");
        try {
            TimeUnit.SECONDS.sleep(2);
//            System.out.println(futureTask.get(1,TimeUnit.MICROSECONDS));
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApplyAsync(value -> value + " world").join();
        System.out.println(result);

        CompletableFuture.supplyAsync(() -> "hello").thenAccept(value -> System.out.println("welcome " + value));
    }

    private static void test3() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + "  " + s2).join();
        System.out.println(result);
    }

    private static void test4() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished");
        });
        completableFuture.whenComplete((t, action) -> System.out.println("执行完成！！！"));
        System.out.println("主线程执行完毕！！！！");
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
