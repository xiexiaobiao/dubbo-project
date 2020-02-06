package com.biao.mall.business.controller;


import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.business.service.SimpleMqService;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.DubboOrderService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
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
public class DubboOrderController {
    private final static Logger logger = LoggerFactory.getLogger(DubboOrderController.class);

    private SimpleMqService simpleMqService;
    private DubboOrderService orderService;

    @Autowired
    public DubboOrderController(SimpleMqService simpleMqService, DubboOrderService orderService){
        this.simpleMqService = simpleMqService;
        this.orderService = orderService;
    }

    //to test the RabbitMQ
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

    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public ResEntity<String> saveOrder(@RequestBody OrderBO orderBO ) throws Exception {
        logger.debug(orderBO.toString());
        System.out.println("orderBO >>>> "+orderBO.toString());
        //存未付款订单
        orderService.saveOrder(orderBO);
        //响应封装
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SUCCESS_CODE);
        resEntity.setMsg(ResConstant.SUCCESS_STRING);
        resEntity.setData(null);
        return resEntity;
    }

    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public ResEntity<String> payOrder(@Param("orderId") String orderId){
        // 已有订单付款
        Boolean indicator = orderService.payOrder(orderId);
        //响应封装
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SUCCESS_CODE);
        resEntity.setMsg(ResConstant.SUCCESS_STRING);
        resEntity.setData(null);
        return resEntity;
    }
}

