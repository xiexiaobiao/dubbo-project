package com.biao.mall.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 快递物流表
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_delivery")
public class DubboDeliveryEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 快递单号
     */
    private String deliveryId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 订单号
     */
    private String orderId;

    private String userId;


}
