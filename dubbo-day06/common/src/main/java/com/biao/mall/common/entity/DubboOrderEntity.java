package com.biao.mall.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 交易订单表
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_order")
public class DubboOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 明细id
     */
    private String detailId;

    /**
     * 是否付款
     */
    private Boolean Paid;

    /**
     * 是否过期
     */
    private Boolean isExpired;

    /**
     * 订单描述
     */
    private String orderDesc;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Boolean getPaid() {
        return Paid;
    }

    public void setPaid(Boolean paid) {
        Paid = paid;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Override
    public String toString() {
        return "DubboOrderEntity{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", orderId='" + orderId + '\'' +
                ", detailId='" + detailId + '\'' +
                ", Paid=" + Paid +
                ", isExpired=" + isExpired +
                ", orderDesc='" + orderDesc + '\'' +
                '}';
    }
}
