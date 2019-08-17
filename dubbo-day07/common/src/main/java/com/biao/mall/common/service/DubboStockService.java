package com.biao.mall.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.entity.DubboStockEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    int updateStockByItemId(DubboStockEntity stockEntity, String itemId);

    //此方法只能留着本地用了，QueryWrapper不支持RPC调用？？？只能自己实现一个updateStockByItemId,
    @Transactional
    int updateStock(DubboStockEntity stockEntity, QueryWrapper qw);

    @Transactional
    int saveStock(DubboStockEntity stockEntity);

    DubboStockEntity getStockEntity(String item_id);
}
