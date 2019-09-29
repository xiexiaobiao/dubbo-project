package com.biao.mall.logistic.schedule;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Classname QuartzConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 19:30
 * @Version 1.0
 **/
@Configuration
public class QuartzConf {
    private MyJobFactory myJobFactory;

    @Autowired
    public QuartzConf(MyJobFactory myJobFactory){
        this.myJobFactory = myJobFactory;
    }

    @Bean(name = "factoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(){
        // Spring提供SchedulerFactoryBean为Scheduler提供配置信息,并被Spring容器管理其生命周期
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        //设置是否自动启动
        factoryBean.setAutoStartup(false);
        //设置系统启动后，Starting Quartz Scheduler的延迟时间
        factoryBean.setStartupDelay(30);
        // 设置自定义Job Factory，用于Spring管理Job bean
        factoryBean.setJobFactory(myJobFactory);
        return factoryBean;
    }

    @Bean(name = "myScheduler")
    public Scheduler getScheduler(){
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }
}
