package com.biao.mall.business.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.biao.mall.common.dao.DubboOrderDetailDao;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.dao.DubboOrderDao;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.service.DubboStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@Service
public class DubboOrderServiceImpl extends ServiceImpl<DubboOrderDao, DubboOrderEntity> implements DubboOrderService {

    private DubboOrderDao orderDao;
    private DubboOrderDetailDao orderDetailDao;

    @Autowired
    public DubboOrderServiceImpl(DubboOrderDao orderDao,DubboOrderDetailDao orderDetailDao){
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
    }

    @Reference
    private DubboStockService stockService;

//    public boolean saveNoPaidOrder(DubboOrderEntity orderEntity){
//        orderDao.insert(orderEntity);
//        DubboStockEntity stockEntity = new DubboStockEntity();
//        //fixme
//        orderDetailDao.selectById(orderEntity.getDetailId());
//        stockEntity.setItemId();
//        stockService.saveStock(stockEntity);
//        return true;
//    }
}
