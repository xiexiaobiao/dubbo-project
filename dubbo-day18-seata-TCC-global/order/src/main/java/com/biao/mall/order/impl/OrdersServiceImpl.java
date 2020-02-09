package com.biao.mall.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.AccountDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.dao.OrdersDao;
import com.biao.mall.order.entity.OrdersEntity;
import com.biao.mall.order.service.OrdersService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    /*@Autowired
    private AccountDubboService  accountDubboService;*/

    /*@Override
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
        System.out.println(tOrder.getAddTime());
        tOrder.setUserId(Integer.valueOf(orderDTO.getUserId()));
        tOrder.setProductId(Integer.valueOf(orderDTO.getCommodityCode()));
        tOrder.setPayAmount(orderDTO.getOrderAmount());
        try {
            baseMapper.createOrder(tOrder);
        } catch (Exception e) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
        }

        if (objectResponse.getStatus() != 200) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.SUCCESS.getCode());
        response.setMessage(RspStatusEnum.SUCCESS.getMessage());
        return response;
    }*/

    @Override
    public boolean prepare(BusinessActionContext actionContext, OrderDTO orderDTO) {
        System.out.println("actionContext 获取Xid prepare >>> "+ actionContext.getXid());
        System.out.println("actionContext 获取TCC参数 prepare >>> "+ actionContext.getActionContext("orderDTO"));
        log.debug("actionContext.getActionName" + actionContext.getActionName());
        ObjectResponse<OrderDTO> response = new ObjectResponse<>();
        Map<String,Object> map = new HashMap<>(1);
        // 1,远程RPC事务--扣减用户账户
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(orderDTO.getUserId());
        accountDTO.setAmount(orderDTO.getOrderAmount());
        //ObjectResponse objectResponse = accountDubboService.prepare(actionContext,accountDTO);
//        boolean result = accountDubboService.prepare(actionContext,accountDTO);
         boolean result = true;
        // 2,本地事务--生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        //生成订单
        OrdersEntity  tOrder = new OrdersEntity ();
        BeanUtils.copyProperties(orderDTO,tOrder);
        tOrder.setAddTime(LocalDateTime.now());
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
            result = false;
        }

        if (!result) {
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            map.put("responseObject",response);
            actionContext.setActionContext(map);
        }

        response.setStatus(RspStatusEnum.SUCCESS.getCode());
        response.setMessage(RspStatusEnum.SUCCESS.getMessage());
        map.put("responseObject",response);
        actionContext.setActionContext(map);
        return result;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        // 提交逻辑已经在prepare中，故这里的commit可以为空，也不要加类似如下验证逻辑，否则TCC出错
        /*//必须注意actionContext.getActionContext返回的是Object,且不可使用以下语句直接强转！
        //CommodityDTO commodityDTO = (CommodityDTO) actionContext.getActionContext("commodityDTO");
        OrderDTO commodityDTO = JSONObject.toJavaObject((JSONObject)actionContext.getActionContext("rderDTO"),OrderDTO.class);
        QueryWrapper<OrdersEntity> qw = new QueryWrapper<>();
        qw.eq(true,"product_id",commodityDTO.getCommodityCode()).eq(true,"user_id",commodityDTO.getUserId());
        OrdersEntity  tOrder = baseMapper.selectOne(qw);
        return !Objects.isNull(tOrder);*/
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        // 将上面提交的逻辑进行反向操作，这里我没有写实现
        /** 略 */
        return true;
    }
}
