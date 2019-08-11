package com.biao.mall.common.order.dao;

import com.biao.mall.common.order.entity.OmsOrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单中所包含的商品 Mapper 接口
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Repository
@Mapper
public interface OmsOrderItemDao extends BaseMapper<OmsOrderItemEntity> {

}
