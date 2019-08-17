package com.biao.mall.common.service;

import com.biao.mall.common.bo.OrderBO;
import com.biao.mall.common.entity.DubboOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
public interface DubboOrderService extends IService<DubboOrderEntity> {

    @Transactional
    boolean saveOrder(OrderBO orderBO) throws Exception;
}
