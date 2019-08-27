package com.biao.mall.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.awt.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class TestMain {

    public static void main(String[] args) {

        System.out.println(0b11111111111111111111111111111111);
        System.out.println(-1 << 3);
        System.out.println(~(-1 << 3));
/*        Map<String,Object> map = new HashMap<>();
        map.put("a","A");
        map.put("b","B");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("orderId","362951082266333184");
        map1.put("URL","http://localhost:8085/delivery/one");
        String jsonStr = JSON.toJSONString(map1);
        map.put("data",jsonStr);
        String jsonString = JSON.toJSONString(map.get("data"));
        System.out.println(jsonString);*/

    }
}
