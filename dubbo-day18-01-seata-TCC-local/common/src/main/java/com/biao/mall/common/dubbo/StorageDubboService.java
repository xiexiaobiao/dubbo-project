package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;

/**
 * @Classname StorageDubboService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-04 11:14
 * @Version 1.0
 **/
public interface StorageDubboService {
    /**
     * 扣减库存
     */
    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);
}
