package com.biao.mall.common.dao;

import com.biao.mall.common.entity.DubboFinanceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 银行账户表 Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-09-01
 */
@Repository
@Mapper
public interface DubboFinanceDao extends BaseMapper<DubboFinanceEntity> {

}
