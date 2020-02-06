package com.biao.mall.logistic.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.DubboDeliveryService;
import com.biao.mall.logistic.util.SentinelRuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    static {
        //从Nacos动态加载规则,static标签，可以让规则只加载一次
        SentinelRuleUtil.loadFlowRules();
    }

    private DubboDeliveryService deliveryService;

    @Autowired
    public DubboDeliveryController(DubboDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @SentinelResource(value = "saveOneDelivery")
    @PostMapping("/delivery/one")
    public ResEntity<String> saveOneDelivery(@RequestBody String jsonString) throws BlockException {
//        Method method = this.getClazz().getMethod("saveOneDelivery");
//        String resourceName = "saveOneDelivery";
//        try(Entry entry = SphU.entry(resourceName)){
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

    @SentinelResource(value = "TestResource")
    @GetMapping("/delivery/test")
    public String test( )   {
        return "OK!!";
    }

}

