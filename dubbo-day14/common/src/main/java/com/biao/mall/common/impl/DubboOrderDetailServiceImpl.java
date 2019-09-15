package com.biao.mall.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.entity.DubboOrderDetailEntity;
import com.biao.mall.common.dao.DubboOrderDetailDao;
import com.biao.mall.common.service.DubboOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@Service
public class DubboOrderDetailServiceImpl extends ServiceImpl<DubboOrderDetailDao, DubboOrderDetailEntity> implements DubboOrderDetailService {

    private DubboOrderDetailDao dubboOrderDetailDao;

    @Autowired
    public DubboOrderDetailServiceImpl(DubboOrderDetailDao dubboOrderDetailDao){
        this.dubboOrderDetailDao = dubboOrderDetailDao;
    }

    @Override
    public boolean saveOrderDetail(DubboOrderDetailEntity orderDetailEntity){
        dubboOrderDetailDao.insert(orderDetailEntity);
        return true;
    }

    @Override
    public List<DubboOrderDetailEntity> listDetail(String detailId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"order_detail_id",detailId);
        return dubboOrderDetailDao.selectList(qw);
    }


}
