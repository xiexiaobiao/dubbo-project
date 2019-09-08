package com.biao.mall.common.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Classname RedisId
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-18 21:19
 * @Version 1.0
 **/
@Component
public class RedisUitl {

    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    public RedisUitl(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
    * @Description: 直接使用简单递增方法生成ID
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-20 21:54
    */
    public String doGetId(String key){
        String date = LocalDate.now().toString().replace("-","");
        //通过转换LocalDateTime获得一个Date
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1L);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date dayAfter = Date.from(zdt.toInstant());
        //生成一个递增的ID号
        Long increment = redisTemplate.opsForHash().increment(key,date,1);
        //一天后，key自动失效，
        redisTemplate.expireAt(key,dayAfter);
        return date + String.format("%08d",increment);
    }

    /**
    * @Description: 先存储，再取值
    * @param
    * @params 
    * @return 
    * @author xiaobiao
    * @date 2019-08-23 19:48 
    */
    public String getShuffleId(){
        /*方式一，不分段存储*/
/*        redisTemplate.opsForList().rightPushAll("Cursor",stringList);
        String ID = LocalDate.now().toString().replace("-","")
                +redisTemplate.opsForList().leftPop("Cursor");
        return ID;*/
        /*方式二，分段存储*/
        if (redisTemplate.opsForValue().get("Count") == null){
            this.generateIdPool();
            //定义一个累加器，指示使用了多少个ID
            redisTemplate.opsForValue().set("Count", String.valueOf(9999));
        }
        Long incr = redisTemplate.opsForValue().increment("Count");
        if (incr == 99999){
            redisTemplate.opsForValue().set("Count", String.valueOf(9999));
            this.generateIdPool();
        }
        //取前2位作为分段List的key值
        String key = incr.toString().substring(0,2);
        //取后3位定位子List中的位置，这里缺省了substring()的结束位置，
        Integer index = NumberUtils.toInt(incr.toString().substring(2));
        /*获取某个5位id值*/
        String id = redisTemplate.opsForList().index("listCode:"+key,index);
        return LocalDate.now().toString().replace("-","")+id;
    }

    /**产生ID值，并存储到redis*/
    private void generateIdPool(){
        List<String> stringList = new ArrayList<>(90000);
        for (int i = 10000; i < 99999 ; i++) {
            stringList.add(String.valueOf(i));
        }
        /*打乱顺序*/
        Collections.shuffle(stringList);
        /*生成key，并分段保存到redis*/
        for (int i = 10; i <= 99; i++) {
            String listIndicator = "listCode:"+i;
            List<String> strList = stringList.subList((i-10)*1000,(i-10)*1000+999);
            redisTemplate.opsForList().rightPushAll(listIndicator,strList);
        }
    }


}
