package com.example.jdkstudy.jdk;

import org.junit.platform.commons.util.StringUtils;

import java.util.TimerTask;

public class MyTimerTask1 extends TimerTask {
    private String name;

    public MyTimerTask1(String name) {
        this.name = name;
    }

    public MyTimerTask1() {
    }

    @Override
    public void run() {
        String var = StringUtils.isBlank(name) ? Thread.currentThread().getName() : name;
        System.out.println(var + "测试----定时任务执行！！！！  当前时间： " + System.currentTimeMillis() + "  线程执行时间：  " + this.scheduledExecutionTime());
    }
}
