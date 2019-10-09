package com.biao.mall.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname BaseResponse
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-04 11:08
 * @Version 1.0
 **/
@Data
public class BaseResponse implements Serializable {
    private int status = 200;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
