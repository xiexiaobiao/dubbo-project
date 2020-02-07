package com.biao.mall.account.dubbo;

import com.biao.mall.account.service.AccountService;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dubbo.AccountDubboService;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname AccountDubboServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-06 13:56
 * @Version 1.0
 **/
@Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        timeout = 3000)
public class AccountDubboServiceImpl implements AccountDubboService {

    @Autowired
    private AccountService  accountService;

    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        BusinessActionContext businessActionContext = new BusinessActionContext();
        //prepare 使用TCC
        /**这里使用了本地业务service中取得ObjectResponse的模式，*/
        if (accountService.prepare(businessActionContext,accountDTO)){
            return (ObjectResponse<AccountDTO>) businessActionContext.getActionContext("responseObject");
        }
        return null;
    }

    @Override
    public boolean prepare(BusinessActionContext actionContext, AccountDTO accountDTO) {
        return accountService.prepare(actionContext,accountDTO);
    }

    @Override
    public boolean accountCommit(BusinessActionContext actionContext) {
        return accountService.commit(actionContext);
    }

    @Override
    public boolean accountRollback(BusinessActionContext actionContext) {
        return accountService.rollback(actionContext);
    }
}
