package com.example.springbootshiro.demo;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:这是一个日志测试类
 * @author: zheng-fx
 * @time: 2019/8/4 09:12
 */
@Controller
public class Demo2 {

    private static final Logger LOGGER = Logger.getLogger(Demo2.class);

    /**
     * task1是每隔5s执行一次，{秒} {分} {时} {日期} {月} {星期}
     */
    @Scheduled(cron = "1/5 * * * * *")
    @RequestMapping("/task")
    @ResponseBody
    public String test(String json) {

        LOGGER.debug("这是一个@Scheduled式的定时任务");

        return "hello";
    }

    /*
     * 功能描述: <br>
     * 〈task是延迟1s,每隔1S执行一次〉
     * @Param: []
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2019/8/4 0004 16:45
     */
    @Scheduled(initialDelay = 1000,fixedRate = 1*1000)
    @RequestMapping("/task1")
    @ResponseBody
    public void task2(){
        LOGGER.debug("springtask 定时任务！");
    }
    
   
    @RequestMapping("/task2")
    @ResponseBody
    public String test1() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOGGER.debug("这是一个TimerTask式的定时任务");
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 1000;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);

        return "这是一个TimerTask式的定时任务 !";
    }

    @RequestMapping("/task3")
    @ResponseBody
    public String test() {

        Runnable runnable = new Runnable() {
            public void run() {
                // task to run goes here
                LOGGER.debug("这是一个runnable式的定时任务 !!");
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);

        return "这是一个runnable式的定时任务 !";
    }

    @RequestMapping("/task4")
    @ResponseBody
    public String test4() {
        //解释：1000ms是延迟启动时间，2000ms是定时任务周期，每2s执行一次
        new Timer("testTimer").schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.debug("TimerTask");
            }
        }, 1000, 2000);

        return "nice!!!";
    }
}
    
    
    

