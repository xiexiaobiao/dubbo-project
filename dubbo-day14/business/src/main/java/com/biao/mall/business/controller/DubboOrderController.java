package com.biao.mall.business.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.business.service.SimpleMqService;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.DubboOrderService;
import org.apache.ibatis.annotations.Param;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public ResEntity<String> saveOrder(@RequestBody OrderBO orderBO ) throws Exception {
        logger.debug(orderBO.toString());
        this.initFlowQpsRule("saveOrder");
        try(Entry entry = SphU.entry("saveOrder",EntryType.IN)) {
            //存未付款订单
            orderService.saveOrder(orderBO);
            //响应封装
            ResEntity<String> resEntity = new ResEntity<>();
            resEntity.setCode(ResConstant.SUCCESS_CODE);
            resEntity.setMsg(ResConstant.SUCCESS_STRING);
            resEntity.setData("order saved");
            return resEntity;
        }
    }

    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public ResEntity<String> payOrder(@Param("orderId") String orderId) throws MQClientException, UnsupportedEncodingException, BlockException {
        this.initFlowQpsRule("payOrder");
        try(Entry entry = SphU.entry("payOrder",EntryType.IN)){
            // 已有订单付款
            Boolean indicator = orderService.payOrder(orderId);
            //响应封装
            ResEntity<String> resEntity = new ResEntity<>();
            resEntity.setCode(ResConstant.SUCCESS_CODE);
            resEntity.setMsg(ResConstant.SUCCESS_STRING);
            resEntity.setData("order payed");
            return resEntity;
        }
    }

    @RequestMapping(value = "/cancel",method = RequestMethod.GET)
    public ResEntity<String> cancelOrder(@Param("orderId") String orderId) throws BlockException {
        // 已有订单付款
        Boolean indicator = orderService.cancelOrder(orderId);
        //响应封装
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SUCCESS_CODE);
        resEntity.setMsg(ResConstant.SUCCESS_STRING);
        resEntity.setData("order cancel");
        return resEntity;
    }

    private void initFlowQpsRule(String resourceName) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //定义资源，resourceName只能是String类型
        rule.setResource(resourceName);
        // set limit QPS
        rule.setCount(2);
        //流控制的门槛类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置应用的名称，
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}

