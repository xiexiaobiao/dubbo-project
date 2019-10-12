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
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public boolean prepare(BusinessActionContext actionContext, OrderDTO orderDTO) {
        ObjectResponse<OrderDTO> response = new ObjectResponse<>();
        Map<String,Object> map = new HashMap<>(1);
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
        tOrder.setUserId(Integer.valueOf(orderDTO.getUserId()));
        tOrder.setProductId(Integer.valueOf(orderDTO.getCommodityCode()));
        tOrder.setPayAmount(orderDTO.getOrderAmount());
        try {
            baseMapper.createOrder(tOrder);
        } catch (Exception e) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            map.put("responseObject",response);
            actionContext.setActionContext(map);
        }

        if (objectResponse.getStatus() != 200) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            map.put("responseObject",response);
            actionContext.setActionContext(map);
        }

        response.setStatus(RspStatusEnum.SUCCESS.getCode());
        response.setMessage(RspStatusEnum.SUCCESS.getMessage());
        map.put("responseObject",response);
        actionContext.setActionContext(map);
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        return false;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        return false;
    }
}
