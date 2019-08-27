package com.biao.mall.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Classname RestfulUtil
 * @Description jsonString中必须包含URL信息，
 * @Author xiexiaobiao
 * @Date 2019-08-24 22:04
 * @Version 1.0
 **/
public class RestfulUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestfulUtil.class);

    public static void sendMsgPost(String jsonString) {
        DataOutputStream ops = null;
        HttpURLConnection conn = null;
        try{
            JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
            logger.debug("sendMsgPost method received>>>"+jsonObject);
            URL url = new URL(jsonObject.getString("URL"));
            //传输前，去掉多余的数据项
            jsonObject.remove("URL");
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
