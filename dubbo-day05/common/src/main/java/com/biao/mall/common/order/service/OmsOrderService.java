package com.biao.mall.common.order.service;

import com.biao.mall.common.order.entity.OmsOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
public interface OmsOrderService extends IService<OmsOrderEntity> {

    boolean saveOneOrder(OmsOrderEntity orderEntity);

    boolean submitOneOrder(OmsOrderEntity orderEntity);

    boolean saveBatchOrder(List<OmsOrderEntity> orderList);

    boolean deleteOneOrder(OmsOrderEntity orderEntity);

    boolean deleteBatchOrder(List<OmsOrderEntity> orderList);

    boolean modifyOneOrder(OmsOrderEntity orderEntity);

    boolean modifyBatchOrder(List<OmsOrderEntity> orderList);

}
