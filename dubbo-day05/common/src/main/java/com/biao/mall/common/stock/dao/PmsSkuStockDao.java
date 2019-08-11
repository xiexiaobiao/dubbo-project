package com.biao.mall.common.stock.dao;

import com.biao.mall.common.stock.entity.PmsSkuStockEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * sku的库存 Mapper 接口
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Repository
@Mapper
public interface PmsSkuStockDao extends BaseMapper<PmsSkuStockEntity> {

}
