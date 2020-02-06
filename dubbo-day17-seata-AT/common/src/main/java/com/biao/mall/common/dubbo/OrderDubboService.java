package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.response.ObjectResponse;

/**
 * @Classname OrderDubboService
 * @Description 订单服务
 * @Author xiexiaobiao
 * @Date 2019-10-04 10:59
 * @Version 1.0
 **/
public interface OrderDubboService {
    /**创建订单*/
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);
}
