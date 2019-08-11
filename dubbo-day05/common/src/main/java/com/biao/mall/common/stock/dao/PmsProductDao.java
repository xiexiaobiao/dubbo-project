package com.biao.mall.common.stock.dao;

import com.biao.mall.common.stock.entity.PmsProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Repository
@Mapper
public interface PmsProductDao extends BaseMapper<PmsProductEntity> {

}
