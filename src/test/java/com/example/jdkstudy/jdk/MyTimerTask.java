package com.example.jdkstudy.jdk;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "  测试》》》》》定时任务执行！！！！  当前时间： " + System.currentTimeMillis());
    }
}
