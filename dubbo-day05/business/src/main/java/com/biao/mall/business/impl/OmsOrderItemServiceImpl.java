package com.biao.mall.business.impl;

import com.biao.mall.common.order.dao.OmsOrderItemDao;
import com.biao.mall.common.order.entity.OmsOrderItemEntity;
import com.biao.mall.common.order.service.OmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemDao, OmsOrderItemEntity> implements OmsOrderItemService {

}
