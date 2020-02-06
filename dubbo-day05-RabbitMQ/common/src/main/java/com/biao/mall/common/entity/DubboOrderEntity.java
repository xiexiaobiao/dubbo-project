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
    private Boolean isPaid;

    /**
     * 是否过期
     */
    private Boolean isExpired;

    /**
     * 订单描述
     */
    private String orderDesc;


}
