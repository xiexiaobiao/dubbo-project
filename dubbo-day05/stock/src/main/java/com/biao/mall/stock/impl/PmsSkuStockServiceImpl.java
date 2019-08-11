package com.biao.mall.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.mall.common.stock.entity.PmsSkuStockEntity;
import com.biao.mall.common.stock.dao.PmsSkuStockDao;
import com.biao.mall.common.stock.service.PmsSkuStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockDao, PmsSkuStockEntity> implements PmsSkuStockService {
    private PmsSkuStockDao pmsSkuStockDao;

    @Autowired
    public PmsSkuStockServiceImpl(PmsSkuStockDao pmsSkuStockDao){
        this.pmsSkuStockDao = pmsSkuStockDao;
    }

    @Override
    public List<PmsSkuStockEntity> listStock() {
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("id");
        return pmsSkuStockDao.selectList(qw);
    }
}
