package com.biao.mall.business.service;

import com.biao.mall.common.dto.BusinessDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Classname BusinessService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-07 11:16
 * @Version 1.0
 **/
public interface BusinessService {

    ObjectResponse handleBusiness(BusinessDTO businessDTO);


}
