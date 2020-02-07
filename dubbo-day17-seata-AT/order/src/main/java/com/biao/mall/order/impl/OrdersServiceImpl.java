package com.biao.mall.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.AccountDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.dao.OrdersDao;
import com.biao.mall.order.entity.OrdersEntity;
import com.biao.mall.order.service.OrdersService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Reference(version = "1.0.0")
    private AccountDubboService  accountDubboService;

    @Override
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
        ObjectResponse<OrderDTO> response = new ObjectResponse<>();
        //扣减用户账户
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(orderDTO.getUserId());
        accountDTO.setAmount(orderDTO.getOrderAmount());
        ObjectResponse objectResponse = accountDubboService.decreaseAccount(accountDTO);

        //生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        //生成订单
        OrdersEntity  tOrder = new OrdersEntity ();
        BeanUtils.copyProperties(orderDTO,tOrder);
//        tOrder.setCount(orderDTO.getOrderCount());
        tOrder.setAddTime(LocalDateTime.now());
        tOrder.setUserId(Integer.valueOf(orderDTO.getUserId()));
        tOrder.setProductId(Integer.valueOf(orderDTO.getCommodityCode()));
        tOrder.setPayAmount(orderDTO.getOrderAmount());
        try {
            baseMapper.createOrder(tOrder);
        } catch (Exception e) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

        if (objectResponse.getStatus() != 200) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.SUCCESS.getCode());
        response.setMessage(RspStatusEnum.SUCCESS.getMessage());
        return response;
    }
}
