package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.AccountDTO;
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

    /** TCC 模式 ，这里是dubboService的接口，使用RPC模式，故无需@LocalTCC注解*/
    @TwoPhaseBusinessAction(name = "OrderAction",commitMethod = "commitOrder",rollbackMethod = "rollbackOrder")
    boolean prepareOrder(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "orderDTO") OrderDTO orderDTO);

    boolean commitOrder(BusinessActionContext actionContext);

    boolean rollbackOrder(BusinessActionContext actionContext);
}
