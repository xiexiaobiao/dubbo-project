package com.biao.mall.common.dao;

import com.biao.mall.common.entity.DubboDeliveryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 快递物流表 Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@Repository
@Mapper
public interface DubboDeliveryDao extends BaseMapper<DubboDeliveryEntity> {

}
