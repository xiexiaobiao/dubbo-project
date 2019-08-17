package com.biao.mall.business.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.bo.ItemBO;
import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.business.util.SnowFlake;
import com.biao.mall.common.dao.DubboOrderDetailDao;
import com.biao.mall.common.entity.DubboOrderDetailEntity;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.dao.DubboOrderDao;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboOrderDetailService;
import com.biao.mall.common.service.DubboOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.service.DubboStockService;
import com.biao.mall.common.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


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
    private DubboOrderDetailService orderDetailService;

    @Reference(version = "1.0.0")  //dubbo 引用服务
    private DubboStockService stockService;

    @Autowired
    public DubboOrderServiceImpl(DubboOrderDao orderDao,DubboOrderDetailDao orderDetailDao,
                                 DubboOrderDetailService orderDetailService){
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.orderDetailService = orderDetailService;
    }

    @Override
    @Transactional
    public boolean saveOrder(OrderBO orderBO) throws Exception {
        DubboOrderEntity orderEntity = new DubboOrderEntity();
        orderEntity.setGmtCreate(orderBO.getGmtCreate());
        //orderIdGenerator雪花算法，detailIdGenerator使用Redis中心式生成
        SnowFlake snowFlake = new SnowFlake(10L,1L);
        Long orderID = snowFlake.nextId();
        orderEntity.setOrderId(String.valueOf(orderID));
        orderEntity.setDetailId(UUID.randomUUID().toString());
        //detailIdGenerator使用Redis中心式生成
//        orderEntity.setDetailId(detailIdGenerator());
        orderEntity.setUserId(orderBO.getUserId());
        orderEntity.setOrderDesc(orderBO.getOrderDesc());
        System.out.println("orderEntity >>> "+orderEntity.toString());
        //更新订单DB
        orderDao.insert(orderEntity);
        //获取商品信息
        List<ItemBO> itemBOList = orderBO.getItemList();

        for (ItemBO itemBO : itemBOList
             ) {
            DubboOrderDetailEntity orderDetail = new DubboOrderDetailEntity();
            orderDetail.setOrderDetailId(orderEntity.getDetailId());
            orderDetail.setGmtCreate(TimeUtil.getTimeNow());
            orderDetail.setOrderId(orderEntity.getOrderId());
            orderDetail.setItemId(itemBO.getItemId());
            orderDetail.setQuantity(itemBO.getQuantity());
            //注意这里 .save()方法，是IService的方法，所以可以不必自己写一个.saveOrderDetail()方法
            System.out.println(orderDetail.toString());
            orderDetailService.save(orderDetail);
            //更新库存DB
            DubboStockEntity stockEntity;
            stockEntity = stockService.getStockEntity(itemBO.getItemId());
                 //库存是否足够
                if (itemBO.getQuantity() <= stockEntity.getQuantity() - stockEntity.getQuantityLock()){
                        stockEntity.setQuantityLock(stockEntity.getQuantityLock() + itemBO.getQuantity());
                        stockEntity.setQuantity(stockEntity.getQuantity() - itemBO.getQuantity());
                        stockEntity.setGmtModified(TimeUtil.getTimeNow());
//                        qw.eq(true,"item_id",itemBO.getItemId());
                        stockService.updateStockByItemId(stockEntity,itemBO.getItemId());
                }else {
                    throw NotEnoughStockException;
                }
        }
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
