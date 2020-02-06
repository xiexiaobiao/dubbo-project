package com.biao.mall.storage.service;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.entity.ProductEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
public interface ProductService extends IService<ProductEntity> {
    /**
     * 扣减库存
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
