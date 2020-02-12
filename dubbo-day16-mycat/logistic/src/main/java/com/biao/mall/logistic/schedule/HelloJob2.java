package com.biao.mall.logistic.schedule;

import com.biao.mall.common.service.DubboDeliveryService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Classname HelloJob2
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 18:37
 * @Version 1.0
 **/
@Component
@DisallowConcurrentExecution //标识这个任务是串行执行，不是并发执行
public class HelloJob2 implements Job, Serializable {

    @Autowired
    private DubboDeliveryService deliveryService;

    /*经测试，下面这中构造方法注入deliveryService的方式会导致NPE！*/
/*    @Autowired
    public HelloJob2(DubboDeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    public HelloJob2(){}
*/
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        int num = deliveryService.checkDelayed();
        System.out.println("delayed num is >>> "+ num);
    }
}
