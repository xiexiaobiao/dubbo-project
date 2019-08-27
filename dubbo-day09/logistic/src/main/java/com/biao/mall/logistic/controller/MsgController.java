package com.biao.mall.logistic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.MqCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MsgController
 * @Description 统一接收mq消息
 * @Author xiexiaobiao
 * @Date 2019-08-24 21:38
 * @Version 1.0
 **/
@RestController
public class MsgController {

    private MqCenter mqCenter;

    @Autowired
    public MsgController(MqCenter mqCenter){
        this.mqCenter = mqCenter;
    }

    @PostMapping("/mq/msg")
    public ResEntity<String> msgHandle(@RequestBody String jsonStr){
        JSONObject jsonObject = new JSONObject();
        jsonObject = (JSONObject) JSON.parse(jsonStr);
        String body = jsonObject.getString("data");
        Map<String,Object> map = new HashMap<>();
        map.put("data",body);
        mqCenter.sendMsg2MQ(map);
        //响应封装
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SUCCESS_CODE);
        resEntity.setMsg(ResConstant.SUCCESS_STRING);
        resEntity.setData("msg received.");
        return resEntity;
    }
}
