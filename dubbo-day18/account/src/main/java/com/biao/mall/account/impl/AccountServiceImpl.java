package com.biao.mall.account.impl;

import com.biao.mall.account.entity.AccountEntity;
import com.biao.mall.account.dao.AccountDao;
import com.biao.mall.account.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.AccountDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {

/*    @Override
    public ObjectResponse decreaseAccount(AccountDTO accountDTO) {
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (account > 0){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }*/

    @Override
    @GlobalLock //seata的注解
    @Transactional(rollbackFor = {Throwable.class})
    public void testGlobalLock() {
        baseMapper.testGlobalLock("1");
        System.out.println("Hi, i got lock, i will do some thing with holding this lock.");
    }

    @Override
    public boolean prepare(BusinessActionContext actionContext, AccountDTO accountDTO) {
        System.out.println("actionContext获取Xid >>> "+actionContext.getXid());
        Map<String,Object> map = new HashMap<>();
        int account = baseMapper.decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (account > 0){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            map.put("responseObject",response);
            actionContext.setActionContext(map);
            return true;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        map.put("responseObject",response);
        actionContext.setActionContext(map);
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        System.out.println("actionContext获取Xid >>> "+actionContext.getXid());
        return false;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        System.out.println("actionContext获取Xid >>> "+actionContext.getXid());
        AccountDTO accountDTO = (AccountDTO) actionContext.getActionContext("accountDTO");
        int account = baseMapper.increaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());
        if (account > 0){
            return true;
        }
        return false;
    }
}
