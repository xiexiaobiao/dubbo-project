package com.biao.mall.order.dao;

import com.biao.mall.order.entity.OrdersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-05
 */
@Repository  //spring注解，用于注入bean
@Mapper  //ibatis注解，用于映射表和实体
public interface OrdersDao extends BaseMapper<OrdersEntity> {
    /**
     * 创建订单
     * @Param:  order 订单信息
     * @Return:
     */
    void createOrder(@Param("order") OrdersEntity order);
}
