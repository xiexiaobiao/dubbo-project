package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.CommodityDTO;
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
    /**
     * 扣减库存
     */
    // ObjectResponse decreaseStorage(CommodityDTO commodityDTO);

    /** TCC 模式 */
    @TwoPhaseBusinessAction(name = "StorageAction",commitMethod = "storageCommit",rollbackMethod = "storageRollback")
    boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "commodityDTO") CommodityDTO commodityDTO);

    boolean storageCommit(BusinessActionContext actionContext);

    boolean storageRollback(BusinessActionContext actionContext);
}
