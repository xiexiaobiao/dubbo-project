package com.biao.mall.order.conf;

import com.biao.mall.common.dubbo.AccountDubboService;
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
/*    @Bean
    public ReferenceBean<AccountDubboService> accountDubboService(){
        ReferenceBean<AccountDubboService> dubboAccountServiceBean = new ReferenceBean<>();
        dubboAccountServiceBean.setVersion("1.0.0");
        dubboAccountServiceBean.setInterface(AccountDubboService.class);
        dubboAccountServiceBean.setTimeout(5000);
        dubboAccountServiceBean.setCheck(false);
        return dubboAccountServiceBean;
    }*/

}
