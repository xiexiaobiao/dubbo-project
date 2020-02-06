package com.biao.mall.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dao.DubboStockDao;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboStockService;
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
@com.alibaba.dubbo.config.annotation.Service
public class DubboStockServiceImpl extends ServiceImpl<DubboStockDao, DubboStockEntity> implements DubboStockService {

    private DubboStockDao dubboStockDao;

    @Autowired
    public DubboStockServiceImpl(DubboStockDao dubboStockDao){
        this.dubboStockDao = dubboStockDao;
    }

    /**
    * @Description: 查询所有库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:15
    */
    @Override
    public List<DubboStockEntity> listStock() {
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("id");
        return dubboStockDao.selectList(qw);
    }

    /**
    * @Description: 更新单个库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:21
    */
    @Override
    public int updateStock(DubboStockEntity stockEntity){
        QueryWrapper qw = new QueryWrapper();
        return dubboStockDao.update(stockEntity,qw);
    }

    /**
    * @Description: 插入单个库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:23
    */
    @Override
    public int saveStock(DubboStockEntity stockEntity){
       return dubboStockDao.insert(stockEntity);
    }
}
