package com.biao.mall.common.entity;

import java.math.BigDecimal;
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
 * 银行账户表
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dubbo_finance")
public class DubboFinanceEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表ID
     */
    private String financeId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String userId;

    /**
     * 余额
     */
    private BigDecimal balance;


}
