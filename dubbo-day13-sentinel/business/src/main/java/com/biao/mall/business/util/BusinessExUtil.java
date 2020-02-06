package com.biao.mall.business.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @Classname BusinessUtil
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-15 20:32
 * @Version 1.0
 **/
public class BusinessExUtil {

    public static String PayOrderExHandler(long s, BlockException ex){
        return "Oops, error occurred at PayOrder" + s;
    }
}
