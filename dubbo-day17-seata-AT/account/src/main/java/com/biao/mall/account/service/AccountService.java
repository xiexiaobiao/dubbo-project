package com.biao.mall.account.service;

import com.biao.mall.account.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.response.ObjectResponse;

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
    ObjectResponse decreaseAccount(AccountDTO accountDTO);

    void testGlobalLock();
}
