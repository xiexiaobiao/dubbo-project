package com.example.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constant.AnnotationConstants;
import com.example.common.order.entity.DubboOrderEntity;
import com.example.common.order.dao.DubboOrderDao;
import com.example.common.order.service.DubboOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaobiao
 * @since 2019-08-04
 */
@Service(version = AnnotationConstants.VERSION)
public class DubboOrderServiceImpl extends ServiceImpl<DubboOrderDao, DubboOrderEntity> implements DubboOrderService {

    private DubboOrderDao dubboOrderDao;

    @Autowired
    public DubboOrderServiceImpl(DubboOrderDao dubboOrderDao){
        this.dubboOrderDao = dubboOrderDao;
    }

    @Override
    public List<DubboOrderEntity> listAllOrder() {
        List<DubboOrderEntity> orderEntities;
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("id");
        orderEntities = dubboOrderDao.selectList(qw);
        return orderEntities;
    }

    @Override
    public boolean saveOrder(DubboOrderEntity order) {
        return false;
    }

    @Override
    public boolean delelteOrder(String orderId) {
        return false;
    }

    @Override
    public boolean invalidateOrder(String orderId) {
        return false;
    }
}
