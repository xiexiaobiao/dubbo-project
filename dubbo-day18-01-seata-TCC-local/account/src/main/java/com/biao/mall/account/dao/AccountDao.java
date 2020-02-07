package com.biao.mall.account.dao;

import com.biao.mall.account.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
@Repository
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {

    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);

    int increaseAccount(@Param("userId") String userId, @Param("amount") Double amount);

    int testGlobalLock(@Param("userId") String userId);
}
