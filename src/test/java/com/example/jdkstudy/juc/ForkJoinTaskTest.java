package com.example.jdkstudy.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskTest {

    @Test
    public void test() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        MyTask myTask = new MyTask(1, 10);
        int result = forkJoinPool.invoke(myTask);
        System.out.println(result);
        forkJoinPool.shutdown();
    }


}

class MyTask extends RecursiveTask<Integer> {

    private int limit = 4;
    private int firstIndex;
    private int lastIndex;

    public MyTask(int firstIndex, int lastIndex) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        int gap = lastIndex - firstIndex;
        boolean flag = gap <= limit;
        if (flag) {
            // 不可拆分的任务执行流程 ， 最小任务执行
            System.out.println(Thread.currentThread().getName());
            for (int i = firstIndex; i <= lastIndex; i++)
                result += i;

        } else {
            // 任务拆分
            int middleIndex = (firstIndex + lastIndex) / 2;
            MyTask leftTask = new MyTask(firstIndex, middleIndex);
            MyTask rightTask = new MyTask(middleIndex + 1, lastIndex);
            invokeAll(leftTask, rightTask);
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            result = leftResult + rightResult;
        }
        return result;
    }
}