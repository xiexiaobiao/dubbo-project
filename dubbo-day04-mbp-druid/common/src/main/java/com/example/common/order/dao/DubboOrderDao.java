package com.example.common.order.dao;

import com.example.common.order.entity.DubboOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaobiao
 * @since 2019-08-04
 */
@Repository
@Mapper
public interface DubboOrderDao extends BaseMapper<DubboOrderEntity> {

}
