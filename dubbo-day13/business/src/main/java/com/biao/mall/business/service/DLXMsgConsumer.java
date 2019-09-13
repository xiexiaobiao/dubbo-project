package com.biao.mall.business.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.conf.RabbitConf;
import com.biao.mall.common.service.DubboOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname DLXMsgConsumer
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-29 19:39
 * @Version 1.0
 **/
@Service
//@XSlf4j
public class DLXMsgConsumer {

    private DubboOrderService orderService;

    @Autowired
    public DLXMsgConsumer(DubboOrderService orderService){
        this.orderService = orderService;
    }

    //特别注意，rabbitMQ的handler方法只能是void！！
    @RabbitListener(queues = RabbitConf.DLQ_QUEUE_B,containerFactory = "customContainerFactory")//启用线程池
    @RabbitHandler
    public void process(String jsonStr) throws BlockException {
        JSONObject jsonObject = (JSONObject) JSON.parse(jsonStr);
//        String orderId = (String) jsonObject.get("orderId"); long不能使用(String)long
        String orderId = String.valueOf(jsonObject.get("orderId"));
        //取消未付订单
        orderService.cancelOrder(orderId);
    }
}
