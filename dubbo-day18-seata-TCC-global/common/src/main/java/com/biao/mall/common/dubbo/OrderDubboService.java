package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Classname OrderDubboService
 * @Description 订单服务
 * @Author xiexiaobiao
 * @Date 2019-10-04 10:59
 * @Version 1.0
 **/
public interface OrderDubboService {
    /**创建订单*/
    ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO);

    @TwoPhaseBusinessAction(name = "orderAction",commitMethod = "orderCommit",rollbackMethod = "orderRollback")
    boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderDTO") OrderDTO orderDTO);

    boolean orderCommit(BusinessActionContext actionContext);

    boolean orderRollback(BusinessActionContext actionContext);
}
