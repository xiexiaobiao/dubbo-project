package com.biao.mall.logistic.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.entity.DubboDeliveryEntity;
import com.biao.mall.common.dao.DubboDeliveryDao;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.service.DubboDeliveryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.service.DubboOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 快递物流表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@Service(version = "1.0.0")
@org.springframework.stereotype.Service
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
    @Transactional
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

    //检查延误未发的物流单,
    /*仅供逻辑测试，直接查找出所有10天之前的订单，生产逻辑肯定比这复杂*/
    @Override
    public int checkDelayed() {
        QueryWrapper qw = new QueryWrapper();
        LocalDateTime timeNow = LocalDateTime.now(ZoneId.systemDefault());
        qw.lt(true,"gmt_create",timeNow.minusDays(10L));
        List<DubboDeliveryEntity> list = deliveryDao.selectList(qw);
        return Objects.isNull(list)? 0: list.size();
    }


}
