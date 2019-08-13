package com.biao.mall.common.service;

import com.biao.mall.common.entity.DubboStockEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
public interface DubboStockService extends IService<DubboStockEntity> {

    List<DubboStockEntity> listStock();

    int updateStock(DubboStockEntity stockEntity);

    int saveStock(DubboStockEntity stockEntity);
}
