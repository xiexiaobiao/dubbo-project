package com.biao.mall.order.dubbo;

import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.OrderDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.service.OrdersService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname OrderDubboServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-06 12:39
 * @Version 1.0
 **/
@Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}")
public class OrderDubboServiceImpl implements  OrderDubboService{

    @Autowired
    private OrdersService ordersService;

    /*@Override
    @GlobalTransactional(timeoutMills = 30000,name = "order-Global-Transaction")
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        //return ordersService.prepare(orderDTO);
        ObjectResponse<OrderDTO> response = new ObjectResponse<>();
        BusinessActionContext businessActionContext = new BusinessActionContext();
        *//** 这里使用了本地业务service中取得ObjectResponse的模式，*//*
        if( ordersService.prepare(businessActionContext,orderDTO)){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }
        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }*/

    @Override
    public boolean prepare(BusinessActionContext actionContext, OrderDTO orderDTO) {
        System.out.println("OrderDubboServiceImpl actionContext >>>" + actionContext.getActionContext("orderDTO"));
        return ordersService.prepare(actionContext,orderDTO);
    }

    @Override
    public boolean orderCommit(BusinessActionContext actionContext) {
        return ordersService.commit(actionContext);
    }

    @Override
    public boolean orderRollback(BusinessActionContext actionContext) {
        return ordersService.rollback(actionContext);
    }
}
