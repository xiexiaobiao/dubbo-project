package com.biao.mall.common.component;

import lombok.Data;

/**
 * @Classname ResEntity
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-12 21:33
 * @Version 1.0
 **/
@Data
public class ResEntity<V> {
   private int code;
   private String msg;
   private V data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }
}
