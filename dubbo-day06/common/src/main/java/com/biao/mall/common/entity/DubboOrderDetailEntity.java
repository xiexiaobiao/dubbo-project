package com.biao.mall.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_order_detail")
public class DubboOrderDetailEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private Long id;

    private Date grmtCreate;

    private Date gmtModified;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 数量
     */
    private Integer quantity;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGrmtCreate() {
        return grmtCreate;
    }

    public void setGrmtCreate(Date grmtCreate) {
        this.grmtCreate = grmtCreate;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
