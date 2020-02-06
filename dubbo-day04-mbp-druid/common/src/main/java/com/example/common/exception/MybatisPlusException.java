package com.example.common.exception;

/**
 * @Classname MybatisPlusException
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-03 21:36
 * @Version 1.0
 **/
public class MybatisPlusException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public MybatisPlusException(String msg){
        super(msg);
    }
}
