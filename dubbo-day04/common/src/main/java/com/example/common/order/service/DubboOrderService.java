package com.example.common.order.service;

import com.example.common.order.entity.DubboOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaobiao
 * @since 2019-08-04
 */
public interface DubboOrderService extends IService<DubboOrderEntity> {

    List<DubboOrderEntity> listAllOrder();

    boolean saveOrder(DubboOrderEntity  order);

    boolean delelteOrder(String orderId);

    boolean invalidateOrder(String orderId);
}
