package com.biao.study.provider;

import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.runtime.api.client.ServiceClient;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import com.alipay.sofa.runtime.api.client.param.ServiceParam;
import com.biao.study.common.api.CommonHelloService;
import com.biao.study.provider.api.ClientFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AnnotationMain
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
@SpringBootApplication
public class AnnotationProviderMain {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AnnotationProviderMain.class);
        ApplicationContext applicationContext = springApplication.run(args);
        System.out.println("AnnotationProviderMain Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");


        /** 以下为api方式发布rpc服务，如使用则需要注释掉 @SofaService 注解方式*/
       /* ClientFactoryBean clientFactory = (ClientFactoryBean) applicationContext
                .getBean("clientFactory");
        // 获得 ServiceClient 对象
        ServiceClient serviceClient = clientFactory.getClientFactory().getClient(ServiceClient.class);
        // ServiceParam 对象包含发布服务所需参数
        ServiceParam serviceParam = new ServiceParam();
        // 指定接口,
        serviceParam.setInterfaceType(CommonHelloService.class);
        serviceParam.setInstance(new CommonHelloServiceImpl());
        List<BindingParam> params = new ArrayList<BindingParam>();
        BindingParam serviceBindingParam = new BoltBindingParam();
        params.add(serviceBindingParam);
        serviceParam.setBindingParams(params);
        // 调用 ServiceClient 的 service 方法，发布一个 RPC 服务
        serviceClient.service(serviceParam);*/
    }
}
