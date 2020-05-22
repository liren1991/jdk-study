package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerTest {

    @Test
    public void test() throws InterruptedException {
        Timer timer = new Timer();
        long start = System.currentTimeMillis();
        timer.schedule(new MyTimerTask(),1000,1000);
        timer.schedule(new MyTimerTask1(),1000);
        System.out.println(System.currentTimeMillis()-start);
        TimeUnit.MINUTES.sleep(5);
    }


    @Test
    public void test1() throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask1("5000"),5000);
        timer.schedule(new MyTimerTask1("6000"),6000);
        timer.schedule(new MyTimerTask1("6000"),6000);
        timer.schedule(new MyTimerTask1("6000"),6000);
        timer.schedule(new MyTimerTask1("200000"),200000);
        timer.schedule(new MyTimerTask1("7000"),7000);
        TimeUnit.MINUTES.sleep(5);
    }
}



//    private void mainLoop() {
//      定时器本质是死循环
//        while (true) {
//            try {
//                TimerTask task;
//                  是否执行任务标识
//                boolean taskFired;
//                synchronized(queue) {
//                    // Wait for queue to become non-empty
//                    while (queue.isEmpty() && newTasksMayBeScheduled)
//                        queue.wait();
//                      没有任务是结束死循环
//                    if (queue.isEmpty())
//                        break; // Queue is empty and will forever remain; die
//
//                    // Queue nonempty; look at first evt and do the right thing
//                    long currentTime, executionTime;
//                      获取执行任务
//                    task = queue.getMin();
//                      加锁防止同一任务多次执行
//                    synchronized(task.lock) {
//                        if (task.state == TimerTask.CANCELLED) {
//                            queue.removeMin();
//                            continue;  // No action required, poll queue again
//                        }
//                        currentTime = System.currentTimeMillis();
//                        executionTime = task.nextExecutionTime;
//                        if (taskFired = (executionTime<=currentTime)) {
//                              调用 timer.schedule(new MyTimerTask1(),1000); 此种方式启动任务时，创建的任务线程只执行一次就会从任务队列中移除
//                            if (task.period == 0) { // Non-repeating, remove
//                                queue.removeMin();
//                                task.state = TimerTask.EXECUTED;
//                              调用 timer.schedule(new MyTimerTask(),1000,1000); 此种方式启动任务时，创建的任务线程不会从任务队列中移除，会间隔第三个参数，一直循环执行下去
//                            } else { // Repeating task, reschedule
//                                queue.rescheduleMin(
//                                        task.period<0 ? currentTime   - task.period
//                                                : executionTime + task.period);
//                            }
//                        }
//                    }
//                    if (!taskFired) // Task hasn't yet fired; wait
//                        queue.wait(executionTime - currentTime);
//                }
//                if (taskFired)  // Task fired; run it, holding no locks
//                    task.run();   //  重点， 调用的是run方法，而非另起一个线程，定时任务只有一个线程在执行。
//            } catch(InterruptedException e) {
//            }
//        }
//    }
