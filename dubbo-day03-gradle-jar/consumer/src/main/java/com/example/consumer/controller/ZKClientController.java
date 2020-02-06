package com.example.consumer.controller;

import com.alibaba.nacos.client.utils.JSONUtils;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ListIterator;

/**
 * @Classname ZKClientController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-02 19:23
 * @Version 1.0
 **/
@RestController
public class ZKClientController {

    private ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000);

    @RequestMapping("/oneService")
    public String listOneServiceProvider(){
        List list = zkClient.getChildren("/dubbo/com.example.common.api.GreetingService/providers");
        list.forEach(item-> System.out.println(item));
        return list.toString();
    }

    @RequestMapping("/method")
    public String methodDemo(){
        List list = zkClient.getChildren("/dubbo");
        list.forEach(System.out::println);
        return list.toString();
    }




}
