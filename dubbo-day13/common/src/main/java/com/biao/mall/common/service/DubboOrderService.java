package com.biao.mall.common.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
public interface DubboOrderService extends IService<DubboOrderEntity> {
    //未付款
    @Transactional
    boolean saveOrder(OrderBO orderBO) throws Exception;
    //已付款
    @Transactional
    boolean payOrder(String orderId) throws MQClientException, UnsupportedEncodingException;
    //取消
    @Transactional
    boolean cancelOrder(String orderId) throws BlockException;

    //付款
    @Transactional
    boolean payOrderTrans(String orderId) throws BlockException;

    //根据orderId查询订单
    DubboOrderEntity getEntityByOrderId(String orderId);

}
