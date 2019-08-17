package com.biao.mall.business.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.dao.DubboOrderDetailDao;
import com.biao.mall.common.entity.DubboOrderDetailEntity;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.dao.DubboOrderDao;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.service.DubboStockService;
import com.biao.mall.common.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private Exception NotEnoughStockException;

    @Reference(version = "1.0.0")  //dubbo 引用服务
    private DubboStockService stockService;

    @Autowired
    public DubboOrderServiceImpl(DubboOrderDao orderDao,DubboOrderDetailDao orderDetailDao){
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
    }

    @Override
    public boolean saveOrder(DubboOrderEntity orderEntity) throws Exception {
        //更新订单DB
        orderDao.insert(orderEntity);
        //获取商品信息
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"order_id",orderEntity.getOrderId());
        List<DubboOrderDetailEntity> orderDetailEntityList = orderDetailDao.selectList(qw);
        //更新库存DB
        DubboStockEntity stockEntity;
        for ( DubboOrderDetailEntity detail: orderDetailEntityList
             ) {
            stockEntity = stockService.getStockEntity(detail.getItemId());
            if (detail.getQuantity() <= stockEntity.getQuantity() - stockEntity.getQuantityLock()){
                if (orderEntity.getPaid()){
                    stockEntity.setQuantity(stockEntity.getQuantity() - detail.getQuantity());
                    stockEntity.setGmtModified(TimeUtil.getTimeNow());
                    stockService.updateStock(stockEntity);
                }
                if( !orderEntity.getPaid()) {
                    stockEntity.setQuantityLock(stockEntity.getQuantityLock() + detail.getQuantity());
                    stockEntity.setQuantity(stockEntity.getQuantity() - detail.getQuantity());
                    stockEntity.setGmtModified(TimeUtil.getTimeNow());
                    stockService.updateStock(stockEntity);
                }
            }else {
                throw NotEnoughStockException;
            }
        };
        /*lamda 表达式中的变量只能是atomic 或 final 的 */
/*        orderDetailEntityList.forEach( detail -> {
            stockEntity = stockService.getStockEntity(detail.getItemId());
            stockEntity.setQuantityLock(stockEntity.getQuantityLock() + detail.getQuantity());
            stockEntity.setGmtModified(TimeUtil.getTimeNow());
            stockService.updateStock(stockEntity);
        });*/
        return true;
    }


}
