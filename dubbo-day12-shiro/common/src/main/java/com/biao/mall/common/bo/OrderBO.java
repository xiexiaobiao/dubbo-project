package com.biao.mall.common.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname OrderBo
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-15 19:52
 * @Version 1.0
 **/
@Data
@ToString
public class OrderBO implements Serializable {

    private static final long serialVersionUID=1L;

    private LocalDateTime gmtCreate;

    private String orderDesc;

    private String userId;

    private List<ItemBO> itemList;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemBO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemBO> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "OrderBO{" +
                "gmtCreate=" + gmtCreate +
                ", orderDesc='" + orderDesc + '\'' +
                ", userId='" + userId + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
