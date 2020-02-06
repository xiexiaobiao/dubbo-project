package com.biao.mall.logistic.schedule;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @Classname QuartzService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 19:40
 * @Version 1.0
 **/
@Service
public class QuartzService {
    private final String groupName = "group1";

    @Autowired
    @Qualifier(value = "factoryBean")
    private SchedulerFactoryBean factoryBean;

    //以下方式也可以获取bean
    // Scheduler scheduler = SpringUtil.getBean("myScheduler");
    @Autowired
    @Qualifier(value = "myScheduler")
    private Scheduler scheduler;

    // 启动 Scheduler
    public void startScheduleJobs() throws SchedulerException {
        if (this.scheduler.isStarted()){
            return;
        }
        this.setCheckScheduler(scheduler);
        this.scheduler.start();

    }

    // 停止 Scheduler
    public void stopScheduleJobs() {
        scheduler = factoryBean.getScheduler();
        try {
            if (!scheduler.isShutdown()){
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 添加 Job 并替换
    public void addJobandReplace(){
        //打印hellowrold的job
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("HelloJob").storeDurably(true).build();
        // 第二个参数为replace，是否替换存在的同名job
        // jobDetail必须是durable属性，表示任务完成之后是否依然保留到数据库，且无定义关联的trigger
        try {
            this.scheduler.addJob(jobDetail,true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    // 添加 Job 不替换
    public void addJobwithoutReplace(){
        //打印hellowrold的job
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("HelloJob").storeDurably(true).build();
        // 第二个参数为replace，是否替换存在的同名job
        try {
            this.scheduler.addJob(jobDetail,false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    // 暂停所有 Job，还可指定具体的Job
    public void pauseScheduler(){
        try {
            this.scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    // 恢复并继续所有Job执行，还可指定具体的Job
    public void resumeJobs(){
        try {
            this.scheduler.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //配置一个自定义的scheduler
    private void setCheckScheduler(Scheduler scheduler) throws SchedulerException {
        //添加HelloJob2作为任务内容
        JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class)
                .withIdentity("job1",groupName).build();
        //cron表达式制定触发规则，每10秒执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1",groupName)
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
}
