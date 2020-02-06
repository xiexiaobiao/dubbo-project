package com.biao.mall.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Classname TimeUtil
 * @Description 与时间处理相关的一些方法
 * @Author xiexiaobiao
 * @Date 2019-08-14 20:00
 * @Version 1.0
 **/
public class TimeUtil {

    public static LocalDateTime getTimeNow(){
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    /**
    * @Description: date -- >  LocalDateTime
    * @param null
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-09-13 09:10
    */
    public static LocalDateTime convert2LocalTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant,zoneId);
        return localDateTime;
    }


}
