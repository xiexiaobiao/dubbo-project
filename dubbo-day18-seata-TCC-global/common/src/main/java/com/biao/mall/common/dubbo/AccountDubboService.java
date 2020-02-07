package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Classname AccountDubboService
 * @Description 账户接口
 * @Author xiexiaobiao
 * @Date 2019-10-04 11:11
 * @Version 1.0
 **/
public interface AccountDubboService {
    /**
     * 从账户扣钱
     */
    ObjectResponse decreaseAccount(AccountDTO accountDTO);

    /** TCC 模式 */
    @TwoPhaseBusinessAction(name = "accountAction",commitMethod = "accountCommit",rollbackMethod = "accountRollback")
    // boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "accountDTO") AccountDTO accountDTO);
    boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "accountDTO") AccountDTO accountDTO);

    boolean accountCommit(BusinessActionContext actionContext);

    boolean accountRollback(BusinessActionContext actionContext);
}
