package com.biao.mall.account.dubbo;

import com.biao.mall.account.service.AccountService;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dubbo.AccountDubboService;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.core.context.RootContext;
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
        return accountService.decreaseAccount(accountDTO);
    }
}
