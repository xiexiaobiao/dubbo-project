package com.biao.mall.business.controller;


import com.biao.mall.business.service.SimpleMqService;
import com.biao.mall.common.order.service.OmsOrderService;
import com.biao.mall.common.order.entity.OmsOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/order")
public class OmsOrderController {

    private OmsOrderService orderService;
    private SimpleMqService simpleMqService;

    @Autowired
    public OmsOrderController(OmsOrderService orderService,SimpleMqService simpleMqService){
        this.orderService = orderService;
        this.simpleMqService = simpleMqService;
    }

    @RequestMapping("/one")
    public void submitOrder(@RequestBody OmsOrderEntity orderEntity) throws IOException, TimeoutException {
        orderService.submitOneOrder(orderEntity);
        //库存异步操作

    }

    @RequestMapping("/smq")
    public void submitOrder() throws IOException, TimeoutException {
        //库存异步操作
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simpleMqService.setSimpleModelMsg(System.currentTimeMillis()+">>> please take action to handle the stock.");
        }

    }
}

