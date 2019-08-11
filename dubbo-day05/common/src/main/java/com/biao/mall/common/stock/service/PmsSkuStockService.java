package com.biao.mall.common.stock.service;

import com.biao.mall.common.stock.entity.PmsSkuStockEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku的库存 服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
public interface PmsSkuStockService extends IService<PmsSkuStockEntity> {
     List<PmsSkuStockEntity> listStock();
}
