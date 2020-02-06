package com.biao.mall.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Classname ObjectResponse
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-04 11:09
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectResponse<T> extends BaseResponse implements Serializable {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
