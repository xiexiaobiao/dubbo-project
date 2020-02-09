package com.biao.mall.business.conf;

import com.biao.mall.common.dubbo.OrderDubboService;
import com.biao.mall.common.dubbo.StorageDubboService;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ReferenceConfig
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/8
 * @Version V1.0
 **/
@Configuration
public class ReferenceConfig {
    @Bean
    public ReferenceBean<StorageDubboService> dubboServiceReferenceBean(){
        ReferenceBean<StorageDubboService> dubboStorageServiceBean = new ReferenceBean<>();
        dubboStorageServiceBean.setVersion("1.0.0");
        dubboStorageServiceBean.setInterface(StorageDubboService.class);
        dubboStorageServiceBean.setTimeout(5000);
        dubboStorageServiceBean.setCheck(false);
        return dubboStorageServiceBean;
    }

    @Bean
    public ReferenceBean<OrderDubboService> dubboOrderReferenceBean(){
        ReferenceBean<OrderDubboService> dubboOrderServiceBean = new ReferenceBean<>();
        dubboOrderServiceBean.setVersion("1.0.0");
        dubboOrderServiceBean.setInterface(OrderDubboService.class);
        dubboOrderServiceBean.setTimeout(5000);
        dubboOrderServiceBean.setCheck(false);
        return dubboOrderServiceBean;
    }
}
