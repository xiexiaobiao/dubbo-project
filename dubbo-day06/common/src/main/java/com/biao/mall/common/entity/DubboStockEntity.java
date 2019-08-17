package com.biao.mall.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存表
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_stock")
public class DubboStockEntity implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 锁定数量
     */
    private Integer quantityLock;


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

    public Integer getQuantityLock() {
        return quantityLock;
    }

    public void setQuantityLock(Integer quantityLock) {
        this.quantityLock = quantityLock;
    }

    @Override
    public String toString() {
        return "DubboStockEntity{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", quantityLock=" + quantityLock +
                '}';
    }
}
