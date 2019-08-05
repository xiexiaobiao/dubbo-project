package com.example.consumer.controller;

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
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()){
            String str = (String) listIterator.next();
            System.out.println("provider info: "+ str);
        }
        return list.toString();
    }

}
