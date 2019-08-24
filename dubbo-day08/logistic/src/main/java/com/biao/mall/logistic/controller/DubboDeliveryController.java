package com.biao.mall.logistic.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.DubboDeliveryService;
import com.biao.mall.common.service.DubboOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

/**
 * <p>
 * 快递物流表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@RestController
public class DubboDeliveryController {

    private DubboDeliveryService deliveryService;


    @Autowired
    public DubboDeliveryController(DubboDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/delivery/one")
    public ResEntity<String> saveOneDelivery(@RequestBody String jsonString) {

         System.out.println("/delivery/one received >>> "+ jsonString);
        JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
        String orderId = jsonObject.getString("orderId");
            //保存待发一个物流单
            deliveryService.saveLogisticSheet(orderId);
            //响应封装
            ResEntity<String> resEntity = new ResEntity<>();
            resEntity.setCode(ResConstant.SUCCESS_CODE);
            resEntity.setMsg(ResConstant.SUCCESS_STRING);
            resEntity.setData("delivery received.");
            return resEntity;
        }
}

