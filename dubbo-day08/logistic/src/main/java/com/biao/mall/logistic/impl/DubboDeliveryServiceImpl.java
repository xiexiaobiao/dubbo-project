package com.biao.mall.logistic.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.biao.mall.common.entity.DubboDeliveryEntity;
import com.biao.mall.common.dao.DubboDeliveryDao;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.service.DubboDeliveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.service.DubboOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * <p>
 * 快递物流表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@Service
public class DubboDeliveryServiceImpl extends ServiceImpl<DubboDeliveryDao, DubboDeliveryEntity>
        implements DubboDeliveryService {

    private final static Logger logger = LoggerFactory.getLogger(DubboDeliveryServiceImpl.class);

    private DubboDeliveryDao deliveryDao;

    @Reference(version = "1.0.0")
    private DubboOrderService orderService;

    @Autowired
    public DubboDeliveryServiceImpl(DubboDeliveryDao deliveryDao){
        this.deliveryDao = deliveryDao;

    }

    /**
    * @Description: 保存一个待发物流单
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-24 09:17
    */
    @Override
    public void saveLogisticSheet(String orderId){
        DubboDeliveryEntity entity = new DubboDeliveryEntity();
        entity.setOrderId(orderId);
        entity.setDeliveryId(UUID.randomUUID().toString());
        entity.setGmtCreate(LocalDateTime.now(ZoneId.systemDefault()));
        DubboOrderEntity orderEntity = orderService.getEntityByOrderId(orderId);
        entity.setUserId(orderEntity.getUserId());
        logger.debug(entity.toString());
        deliveryDao.insert(entity);
        logger.debug("one delivery is saved.");
    }
}
