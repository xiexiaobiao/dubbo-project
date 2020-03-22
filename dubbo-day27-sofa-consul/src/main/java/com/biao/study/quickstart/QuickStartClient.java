package com.biao.study.quickstart;

import com.alipay.sofa.rpc.config.ConsumerConfig;

/**
 * @ClassName QuickStartClient
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
public class QuickStartClient {
    public static void main(String[] args) {
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setProtocol("bolt") // 指定协议
                .setDirectUrl("bolt://127.0.0.1:12200"); // 指定直连地址
        // 生成代理类
        HelloService helloService = consumerConfig.refer();
        while (true) {
            System.out.println(helloService.sayHello("world"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
