package com.biao.mall.common.dubbo;

import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.response.ObjectResponse;

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
}
