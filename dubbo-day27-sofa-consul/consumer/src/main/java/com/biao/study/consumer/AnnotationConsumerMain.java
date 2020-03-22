package com.biao.study.consumer;

import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import com.biao.study.common.api.CommonHelloService;
import com.biao.study.consumer.api.ClientFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @ClassName AnnotationMain
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
@SpringBootApplication
public class AnnotationConsumerMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("AnnotationConsumerMain Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SpringApplication springApplication = new SpringApplication(AnnotationConsumerMain.class);
        ApplicationContext applicationContext = springApplication.run(args);

        AnnotationClient  helloSyncServiceReference = (AnnotationClient) applicationContext
                .getBean("helloSyncServiceReference");

        while (true){
            Thread.sleep(2000L);
            System.out.println(LocalDateTime.now());
            System.out.println(helloSyncServiceReference.sayClientAnnotation("xiao biao"));
        }

        /** 以下为api方式发布rpc服务，如使用则需要注释掉 @SofaReference 注解方式*/
        /*ClientFactoryBean clientFactory = (ClientFactoryBean) applicationContext
                .getBean("clientFactoryCon");
        ReferenceClient referenceClient = clientFactory.getClientFactory().getClient(ReferenceClient.class);
        ReferenceParam<CommonHelloService> referenceParam = new ReferenceParam<>();
        // 指定接口
        referenceParam.setInterfaceType(CommonHelloService.class);

        BindingParam refBindingParam = new BoltBindingParam();
        referenceParam.setBindingParam(refBindingParam);

        CommonHelloService proxy = referenceClient.reference(referenceParam);
        proxy.sayAnnotation("xiao biao");*/

    }

}
