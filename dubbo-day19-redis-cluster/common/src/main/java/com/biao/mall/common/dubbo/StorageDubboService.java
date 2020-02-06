package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Classname StorageDubboService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-04 11:14
 * @Version 1.0
 **/
public interface StorageDubboService {

    /** TCC 模式 ，这里是dubboService的接口，使用RPC模式，故无需@LocalTCC注解*/
    @TwoPhaseBusinessAction(name = "StorageAction",commitMethod = "commitStorage",rollbackMethod = "rollbackStorage")
    boolean prepareStorage(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "commodityDTO")CommodityDTO commodityDTO);

    boolean commitStorage(BusinessActionContext actionContext);

    boolean rollbackStorage(BusinessActionContext actionContext);
}
