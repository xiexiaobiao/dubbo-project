package com.biao.mall.business.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.business.util.SnowFlake;
import com.biao.mall.common.bo.ItemBO;
import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.common.dao.DubboOrderDao;
import com.biao.mall.common.entity.DubboOrderDetailEntity;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboOrderDetailService;
import com.biao.mall.common.service.DubboOrderService;
import com.biao.mall.common.service.DubboStockService;
import com.biao.mall.common.service.MqCenter;
import com.biao.mall.common.util.RedisUitl;
import com.biao.mall.common.util.RestfulUtil;
import com.biao.mall.common.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = DubboOrderService.class,version = "1.0.0")
public class DubboOrderServiceImpl extends ServiceImpl<DubboOrderDao, DubboOrderEntity> implements DubboOrderService {

    private final Logger logger = LoggerFactory.getLogger(DubboOrderServiceImpl.class);

    private DubboOrderDao orderDao;
    private Exception NotEnoughStockException;
    private DubboOrderDetailService orderDetailService;
    private RedisUitl redisUitl;

    @Reference(version = "1.0.0")  //dubbo 引用服务
    private DubboStockService stockService;

    @Autowired
    public DubboOrderServiceImpl(DubboOrderDao orderDao,
                                 DubboOrderDetailService orderDetailService,
                                 Exception notEnoughStockException, RedisUitl redisUitl){
        this.orderDao = orderDao;
        this.orderDetailService = orderDetailService;
        NotEnoughStockException = notEnoughStockException;
        this.redisUitl = redisUitl;
    }

    /**
    * @Description: 未付款处理
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-23 22:18
    */
    @Override
    @Transactional
    public boolean saveOrder(OrderBO orderBO) throws Exception {
        DubboOrderEntity orderEntity = new DubboOrderEntity();
        orderEntity.setGmtCreate(orderBO.getGmtCreate());
        //为了方便DB对比订单创建时间，故意修改了GmtCreate
        orderEntity.setGmtCreate(TimeUtil.getTimeNow());
        //orderId雪花算法，
        SnowFlake snowFlake = new SnowFlake(10L,1L);
        Long orderID = snowFlake.nextId();
        orderEntity.setOrderId(String.valueOf(orderID));
        //Redis中心式生成
        orderEntity.setDetailId(redisUitl.getShuffleId());
        orderEntity.setUserId(orderBO.getUserId());
        orderEntity.setOrderDesc(orderBO.getOrderDesc());
        orderEntity.setPaid(false);
        orderEntity.setExpired(false);
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
            orderDetailService.save(orderDetail);
            //更新库存DB
            DubboStockEntity stockEntity;
            stockEntity = stockService.getStockEntity(itemBO.getItemId());
                 //库存是否足够
                if (itemBO.getQuantity() <= stockEntity.getQuantity() - stockEntity.getQuantityLock()){
                        stockEntity.setQuantityLock(stockEntity.getQuantityLock() + itemBO.getQuantity());
                        stockEntity.setGmtModified(TimeUtil.getTimeNow());
//                        qw.eq(true,"item_id",itemBO.getItemId());
                        stockService.updateStockRPC(stockEntity);
                }else {
                    throw NotEnoughStockException;
                }
        }
        return true;
    }

    /**
    * @Description: 付款处理
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-23 22:18
    */
    @Override
    @Transactional
    public boolean payOrder(String orderId){
        if (StringUtils.isEmpty(orderId)){
            return false;
        }
        DubboOrderEntity orderEntity;
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"order_id",orderId);
        orderEntity = orderDao.selectOne(qw);
        //修改订单付款状态
        orderEntity.setPaid(true);
        orderEntity.setGmtModified(TimeUtil.getTimeNow());
        orderDao.updateById(orderEntity);
        //获取订单详细
        List<DubboOrderDetailEntity> orderDetailList = orderDetailService.listDetail(orderEntity.getDetailId());
        if (orderDetailList == null){
            return false;
        }
        //更新库存DB
        DubboStockEntity stockEntity;
        for (DubboOrderDetailEntity orderDetail : orderDetailList
             ) {
            stockEntity = stockService.getStockEntity(orderDetail.getItemId());
            stockEntity.setGmtModified(TimeUtil.getTimeNow());
            //这里由前面的订单生成过程可知，锁定的数量必定是大于等于某单个订单明细数量的，且库存量一定是大于等于锁定量的，故不需判断
            stockEntity.setQuantity(stockEntity.getQuantity() - orderDetail.getQuantity());
            stockEntity.setQuantityLock(stockEntity.getQuantityLock() - orderDetail.getQuantity());
            //RPC调用
            stockService.updateStockRPC(stockEntity);
        }
        //首次MQ的消费者地址
        Map<String,Object> map  = new HashMap<>();
        map.put("URL","http://localhost:8085/mq/msg");
        //通知物流快递 MQ方式
        Map<String,Object> logisticMap  = new HashMap<>();
        logisticMap.put("orderId",orderId);
        logisticMap.put("URL","http://localhost:8085/delivery/one");
        String jsonStr = JSON.toJSONString(logisticMap);
        //注意去掉转义字符
        map.put("data", StringEscapeUtils.unescapeJava(jsonStr));
        String jsonString = JSON.toJSONString(map);
        logger.debug(jsonString);
        //统一使用rest发送
        logger.debug("to the sendMsgPost >>>"+jsonString);
        RestfulUtil.sendMsgPost(jsonString);
        return true;
    }

    /**
    * @Description: 取消订单
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-24 22:50
    */
    @Override
    public boolean cancelOrder(String orderId){
        if (StringUtils.isEmpty(orderId)){
            return false;
        }
        return true;
    }

    /**
    * @Description: 根据orderId查询订单
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-24 09:23
    */
    @Override
    public DubboOrderEntity getEntityByOrderId(String orderId) {
        DubboOrderEntity orderEntity;
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"order_id",orderId);
        orderEntity = orderDao.selectOne(qw);
        return orderEntity;
    }

}
