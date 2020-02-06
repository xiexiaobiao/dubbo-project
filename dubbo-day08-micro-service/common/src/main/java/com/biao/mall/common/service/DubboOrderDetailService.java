package com.biao.mall.common.service;

import com.biao.mall.common.entity.DubboOrderDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
public interface DubboOrderDetailService extends IService<DubboOrderDetailEntity> {

    boolean saveOrderDetail(DubboOrderDetailEntity orderDetailEntity);

    List<DubboOrderDetailEntity> listDetail(String detailId);
}
