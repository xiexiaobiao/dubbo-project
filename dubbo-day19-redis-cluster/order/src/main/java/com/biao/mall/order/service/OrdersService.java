package com.biao.mall.order.service;

import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.entity.OrdersEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-05
 */
public interface OrdersService extends IService<OrdersEntity> {
    /**
     * 创建订单
     */
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);

}
