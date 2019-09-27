package com.biao.mall.logistic.schedule;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname MyJobFactory
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-27 19:32
 * @Version 1.0
 **/
@Component
public class MyJobFactory extends AdaptableJobFactory {
    /**
     * AutowireCapableBeanFactory接口是BeanFactory的子类
     * 可以连接和填充那些生命周期不被Spring管理的已存在的bean实例
     */
    private AutowireCapableBeanFactory capableBeanFactory;

    @Autowired
    public MyJobFactory(AutowireCapableBeanFactory capableBeanFactory){
        this.capableBeanFactory = capableBeanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
        //调用父类方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入（Spring接管该Bean）
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
