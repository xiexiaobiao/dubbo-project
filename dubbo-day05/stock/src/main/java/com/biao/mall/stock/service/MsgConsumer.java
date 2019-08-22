package com.biao.mall.stock.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.conf.RabbitConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//import com.eip.mq.util.HttpUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @Classname MsgConsumer
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-08 22:22
 * @Version 1.0
 **/
@Service
@RabbitListener(queues = RabbitConf.QUEUE_A,containerFactory = "customContainerFactory")//指定监听的队列
public class MsgConsumer {

    private final Logger logger = LoggerFactory.getLogger(MsgConsumer.class);
    private final static String URL = "http://localhost:8082/user/userInfo";

    @RabbitHandler //消息处理方法
    public void process(Map<String,Object> map){
        logger.info("==current service : mq==start");
        logger.info("to handle the received msg>>>> " + map + "\n  current thread name>>>>"
                + Thread.currentThread().getName()
                + "\n thread id>>>>" + Thread.currentThread().getId());
/*        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        String resultStr = HttpUtils.doPost(URL,jsonObject);*/
        String jsonString = JSON.toJSONString(map);
        sendPost(jsonString);
        logger.info("==current service: mq===end");
    }

    private void sendPost(String jsonString) {
        DataOutputStream ops = null;
        HttpURLConnection conn = null;
        try{
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            logger.info("connection created>>>>" + conn.getURL());
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            logger.info("connection get success.>>>> ");
            ops = new DataOutputStream(conn.getOutputStream());
            ops.write(jsonString.getBytes("UTF-8"));
            logger.info("data write success >>>>");
            ops.flush();
            //读取响应
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            StringBuffer stringBuffer = new StringBuffer("");
            while ((line = br.readLine()) != null){
                line = new String(line.getBytes());
                stringBuffer.append(line);
            }
            logger.info("return>>>>" + stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ops.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            conn.disconnect();
        }
    }
}
