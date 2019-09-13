package com.biao.mall.logistic.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
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

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ResEntity<String> saveOneDelivery(@RequestBody String jsonString) throws BlockException {
//        Method method = this.getClazz().getMethod("saveOneDelivery");
        String resourceName = "saveOneDelivery";
        this.initWhiteRules(resourceName);
        //获取app来源
        ContextUtil.enter(resourceName,"mall-business");
        try(Entry entry = SphU.entry(resourceName)){
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

        /**白名单规则*/
        private void initWhiteRules(String resourceName){
            AuthorityRule rule = new AuthorityRule();
            rule.setResource(resourceName);
            rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
            rule.setLimitApp("appA,appE");
            AuthorityRuleManager.loadRules(Collections.singletonList(rule));
        }



}

