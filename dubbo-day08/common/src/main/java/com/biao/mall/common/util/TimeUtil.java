package com.biao.mall.common.util;

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
}
