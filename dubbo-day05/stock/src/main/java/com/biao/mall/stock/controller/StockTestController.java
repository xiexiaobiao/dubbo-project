package com.biao.mall.stock.controller;

import com.biao.mall.common.stock.entity.PmsSkuStockEntity;
import com.biao.mall.common.stock.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname StockTestController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-06 21:57
 * @Version 1.0
 **/
@RestController
public class StockTestController {
    @Autowired
    private PmsSkuStockService pmsSkuStockService;

    @RequestMapping("/stock")
    public List<PmsSkuStockEntity> listStock(){
       return pmsSkuStockService.listStock();
    }
}
