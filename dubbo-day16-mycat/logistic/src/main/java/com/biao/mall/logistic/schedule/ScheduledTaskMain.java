package com.biao.mall.logistic.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Classname ScheduledTaskMain
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 18:30
 * @Version 1.0
 **/
public class ScheduledTaskMain {
    public static void main(String[] args) throws SchedulerException {
        /*创建一个jobDetail的实例，将该实例与HelloJob Class绑定*/
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("HelloJob").build();
        /*创建一个触发器，每2秒执行一次任务，一直持续下去*/
        SimpleTrigger cronTrigger=  TriggerBuilder.newTrigger().withIdentity("HelloTrigger").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        /*创建schedule实例*/
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        /*将Job和trigger放入Scheduler容器*/
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
    }
}
