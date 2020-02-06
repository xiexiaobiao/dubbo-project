package com.biao.mall.stock.controller;


import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 *
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/dubbo")
public class DubboStockController {

    private DubboStockService dubboStockService ;

    @Autowired
    public DubboStockController(DubboStockService dubboStockService){
        this.dubboStockService = dubboStockService;
    }

    @RequestMapping("/stock")
    public List<DubboStockEntity> listStock(){
        List<DubboStockEntity> list =  dubboStockService.listStock();
        System.out.println("list.size() >>> "+list.size());
        System.out.println(list);
        //测试统一异常处理
//        int i = 1 / 0;
        return list;
    }

}

