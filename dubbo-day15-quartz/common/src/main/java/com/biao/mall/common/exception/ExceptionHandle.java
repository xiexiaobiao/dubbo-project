package com.biao.mall.common.exception;

import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname ExceptionHandle
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-13 20:19
 * @Version 1.0
 **/
@ControllerAdvice //全局统一异常处理
public class ExceptionHandle {

    @ResponseBody
    @ExceptionHandler(NullPointerException.class) //处理抛出的NullPointerException
    public ResEntity<String> NPEHandle(NullPointerException e){
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SERVER_ERROR_CODE);
        resEntity.setMsg("NullPointerException");
        return resEntity;
    }

    @ResponseBody
    @ExceptionHandler(NotEnoughStockException .class) //NotEnoughStockException
    public ResEntity<String> notEnoughStockExceptionHandle(NotEnoughStockException  e){
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SERVER_ERROR_CODE);
        resEntity.setMsg("not enough stock.");
        return resEntity;
    }

/*    @ResponseBody
    @ExceptionHandler(Exception.class) //处理抛出的NullPointerException
    public ResEntity<String> exceptionHandle(Exception e){
        ResEntity<String> resEntity = new ResEntity<>();
        resEntity.setCode(ResConstant.SERVER_ERROR_CODE);
        resEntity.setMsg("Exception");
        return resEntity;
    }*/
}
