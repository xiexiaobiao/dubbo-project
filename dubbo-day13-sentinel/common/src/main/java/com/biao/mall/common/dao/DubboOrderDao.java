package com.biao.mall.common.dao;

import com.biao.mall.common.entity.DubboOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@Repository
@Mapper
public interface DubboOrderDao extends BaseMapper<DubboOrderEntity> {

}
