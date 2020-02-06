package com.biao.mall.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.dao.OrdersDao;
import com.biao.mall.order.entity.OrdersEntity;
import com.biao.mall.order.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-05
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersEntity> implements OrdersService {


    @Override
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
      return null;
    }

}
