package com.biao.mall.logistic.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * @Classname HelloJob
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 18:28
 * @Version 1.0
 **/
public class HelloJob implements Job, Serializable {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(System.currentTimeMillis()+"...helloWorld");
    }
}
