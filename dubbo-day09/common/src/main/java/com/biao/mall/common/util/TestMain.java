package com.biao.mall.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.text.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;


public class TestMain {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("a","A");
        map.put("b","B");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("orderId","362951082266333184");
        map1.put("URL","http://localhost:8085/delivery/one");
        String jsonStr = JSON.toJSONString(map1);
        map.put("data",jsonStr);
        String jsonString = JSON.toJSONString(map.get("data"));
        System.out.println(jsonString);
    }
}
