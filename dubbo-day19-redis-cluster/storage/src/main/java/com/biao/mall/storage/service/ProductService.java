package com.biao.mall.storage.service;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.entity.ProductEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
@LocalTCC
public interface ProductService extends IService<ProductEntity> {
    /**
     * 扣减库存
     */
//    ObjectResponse decreaseStorage(CommodityDTO commodityDTO);

    /** TCC 模式 */
    @TwoPhaseBusinessAction(name = "StorageAction2",commitMethod = "commit",rollbackMethod = "rollback")
    boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "commodityDTO2") CommodityDTO commodityDTO);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
