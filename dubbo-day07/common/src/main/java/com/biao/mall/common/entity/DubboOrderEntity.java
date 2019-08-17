package com.biao.mall.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;


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
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_order")
public class DubboOrderEntity implements Serializable {

    private static final long serialVersionUID=2145264L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

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

    /**
     * 用户id
     */
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
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
                ", userId='" + userId + '\'' +
                '}';
    }
}
