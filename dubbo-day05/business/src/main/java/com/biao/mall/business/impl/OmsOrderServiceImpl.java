package com.biao.mall.business.impl;

import com.biao.mall.common.order.dao.OmsOrderDao;
import com.biao.mall.common.order.entity.OmsOrderEntity;
import com.biao.mall.common.order.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrderEntity> implements OmsOrderService {

    /**
    * @Description：保存生效的订单
    * @param null
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-07 19:47
    */
    @Override
    public boolean saveOneOrder(OmsOrderEntity orderEntity) {
        return false;
    }

    /**
    * @Description: 提交未支付订单
    * @param null
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-07 19:55
    */
    @Override
    public boolean submitOneOrder(OmsOrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean saveBatchOrder(List<OmsOrderEntity> orderList) {
        return false;
    }

    @Override
    public boolean deleteOneOrder(OmsOrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean deleteBatchOrder(List<OmsOrderEntity> orderList) {
        return false;
    }

    @Override
    public boolean modifyOneOrder(OmsOrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean modifyBatchOrder(List<OmsOrderEntity> orderList) {
        return false;
    }
}
