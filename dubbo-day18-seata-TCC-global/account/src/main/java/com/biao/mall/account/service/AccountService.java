package com.biao.mall.account.service;

import com.biao.mall.account.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
public interface AccountService extends IService<AccountEntity> {
    /**
     * 扣用户钱
     */
//    ObjectResponse decreaseAccount(AccountDTO accountDTO);

    void testGlobalLock();

    /** TCC 模式 */
    // @TwoPhaseBusinessAction(name = "accountAction",commitMethod = "commit",rollbackMethod = "rollback")
    // boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "accountDTO") AccountDTO accountDTO);
    boolean prepare(BusinessActionContext actionContext, AccountDTO accountDTO);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
